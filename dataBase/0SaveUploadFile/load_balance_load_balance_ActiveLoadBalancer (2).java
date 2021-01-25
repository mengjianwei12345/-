


















































public class ActiveLoadBalancer implements IFloodlightModule,
		IOFMessageListener {
	
	protected static Logger log = LoggerFactory.getLogger(LoadBalancer.class);
	
	protected IFloodlightProviderService floodlightProvider;
	protected IDeviceService deviceManager;
	protected ITopologyService topology;
	protected ILoadbalanceRoutingService routingEngine;
	protected IStaticFlowEntryPusherService sfp;
    protected ICounterStoreService counterStore;
    protected OFMessageDamper messageDamper;
    
    //Copied from Forwarding with message damper routine for pushing proxy Arp 
    protected static int OFMESSAGE_DAMPER_CAPACITY = 10000; // ms. 
    protected static int OFMESSAGE_DAMPER_TIMEOUT = 250; // ms 
	protected static int LB_PRIORITY = 10235;
	protected static String LB_ETHER_TYPE = "0x800";

	protected static short FLOWMOD_DEFAULT_IDLE_TIMEOUT = 300; // in seconds
	public static short FLOWMOD_DEFAULT_HARD_TIMEOUT = 0; // infinite

	@Override
	public String getName() {
		return "activeLoadbalancer";
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// copy form LoadBalancer, and our active loadbalancer should run behind build-in loadbalancer 
		return (type.equals(OFType.PACKET_IN) && 
               (name.equals("topology") || 
               name.equals("devicemanager") ||
               name.equals("loadbalancer") ||
               name.equals("virtualizer")));
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// copy form LoadBalancer
		return (type.equals(OFType.PACKET_IN) && name.equals("forwarding"));
	}

	@Override
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		switch (msg.getType()) {
        case PACKET_IN:
            return processPacketIn(sw, (OFPacketIn)msg, cntx);
        default:
            break;
		}
		return Command.CONTINUE;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//copy from ForwadingBase
    public Comparator<SwitchPort> clusterIdComparator =
            new Comparator<SwitchPort>() {
                @Override
                public int compare(SwitchPort d1, SwitchPort d2) {
                    Long d1ClusterId = 
                            topology.getL2DomainId(d1.getSwitchDPID());
                    Long d2ClusterId = 
                            topology.getL2DomainId(d2.getSwitchDPID());
                    return d1ClusterId.compareTo(d2ClusterId);
                }
            };
	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(ILoadbalanceRoutingService.class);
		l.add(ITopologyService.class);
		l.add(IStaticFlowEntryPusherService.class);
		l.add(ICounterStoreService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		deviceManager = context.getServiceImpl(IDeviceService.class);
		topology = context.getServiceImpl(ITopologyService.class);
		routingEngine = context.getServiceImpl(ILoadbalanceRoutingService.class);
		sfp = context.getServiceImpl(IStaticFlowEntryPusherService.class);
		counterStore = context.getServiceImpl(ICounterStoreService.class);
		messageDamper = new OFMessageDamper(OFMESSAGE_DAMPER_CAPACITY, 
                EnumSet.of(OFType.FLOW_MOD),
                OFMESSAGE_DAMPER_TIMEOUT);
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);

	}
	private net.floodlightcontroller.core.IListener.Command
    processPacketIn(IOFSwitch sw, OFPacketIn pi,
                    FloodlightContext cntx)
	{
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx,
                IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		IPacket pkt = eth.getPayload();
		if(eth.isBroadcast() || eth.isMulticast())
		{
			return Command.CONTINUE;
		}
		else if (pkt instanceof IPv4) {
			
	        IPv4 ip_pkt = (IPv4) pkt;
	        int destIpAddress = ip_pkt.getDestinationAddress();
	        int srcIpAddress = ip_pkt.getSourceAddress();
	        pushBidirectionalVipRoutes(sw, pi, cntx, destIpAddress, srcIpAddress);
	        
	        // packet out based on table rule
	        pushPacket(pkt, sw, pi.getBufferId(), pi.getInPort(), OFPort.OFPP_TABLE.getValue(),
                    cntx, true);

	        return Command.STOP;
		}

		return Command.CONTINUE;
	}
	
	protected void pushBidirectionalVipRoutes(IOFSwitch sw, OFPacketIn pi, FloodlightContext cntx, int destIpAddress, int srcIpAddress) {
		Collection<? extends IDevice> allDevices = deviceManager
                .getAllDevices();
		IDevice srcDevice = null;
        IDevice dstDevice = null;
        for (IDevice d : allDevices) {
            for (int j = 0; j < d.getIPv4Addresses().length; j++) {
                    if (srcDevice == null && srcIpAddress == d.getIPv4Addresses()[j])
                        srcDevice = d;
                    if (dstDevice == null && destIpAddress == d.getIPv4Addresses()[j]) {
                        dstDevice = d;
                    }
                    if (srcDevice != null && dstDevice != null)
                        break;
            }
        }  
        
     // srcDevice and/or dstDevice is null, no route can be pushed
        if (srcDevice == null || dstDevice == null) return;
        
        Long srcIsland = topology.getL2DomainId(sw.getId());

        if (srcIsland == null) {
            /*log.debug("No openflow island found for source {}/{}", 
                      sw.getStringId(), pi.getInPort());
                      */
            return;
        }
        
        // Validate that we have a destination known on the same island
        // Validate that the source and destination are not on the same switchport
        boolean on_same_island = false;
        boolean on_same_if = false;
        for (SwitchPort dstDap : dstDevice.getAttachmentPoints()) {
            long dstSwDpid = dstDap.getSwitchDPID();
            Long dstIsland = topology.getL2DomainId(dstSwDpid);
            if ((dstIsland != null) && dstIsland.equals(srcIsland)) {
                on_same_island = true;
                if ((sw.getId() == dstSwDpid) &&
                        (pi.getInPort() == dstDap.getPort())) {
                    on_same_if = true;
                }
                break;
            }
        }
        if (!on_same_island) {
            // Flood since we don't know the dst device
            
            return;
        }            
        
        if (on_same_if) {
            
            return;
        }
        
     // Install all the routes where both src and dst have attachment
        // points.  Since the lists are stored in sorted order we can 
        // traverse the attachment points in O(m+n) time
        SwitchPort[] srcDaps = srcDevice.getAttachmentPoints();
        Arrays.sort(srcDaps, clusterIdComparator);
        SwitchPort[] dstDaps = dstDevice.getAttachmentPoints();
        Arrays.sort(dstDaps, clusterIdComparator);
        
        int iSrcDaps = 0, iDstDaps = 0;

        // following Forwarding's same routing routine, retrieve both in-bound and out-bound routes for
        // all clusters.
        while ((iSrcDaps < srcDaps.length) && (iDstDaps < dstDaps.length)) {
            SwitchPort srcDap = srcDaps[iSrcDaps];
            SwitchPort dstDap = dstDaps[iDstDaps];
            Long srcCluster = 
                    topology.getL2DomainId(srcDap.getSwitchDPID());
            Long dstCluster = 
                    topology.getL2DomainId(dstDap.getSwitchDPID());

            int srcVsDest = srcCluster.compareTo(dstCluster);
            if (srcVsDest == 0) {
                if (!srcDap.equals(dstDap) && 
                        (srcCluster != null) && 
                        (dstCluster != null)) {
                    Route routeIn = 
                            routingEngine.getRoute(srcDap.getSwitchDPID(),
                                                   (short)srcDap.getPort(),
                                                   dstDap.getSwitchDPID(),
                                                   (short)dstDap.getPort(), 0);
                    Route routeOut = 
                            routingEngine.getRoute(dstDap.getSwitchDPID(),
                                                   (short)dstDap.getPort(),
                                                   srcDap.getSwitchDPID(),
                                                   (short)srcDap.getPort(), 0);

                    // use static flow entry pusher to push flow mod along in and out path
                    // in: match src client (ip, port), rewrite dest from vip ip/port to member ip/port, forward
                    // out: match dest client (ip, port), rewrite src from member ip/port to vip ip/port, forward
                    
                    if (routeIn != null) {
                        pushStaticVipRoute(true, routeIn, srcIpAddress, destIpAddress,cntx);
                    }
                    
                    if (routeOut != null) {
                        pushStaticVipRoute(false, routeOut, srcIpAddress, destIpAddress,cntx);
                    }
                }
                iSrcDaps++;
                iDstDaps++;
            } else if (srcVsDest < 0) {
                iSrcDaps++;
            } else {
                iDstDaps++;
            }
        }
        return;
	}
    /**
     * used to push given route using static flow entry pusher
     * @param boolean inBound
     * @param Route route
     * @param IPClient client
     * @param LBMember member
     * @param long pinSwitch
     */
    public void pushStaticVipRoute(boolean inBound, Route route, int ipFrom, int ipTo,FloodlightContext cntx) {
        List<NodePortTuple> path = route.getPath();
        if (path.size()>0) {
		   //Wildcardc
		   Integer wildcard_hints = null;
		   OFFlowMod fm = (OFFlowMod) floodlightProvider.getOFMessageFactory().getMessage(OFType.FLOW_MOD);
		   //OFMatch
		   OFMatch match = new OFMatch();
		   match.setDataLayerType((short)0x800);
		   match.setNetworkDestination(ipTo);
		   match.setNetworkSource(ipFrom);
		   //OFActions
		   OFActionOutput action = new OFActionOutput();
		   action.setMaxLength((short)0xffff);
		   List<OFAction> actions = new ArrayList<OFAction>();
		   actions.add(action);

		   fm.setIdleTimeout(FLOWMOD_DEFAULT_IDLE_TIMEOUT)
			   .setHardTimeout(FLOWMOD_DEFAULT_HARD_TIMEOUT)
			   .setBufferId(OFPacketOut.BUFFER_ID_NONE)
			   .setCookie((long)0)
			   .setPriority((short)1024)
			   .setCommand(OFFlowMod.OFPFC_ADD)
			   .setMatch(match)
			   .setActions(actions)
			   .setLengthU(OFFlowMod.MINIMUM_LENGTH+OFActionOutput.MINIMUM_LENGTH);
					   	 
           for (int i = path.size()-1 ; i >0 ; i -=2 ) {
               
               long sw = path.get(i).getNodeId();
			   IOFSwitch swnode = floodlightProvider.getSwitch(sw);
			   wildcard_hints = ((Integer) swnode.getAttribute(IOFSwitch.PROP_FASTWILDCARDS)).intValue()
				   			& ~OFMatch.OFPFW_IN_PORT
							& ~OFMatch.OFPFW_NW_SRC_MASK
							& ~OFMatch.OFPFW_NW_DST_MASK
							& ~OFMatch.OFPFW_DL_TYPE;

               fm.setMatch(wildcard(match,swnode,wildcard_hints));
			   fm.getMatch().setInputPort(path.get(i-1).getPortId());
			   ((OFActionOutput)fm.getActions().get(0)).setPort(path.get(i).getPortId());
				
               try {
			   	   messageDamper.write(swnode,fm,cntx);
               } catch (IOException e) {
				   log.info("match string = {}",fm.getMatch().toString());
				   log.info("action string = {}",((OFActionOutput)fm.getActions().get(0)).toString());
               }
			   try {
				   fm = fm.clone();
			   }catch (CloneNotSupportedException e){
					log.error(" clone faile");
				}
					
           }
        }
        return;
    }
    
    
	protected OFMatch wildcard(OFMatch match, IOFSwitch sw,
                               Integer wildcard_hints) {
        if (wildcard_hints != null) {
            return match.clone().setWildcards(wildcard_hints.intValue());
        }
        return match.clone();
    }

    
    /**
     * used to push any packet - borrowed routine from Forwarding
     * 
     * @param OFPacketIn pi
     * @param IOFSwitch sw
     * @param int bufferId
     * @param short inPort
     * @param short outPort
     * @param FloodlightContext cntx
     * @param boolean flush
     */    
    public void pushPacket(IPacket packet, 
                           IOFSwitch sw,
                           int bufferId,
                           short inPort,
                           short outPort, 
                           FloodlightContext cntx,
                           boolean flush) {
        if (log.isTraceEnabled()) {
            log.trace("PacketOut srcSwitch={} inPort={} outPort={}", 
                      new Object[] {sw, inPort, outPort});
        }

        OFPacketOut po =
                (OFPacketOut) floodlightProvider.getOFMessageFactory()
                                                .getMessage(OFType.PACKET_OUT);

        // set actions
        List<OFAction> actions = new ArrayList<OFAction>();
        actions.add(new OFActionOutput(outPort, (short) 0xffff));

        po.setActions(actions)
          .setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);
        short poLength =
                (short) (po.getActionsLength() + OFPacketOut.MINIMUM_LENGTH);

        // set buffer_id, in_port
        po.setBufferId(bufferId);
        po.setInPort(inPort);

        // set data - only if buffer_id == -1
        if (po.getBufferId() == OFPacketOut.BUFFER_ID_NONE) {
            if (packet == null) {
                log.error("BufferId is not set and packet data is null. " +
                          "Cannot send packetOut. " +
                        "srcSwitch={} inPort={} outPort={}",
                        new Object[] {sw, inPort, outPort});
                return;
            }
            byte[] packetData = packet.serialize();
            poLength += packetData.length;
            po.setPacketData(packetData);
        }

        po.setLength(poLength);

        try {
            counterStore.updatePktOutFMCounterStoreLocal(sw, po);
            messageDamper.write(sw, po, cntx, flush);
        } catch (IOException e) {
            log.error("Failure writing packet out", e);
        }
    }
}
