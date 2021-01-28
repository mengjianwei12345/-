/**
 * Filter Traffic PF_RING example application
 * ==========================================
 * An application that listens to one or more PF_RING interface, captures all traffic
 * and matches packets by user-defined matching criteria. Matching criteria is given on startup and can contain one or more of the following:
 * source IP, destination IP, source TCP/UDP port, destination TCP/UDP port and TCP or UDP protocol. Matching is done per flow, meaning the first packet
 * received on a flow is matched against the matching criteria and if it's matched then all packets of the same flow will be matched too.
 * Packets that are matched can be send to another PF_RING interface and/or be save to a pcap file.
 * In addition the application collect statistics on received and matched packets: number of packets per protocol, number of matched flows and number
 * of matched packets.
 *
 * The application uses PfRingDevice's multi-threaded capturing. Number of capture threads can be set by the user (to the maximum of machine's core number minus 1)
 * or set to default (default is all machine cores minus one management core the application runs on). Each core is assigned with one capture thread.
 * PfRingDevice tries to assign one RX channel for each capturing thread (to improve performance), but if NIC doesn't enough RX channels to
 * provide one for each thread, it will assign several thread with the same RX channel
 * For example: if NIC supports 4 RX channels but the user asks for 6 capturing threads than 4 cores will share 2 RX channels and the 2 remaining cores will
 * use RX channels of their own.
 * Each capturing thread does exactly the same work: receiving packets, collecting packet statistics, matching flows and sending/saving matched packets
 *
 * Another thing shown here is getting interface capabilities such as total RX channels available, MAC address, PF_RING interface
 * index, MTU, etc.
 *
 * __Important__:
 * 1. Before compiling this application make sure you set "Compile PcapPlusPlus with PF_RING" to "y" in configure-linux.sh. Otherwise
 *    the application won't compile
 * 2. Before running the application make sure you load the PF_RING kernel module: sudo insmod <PF_RING_LOCATION>/kernel/pf_ring.ko
 *    Otherwise the application will exit with an error log that instructs you to load the kernel module
 * 3. This application (like all applications using PF_RING) should be run as 'sudo'
 */

#include "Common.h"
#include "PacketMatchingEngine.h"
#include <PfRingDeviceList.h>
#include <PcapFileDevice.h>
#include "PcapLiveDeviceList.h"
#include "PcapFilter.h"
#include <PacketUtils.h>
#include <SystemUtils.h>
#include <Logger.h>
#include <stdlib.h>
#include <vector>
#include <getopt.h>
#include <map>
#include <sstream>
#include <unistd.h>
#include <signal.h>

#include "pfring.h"
//#include "pfutils.c"

#include "Packet.h"
#include "IPv4Layer.h"
#include "IPv6Layer.h"
#include "TcpLayer.h"
#include "UdpLayer.h"
#include "VlanLayer.h"
#include "IcmpLayer.h"
#include "IpAddress.h"

#include "/home/sdn/nfc-dlb/sach-build/install/include/sach.h"

using namespace pcpp;
using namespace std;
using namespace sach;

#define MAX_PKT_LEN 1518
std::string controlPort = "6000";
std::string slavePort = "6001";

u_int32_t num_sent = 0;

/* ****************************************************** */

void printHelp(void) {
  printf("pfbridge - Forwards traffic from -a -> -b device using vanilla PF_RING (no DNA)\n\n");
  printf("-h              [Print help]\n");
  printf("-v              [Verbose]\n");
  printf("-i <device>     [Incoming device name]\n");
  printf("-o <device>     [Outgoing device name]\n");
  printf("-l 		      [Load balancer type: master/slave]\n");
  printf("-s 		      [If Load balancer type is master, then specify Slave IP]\n");
  printf("-d 		      [If Load balancer type is master, then specify Destination IP]\n");
  printf("-t                  [If Load balancer type is master, then specify session timeout value]\n");
}

/* ******************************** */

void my_sigalarm(int sig) {
  char buf[32];

  pfring_format_numbers((double)num_sent, buf, sizeof(buf), 0),
  //printf("%s pps\n", buf);
  num_sent = 0;
  alarm(1);
  signal(SIGALRM, my_sigalarm);
}

/* ****************************************************** */


