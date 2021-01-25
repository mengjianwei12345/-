import subprocess

def _cmd_execute(self, cmd):
    '''
      wrapper for Popen
    '''

    DEV_LOGGER.debug('Detail="Executing SSLH IP Command = %s"', cmd)

    try:
        output = subprocess.check_output(cmd, stderr=subprocess.PIPE)

        self.alarm_manager.lower_alarm(SSLH_ALARM)
        return output
    except subprocess.CalledProcessError:
        ADMIN_LOGGER.error('Detail="SSLH Failure" Reason="IP table command error %s"', cmd)
        self.alarm_manager.raise_alarm(SSLH_ALARM)
        DEV_LOGGER.exception('Detail="SSLH Failure" Reason="IP table command error %s"', cmd)


def update_ip_table_rules(self, startport, interface):
	endport = startport + 5

	#delete existing nat prerouting rules
    p = subprocess.Popen(["iptables", "-t", "nat", "-F", "PREROUTING"], stdout=subprocess.PIPE)
    output = p.communicate()[0]
    
	#add new nat prerouting rules
    index = endport-startport+1
    for port in range(startport,endport):
        p = subprocess.Popen(["iptables", "-t", "nat", "-A", "PREROUTING","-p","tcp","-i",interface,"--dport",str(startport),"-m","statistic","--mode","nth","--every", str(index),"--packet","0","-j","REDIRECT","--to-port", str(port)], stdout=subprocess.PIPE)
        p.communicate()
        index = index - 1

    p = subprocess.Popen(["iptables", "-t", "nat", "-A", "PREROUTING", "-p","tcp","-i",interface,"--dport",str(startport),"-j","REDIRECT","--to-port", str(endport)], stdout=subprocess.PIPE)
    p.communicate()
    
    index = endport-startport+1
    for port in range(startport,endport): 
        p = subprocess.Popen(["iptables", "-t", "nat", "-A", "PREROUTING","-p","udp","-i",interface,"--dport",str(startport),"-m","statistic","--mode","nth","--every", str(index),"--packet","0","-j","REDIRECT","--to-port", str(port)], stdout=subprocess.PIPE)
        p.communicate()
        index = index - 1

    p = subprocess.Popen(["iptables", "-t", "nat", "-A", "PREROUTING", "-p","udp","-i",interface,"--dport",str(startport),"-j","REDIRECT","--to-port", str(endport)], stdout=subprocess.PIPE)
    p.communicate()

startport = 3478
interface = "eth0"

update_ip_table_rules(startport,interface)
