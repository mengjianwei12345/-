#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include        "read_config.h"
#include        "common.h"
#include        "server_config.h"
#include        "client_config.h"
#include        "lbconfig.h"
#include        "Log/listener.h"

class LB{
private:
  //Set the variable if show message
  int SIGNAL_IF_SHOW_MESSAGE;
  //Set the variable fork id
  int					forid;
  //Set the socket client and server 
  int                 cup_sockfd,sup_sockfd;//对应client_udp_port ，server_udp_port的文件描述符
  char		mesg[MAXLINE];
  //Set the socklen_t length
  socklen_t    clilen ;
  //Set the listener to listen if show the detail message
  listener li;
  //Set the tmp to store 
  char tmp_str[2000];
  //Set the statistics
  Statistics statistics;
  //Set the thread variable
  pthread_t  thread_id;
  pthread_attr_t pthread_attr;

  //Set the Intormation
  struct Information{
    t_msg msg;
    struct sockaddr_in	servaddr;
    SA * servaddrs;
    SA *pcliaddr;
    int serverID;
    void *l_b;
  };

public:
  map<int,int>map_sockfd;
  vector<int>vec_port;
  int client_udp_port ;
  int server_udp_port ;
  int num_pid;
  int* server_ports;
  int* server_vis;
  int* ServerID;

  LB();
  ~LB(){
    delete server_vis;
    delete server_ports;
    delete ServerID;
    close(cup_sockfd);
    close(sup_sockfd);
  }

  void init(int port);
  void dg_echo();
  void Cycle(Information inf);
  void sg_echo(Information inf);
  void send_to_client(Information inf,int sockfd);
  void output_log(char *content);
  static void* Cycles(void *args);
};

LB::LB(){
  //  li.create_listen_thread(&statistics);
  SIGNAL_IF_SHOW_MESSAGE = 1;
  li.create_listen_thread(&SIGNAL_IF_SHOW_MESSAGE);
}

void LB::output_log(char *content){
  if(SIGNAL_IF_SHOW_MESSAGE){
    cout<<endl;
    cout<<"-------------------------------------------------------------------------------"<<endl;
    cout<<content<<endl;
    cout<<"-------------------------------------------------------------------------------"<<endl;
  }
}

void LB::send_to_client(Information inf,int sockfd){//将应答返回给客户端
  sprintf(tmp_str,"time will send to client. value is: %s",inf.msg.data);
  this->output_log(tmp_str);

  int n = sendto(sockfd,&inf.msg, MAXLINE, 0, inf.pcliaddr, clilen);

  if (n < 0){
    sprintf(tmp_str,"Time send error sendto return value is:%d\n",n);
    this->output_log(tmp_str);
  }else{
    sprintf(tmp_str,"time is sended successfully");
    this->output_log(tmp_str);
  }
}

void LB::init(int port){ //初始化 两个udp端口
  struct sockaddr_in	servaddr,cliaddr;
  int sizes = 7000;
  clilen = sizeof(cliaddr);
  bzero(&servaddr, sizeof(servaddr));
  servaddr.sin_family      = AF_INET;
  servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
  servaddr.sin_port        = htons(port);

  if(port == client_udp_port){
    cup_sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    bind(cup_sockfd, (SA *) &servaddr, sizeof(servaddr));
  }
  else {
    sup_sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    bind(sup_sockfd, (SA *) &servaddr, sizeof(servaddr));
  }



  cout<<"port "<<port<<" is work"<<endl;

  if(port == client_udp_port)dg_echo();
}


void LB::Cycle(Information inf){// 轮转调度一个服务器

  int forkid,ii,ports = -1;
  struct timeval tv;


  while(1){
    for(int i=0; i<num_pid;i++){
      if(!server_vis[i]){
	ii = i;
	ports = ServerID[i];
	break;
      }
    }
    if(ports!=-1)break;
    if(ports==-1)sleep(10);//无服务器等待

  }


  server_vis[ii]=1;

  sprintf(tmp_str,"LB choose the %d server its port is:%d\n",ports,map_sockfd[ports]);
  this->output_log(tmp_str);

  inf.msg.dst_id = ServerID[ii];
  bzero(&inf.servaddr, sizeof(inf.servaddr));
  inf.servaddr.sin_family      = AF_INET;
  inf.servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
  inf.servaddr.sin_port        = htons(map_sockfd[ports]);
  inf.serverID = ii;
  inf.servaddrs = (SA *)&inf.servaddr;
  sg_echo(inf);

}