/**
 * An auxiliary method for extracting packet's IPv4/IPv6 source address hashed as 4 bytes uint32_t value
 */
uint32_t getSrcIPValue(pcpp::Packet& packet)
{
    if (packet.isPacketOfType(pcpp::IPv4))
        return packet.getLayerOfType<pcpp::IPv4Layer>()->getSrcIpAddress().toInt();
    //else if (packet.isPacketOfType(pcpp::IPv6))
      //return pcpp::fnv_hash((uint8_t*)packet.getLayerOfType<pcpp::IPv6Layer>()->getSrcIpAddress().toIn6Addr(), 16);
    else
        return 0;
}

uint32_t getDstIPValue(pcpp::Packet& packet)
{
    if (packet.isPacketOfType(pcpp::IPv4))
        return packet.getLayerOfType<pcpp::IPv4Layer>()->getDstIpAddress().toInt();
    //else if (packet.isPacketOfType(pcpp::IPv6))
      //  return pcpp::fnv_hash((uint8_t*)packet.getLayerOfType<pcpp::IPv6Layer>()->getDstIpAddress().toIn6Addr(), 16);
    else
        return 0;
}

uint16_t getSrcPortValue(pcpp::Packet& packet){
    uint16_t srcPort;
    if (packet.isPacketOfType(pcpp::TCP))
    {
     // extract TCP layer
        pcpp::TcpLayer* tcpLayer = packet.getLayerOfType<pcpp::TcpLayer>();
        if (tcpLayer != NULL)
        {
            srcPort = ntohs(tcpLayer->getTcpHeader()->portSrc);
        }
    }
    else if (packet.isPacketOfType(pcpp::UDP))
    {
        // for UDP packets, decide the server port by the lower port
        pcpp::UdpLayer* udpLayer = packet.getLayerOfType<pcpp::UdpLayer>();
        if (udpLayer != NULL)
        {
            srcPort = ntohs(udpLayer->getUdpHeader()->portSrc);
        }
    }
    return srcPort;
}

uint16_t getDstPortValue(pcpp::Packet& packet){
    uint16_t dstPort;
    if (packet.isPacketOfType(pcpp::TCP))
    {
        // extract TCP layer
        pcpp::TcpLayer* tcpLayer = packet.getLayerOfType<pcpp::TcpLayer>();
        if (tcpLayer != NULL)
        {
            dstPort = ntohs(tcpLayer->getTcpHeader()->portDst);
        }
    }
    else if (packet.isPacketOfType(pcpp::UDP))
    {
      // for UDP packets, decide the server port by the lower port
        pcpp::UdpLayer* udpLayer = packet.getLayerOfType<pcpp::UdpLayer>();
        if (udpLayer != NULL)
        {
            dstPort = ntohs(udpLayer->getUdpHeader()->portDst);
        }
    }
    return dstPort;
}

