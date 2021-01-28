#include"sysutil.h"

// ./loadbalance
//#define LOG

int main(int argc, char *argv[])
{
	if(getuid() != 0)
	{
		fprintf(stderr, "miniftpd: must be started as root\n");
		exit(EXIT_FAILURE);
	}

#ifdef LOG
	log_init(LL_DEBUG, "loadbalance_log", "./log");
#endif

	if(argc <= 1)
	{
		LOG_ERROR("cmd argument too less, please help.");
		return -1;
	}
	
	char cfgfile[FILE_NAME_MAX_SIZE];
	memset(cfgfile, 0, FILE_NAME_MAX_SIZE);

	ParseCmdArg(argc, argv, cfgfile);
	LOG_TRACE("Parse Command Argument Finish.");
	//printf("cfg_file : %s\n", cfgfile);

	//char *cfgfilebuf = NULL;
	//LoadCfgFile(cfgfile, cfgfilebuf);
	//printf("%s", cfgfilebuf);

	vector< host > balance_srv;
    vector< host > logical_srv;
	if(ParseCfgFile(cfgfile, balance_srv, logical_srv) < 0)
		ERR_EXIT("ParseCfgFile");
	
	if( balance_srv.size() == 0 || logical_srv.size() == 0 )
	{
		LOG_ERROR("%s", "parse config file failed." );
		return 1;
	}

	LOG_TRACE("Parse ConfigFile Finish.");
	/////////////////////////////////////////////////////////////////////////////////////
	
	int listenfd = tcp_server(balance_srv[0].m_hostname, balance_srv[0].m_port);
	
	//进程池的创建 单件设计模式
	processpool<conn, host, mgr> *pool = processpool<conn, host, mgr>::create(listenfd,logical_srv.size());
	if(pool)
	{
		pool->run(logical_srv);
		delete pool;
	}

	close(listenfd);
	return 0;
}