void* LB::Cycles(void *args){
  struct Information *inf;
  inf = (struct Information *)args;
  LB* l_b = (LB *)inf->l_b;

  l_b->Cycle(*inf);
}
void LB::sg_echo(Information inf){ //发送给一个服务器
  sprintf(tmp_str,"Send a request to one server and choose a server");
  this->output_log(tmp_str);

  fd_set readfds;
  struct timeval tv;
  int sizes = 7000;

  setsockopt(sup_sockfd, SOL_SOCKET, SO_SNDBUF, &sizes, sizeof(sizes));


  int n = sendto(sup_sockfd,&inf.msg, MAXLINE, 0,inf.servaddrs, clilen);

  if (n < 0){

    sprintf(tmp_str,"Send a request to one server and choose a server");
    this->output_log(tmp_str);
  }

  while(1){
    FD_ZERO(&readfds);

    // add our descriptors to the set
    FD_SET(sup_sockfd, &readfds);
    //  FD_SET(s2, &readfds);

    // wait until either socket has data ready to be recv()d (timeout 10.5 secs)
    tv.tv_sec = 10;
    tv.tv_usec = 500000;

    int rv = select(sup_sockfd+1, &readfds, NULL, NULL, &tv);

    /* if (rv == -1) {
       perror("select"); // error occurred in select()
       } else if (rv == 0) {
       printf("Timeout occurred!  No data after 10.5 seconds.\n");
       } */
    if(rv > 0){
      recvfrom(sup_sockfd, &inf.msg, MAXLINE, 0, inf.servaddrs, &clilen);
      break;
    }
  }
  send_to_client(inf,cup_sockfd);//结果返回个客户端
  sprintf(tmp_str,"Get the time from server its value:%s",inf.msg.data);
  this->output_log(tmp_str);

  server_vis[inf.serverID]=0;

  sprintf(tmp_str,"%d server is ready ",inf.serverID);
  this->output_log(tmp_str);
}

void
LB::dg_echo()//接收客户端请求
{

  socklen_t	len;
  char		mesg[MAXLINE];
  int size = 7000;
  struct sockaddr_in servaddr;
  SA * pcliaddr;
  //	signal(SIGINT, recvfrom_int);
  setsockopt(cup_sockfd, SOL_SOCKET, SO_SNDBUF, &size, sizeof(size));

  for ( ; ; ) {
    len = clilen;
    t_msg clientmsg;
    recvfrom(cup_sockfd, &clientmsg, MAXLINE, 0, pcliaddr, &len);
    sprintf(tmp_str,"LB accepted client's request:\n client dist_id:%d-----LB's id:%d-----message's type:%d-----messages'id:%d",clientmsg.dst_id,LBID,clientmsg.msg_type,clientmsg.src_id);
    this->output_log(tmp_str);

    struct Information *infs ;
    struct Information inf;
    infs = (struct Information *)malloc(sizeof(struct Information));

    infs->pcliaddr = pcliaddr;
    infs->serverID = -1;
    infs->msg = clientmsg;
    infs->servaddr = servaddr;
    infs->l_b = this;
    inf = *infs;
    if(clientmsg.dst_id != LBID){//消息中的dst_id不等于自己的id

      strcpy(inf.msg.data,"abondon");

      send_to_client(inf,cup_sockfd);
    }
    else {//如果消息中的dst_id等于自己的id

      //  clientmsg.dst_id =
      pthread_attr_init (&this->pthread_attr);
      pthread_attr_setdetachstate (&this->pthread_attr, PTHREAD_CREATE_DETACHED);
      int err;
      //
      err = pthread_create(&this->thread_id,&this->pthread_attr,Cycles,(void *)infs);
      if(err != 0){ //error
	printf("can't create thread: %s\n",strerror(err));
      }

    }
    //	int n = sendto(sockfd, ctime(&timer), MAXLINE, 0, pcliaddr, clilen);

  }
}



int main(){
  // freopen ("myfiles.txt","w",stdout);
  LB l_b;

  server_config *se = new server_config();
  se->deal_data();

  l_b.num_pid = se->get_num_pid();
  l_b.ServerID = se->get_id();
  l_b.server_vis = (int *)malloc(sizeof(int)*l_b.num_pid);

  memset(l_b.server_vis,0,sizeof(int)*l_b.num_pid);

  l_b.server_ports = se->get_port();
  lb_config *lb = new lb_config();
  lb->deal_data();
  l_b.server_udp_port = lb->get_server_udb_port();
  l_b.client_udp_port = lb->get_client_udb_port();

  for (int i = 0;i < l_b.num_pid;i++){
    l_b.map_sockfd[l_b.ServerID[i]]= l_b.server_ports[i];
  }

  l_b.init(l_b.server_udp_port);
  l_b.init(l_b.client_udp_port);
}