void LB(LoadBalancer* loadBalancer, pfring *a_ring,pfring *b_ring, string dstIp, u_int8_t verbose, u_int8_t use_pfring_send, int a_ifindex, int b_ifindex){
	while(1) {
	    u_char *buffer;
	    struct pfring_pkthdr hdr;
	  
	    // WR
	    uint32_t bufferLen = 65536;
	    uint8_t tempBuffer[65536];
	    buffer = tempBuffer;

	    if(pfring_recv(a_ring, &buffer, bufferLen, &hdr, 1) > 0){

	    	//printf("Packet is captured \n");

	    	RawPacket rawPacket(buffer, hdr.caplen, hdr.ts, false);
	    	//printf("Packet is raw \n");
			pcpp::Packet parsedPacket(&rawPacket);
			//printf("Packet is parsed \n");

	    	if(parsedPacket.isPacketOfType(pcpp::IPv4))
	    	{
	    		bool validPacket = false;

		    	if(parsedPacket.getLayerOfType<pcpp::IPv4Layer>()->getDstIpAddress().toString() == dstIp)
		   		{
		   			validPacket = true;
		   		}
		   		else if(parsedPacket.getLayerOfType<pcpp::IPv4Layer>()->getSrcIpAddress().toString() == dstIp)
			    {
		   			validPacket = true;
		   		}

		   		if(validPacket == true)
		   		{
			        //printf("Packet is filtered \n");
					int rc;
					    
					MacAddress srcMac2 = parsedPacket.getLayerOfType<EthLayer>()->getSourceMac();
					MacAddress dstMac2 = parsedPacket.getLayerOfType<EthLayer>()->getDestMac();
					   
					EthLayer ethLayerNew(srcMac2, dstMac2, PCPP_ETHERTYPE_VLAN);
					parsedPacket.insertLayer(parsedPacket.getFirstLayer(), &ethLayerNew);
					//printf("Ethernet layer added \n");

					parsedPacket.removeLayer(parsedPacket.getFirstLayer());

					//printf("Ethernet layer removed \n");
			  
					      //JM
					      
					tag_t vlanID = 0;
					sach::Packet p;

					timeval timestamp = rawPacket.getPacketTimeStamp();

					p.socket.srcIp = (sach::ip_t) getSrcIPValue(parsedPacket);
					p.socket.destIp = (sach::ip_t) getDstIPValue(parsedPacket);

					if (!parsedPacket.isPacketOfType(pcpp::ICMP))
					{
					    p.socket.srcPort = (sach::port_t) getSrcPortValue(parsedPacket);
					    p.socket.destPort = (sach::port_t) getDstPortValue(parsedPacket);
					}
 
                                        if(parsedPacket.getLayerOfType<pcpp::IPv4Layer>()->getDstIpAddress().toString() == dstIp)
		   		        {
						p.inbound = true;
					}
	                                else{
        	                                p.inbound = false;
					}

			      	p.timestamp = (timestamp_t) (timestamp.tv_sec + (timestamp.tv_usec/1000000.0));
				
					if (!parsedPacket.isPacketOfType(pcpp::ICMP))
			        {
                        vlanID = loadBalancer->packetToInstance(p);
                	}
					else{			
						if (p.inbound)                                  
                       		vlanID = 2;                             
                		else                                           
                        	vlanID = 3;
       				}		

                    // vlanID = 2;

					VlanLayer vlanLayer((uint16_t) vlanID, 0, 2, PCPP_ETHERTYPE_IP);
					parsedPacket.insertLayer(parsedPacket.getFirstLayer(), &vlanLayer);

					//printf("Vlan is added \n");

					parsedPacket.computeCalculateFields();

			     	parsedPacket.setRawPacket(&rawPacket, false);

			     	//printf("Convert back to raw packet \n"); 

					u_char *bufferNew;
					bufferNew = (u_char *) rawPacket.getRawData();
					u_int bufferNewLen = (u_int) rawPacket.getRawDataLen();

			        if(use_pfring_send) {
						rc = pfring_send(b_ring, (char *) bufferNew, bufferNewLen, 1);
						if(rc < 0)
						  printf("pfring_send(caplen=%u <= mtu=%u?) error %d\n", hdr.caplen, b_ring->mtu_len, rc);
						else if(verbose)
						  printf("Forwarded %d bytes packet\n", hdr.len);	
					} 
					else {
						rc = pfring_send_last_rx_packet(a_ring, b_ifindex);
						if(rc < 0)
						  	printf("pfring_send_last_rx_packet() error %d\n", rc);
						else if(verbose)
						  	printf("Forwarded %d bytes packet\n", hdr.len);
					}

					if(rc >= 0) num_sent++;	
				}	
			}	
		}
    }
}


int main(int argc, char* argv[]) {
    pfring *a_ring, *b_ring;
    char *a_dev = NULL, *b_dev = NULL, c;
    std::string loadBalancerType = "";
    std::string slaveIp = "";
    std::string dstIp = "";
    std::string srcIp = "";
    u_int8_t verbose = 0, use_pfring_send = 1;
    timestamp_t sessionTime = 3000;
    int a_ifindex, b_ifindex;
 // int bind_core = -1;
    u_int16_t watermark = 1;
    char *bpfFilter = NULL;

    while((c = getopt(argc,argv, "hi:o:c:f:vg:w:l:s:d:a:t:")) != -1) {
        switch(c) {
          case 'h':
               printHelp();
               return 0;  
               break;
	      case 'i':
	        a_dev = strdup(optarg);
	        break;
	      case 'o':
	        b_dev = strdup(optarg);
	        break;
	      case 'f':
	        bpfFilter = strdup(optarg);
	        break;
	      case 'v':
	        verbose = 1;
	        break;
	      case 'w':
	        watermark = atoi(optarg);
	        break;
	      case 'l':
	        loadBalancerType = optarg;
	        break;
	      case 's':
	        slaveIp = optarg;
	        break;
	      case 'd':
	        dstIp = optarg;
	        break;
	      case 'a':
	        srcIp = optarg;
	        break;
              case 't':
                sessionTime = (timestamp_t) atoi(optarg);
                break;
    	}
	}  

  	if ((!a_dev) || (!b_dev)) {
	    printf("You must specify two devices!\n");
	    return -1;
  	}

	if(strcmp(a_dev, b_dev) == 0) {
	    printf("Bridge devices must be different!\n");
	    return -1;
	}

	  /* Device A */
	if((a_ring = pfring_open(a_dev, MAX_PKT_LEN, PF_RING_PROMISC | PF_RING_LONG_HEADER | (use_pfring_send ? 0 : PF_RING_RX_PACKET_BOUNCE))) == NULL) 
	{
	    printf("pfring_open error for %s [%s]\n", a_dev, strerror(errno));
	    return(-1);
	}

	pfring_set_application_name(a_ring, "pfbridge-a");
	pfring_set_direction(a_ring, rx_only_direction);
	pfring_set_socket_mode(a_ring, recv_only_mode);
	pfring_set_poll_watermark(a_ring, watermark);
	pfring_get_bound_device_ifindex(a_ring, &a_ifindex);

	  /* Adding BPF filter */
	if(bpfFilter != NULL) {
	    int rc = pfring_set_bpf_filter(a_ring, bpfFilter);
	    if(rc != 0)
	      printf("pfring_set_bpf_filter(%s) returned %d\n", bpfFilter, rc);
	    else
	      printf("Successfully set BPF filter '%s'\n", bpfFilter);
	}

	  /* Device B */

	if((b_ring = pfring_open(b_dev, MAX_PKT_LEN, PF_RING_PROMISC|PF_RING_LONG_HEADER)) == NULL) {
	    printf("pfring_open error for %s [%s]\n", b_dev, strerror(errno));
	    pfring_close(a_ring);
	    return(-1);
	}

    pfring_set_application_name(b_ring, "pfbridge-b");
    pfring_set_socket_mode(b_ring, send_only_mode);
    pfring_get_bound_device_ifindex(b_ring, &b_ifindex);
  
  /* Enable Sockets */

	if (pfring_enable_ring(a_ring) != 0) {
	    printf("Unable enabling ring 'a' :-(\n");
	    pfring_close(a_ring);
	    pfring_close(b_ring);
	    return(-1);
	}

	if(use_pfring_send) {
	    if (pfring_enable_ring(b_ring)) {
	      printf("Unable enabling ring 'b' :-(\n");
	      pfring_close(a_ring);
	      pfring_close(b_ring);
	      return(-1);
	    }
	} 
	else {
	    pfring_close(b_ring);
	}

    signal(SIGALRM, my_sigalarm);
    alarm(1);

    if (loadBalancerType != ""){
        if(loadBalancerType.compare("master") == 0){
            loadBalancerType = "master";
            if (slaveIp != ""){
			LoadBalancer loadBalancer(controlPort, slaveIp, slavePort, sessionTime);
                boost::this_thread::sleep_for(boost::chrono::seconds(4));
                cout << "Master load balancer launched." << endl;       // DBG 
                LB(&loadBalancer,a_ring,b_ring, dstIp, verbose, use_pfring_send, a_ifindex, b_ifindex);        
            }
            else{
                EXIT_WITH_ERROR("Missing Slave LB's IP address");
            }
        }
        else{
            LoadBalancer loadBalancer(slavePort, sessionTime);
            boost::this_thread::sleep_for(boost::chrono::seconds(4));
            cout << "Slave load balancer launched." << endl;        // DBG
			loadBalancerType = "slave";
			LB(&loadBalancer,a_ring,b_ring, dstIp,verbose, use_pfring_send, a_ifindex, b_ifindex);
        }
    }

    //printf("Closing pfring \n");
 
	pfring_close(a_ring);
	
	if(use_pfring_send) pfring_close(b_ring);

	//printf("Exit program \n");
	  
	return(0);
}
