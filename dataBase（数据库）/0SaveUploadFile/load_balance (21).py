#!/usr/bin/python

from daemon import Daemon
from datetime import *
import commands, time, sys, os.path

def balance(nodes, host, user, pasw, log_balancer, log_path):
    for passloop in range(1, int(nodes) + 1):
	# get pool host uuids
	uuidlist = commands.getoutput('xe host-list -s %s -pw "%s" -u %s params=uuid host-metrics-live=true enabled=true --minimal' % (host, pasw, user))
	# put the pool host uuids into an array
	huuid = uuidlist.split(',')
	# check for only one operating host
	if len(huuid) != 1:
	    # get percentage of memory free on all hosts and put in array with matching index
	    mlist = {}
	    for item in huuid:
		# get host memory free
		mfree = commands.getoutput('xe host-param-get -s %s -pw "%s" -u %s uuid=%s param-name=memory-free host-metrics-live=true' % (host, pasw, user, item))
		# get host memory total
		mtotal = commands.getoutput('xe host-param-get -s %s -pw "%s" -u %s uuid=%s param-name=memory-total host-metrics-live=true' % (host, pasw, user, item))
		# calculate percentage of memory free
		mempercent = 100*int(mfree)/int(mtotal)
		# check for single digit percent
		mlist[item] = mempercent
	    # get the host with highest percentage of memory free
	    highper = max(mlist.values())
	    highuuid = max(mlist, key = mlist.get)
	    mlist_new = mlist
	    del mlist_new[highuuid]
	    highuuid2 = max(mlist_new, key = mlist_new.get)
	    hstname = commands.getoutput('xe host-list -s %s -pw "%s" -u %s params=name-label uuid=%s --minimal' % (host, pasw, user, highuuid))
	    # sort for host with lowest percentage of memory free
	    lowper = min(mlist.values())
	    lowuuid = min(mlist, key = mlist.get)
	    hstname = commands.getoutput('xe host-list -s %s -pw "%s" -u %s params=name-label uuid=%s --minimal' % (host, pasw, user, lowuuid))
	    # figure percentage difference between highest and lowest
	    diffper = highper - lowper
	    # if the difference is greater than 10, we need to rebalance, otherwise exit
	    if diffper > 10:
		# get memory usage of all VMs on the host with the lowest percentage of mem free
		# get all VM uuids on the lowest free memory host
		# get all VMs with up to date PV drivers
		uuidlist = commands.getoutput('xe vm-list -s %s -pw "%s" -u %s params=uuid resident-on=%s is-control-domain=false PV-drivers-up-to-date=true --minimal' % (host, pasw, user, lowuuid))
		# Get all VMs with out of date PV drivers
		uuidlist1 = commands.getoutput('xe vm-list -s %s -pw "%s" -u %s params=uuid resident-on=%s is-control-domain=false PV-drivers-up-to-date="<not in database>" --minimal' % (host, pasw, user, lowuuid))
		# check to make sure that there is at least 1 moveable VM
		vuuid = uuidlist.split(',')
		# check to see if there are 2 or more migratable VMs
		if uuidlist != '':
		    # check for at least 1 non-migratable VM
		    if len(vuuid) > 2 or (len(vuuid) == 1 and uuidlist1 != ''):
			# get the actual memory used by each VM and put in dictionary with matching index
			mlist = {}
			for item in vuuid:
			    mactual = commands.getoutput('xe vm-param-get -s %s -pw "%s" -u %s uuid=%s param-name=memory-actual' % (host, pasw, user, item))
			    mlist[item] = mactual
			# get the lowest memory usage VM
			vmlowuuid = min(mlist, key = mlist.get)
			# check to make sure that low usage VM can actually be migrated to high mem free host
			possible = commands.getoutput('xe vm-param-get -s %s -pw "%s" -u %s uuid=%s param-name=possible-hosts | grep "%s"' % (host, pasw, user, vmlowuuid, highuuid))
			if possible:
			    # check to see if there's an unfriend
			    # get unfriend record
			    unfrnd = commands.getoutput('grep UNFRND dependencies.cfg | grep -m 1 %s' % vmlowuuid)
			    if unfrnd:
				# not blank so we have a real record
				unfrnd_tst = unfrnd.split()[0]
				if unfrnd_tst == vmlowuuid:
				    unfrnd_uuid = unfrnd.split()[1]
				else:
				    unfrnd_uuid = unfrnd.split()[0]
				# check to see if UNFRND_UUID is running
				unfrnd_state = commands.getoutput('xe vm-param-get -s %s -pw "%s" -u %s uuid=%s param-name=power-state' % (host, pasw, user, unfrnd_uuid))
				if unfrnd_state == 'running':
				    # check to see if UNFRND_UUID is running on HOST_UUID
				    unfrnd_host_uuid = commands.getoutput('xe vm-param-get -s %s -pw "%s" -u %s uuid=%s param-name=resident-on' % (host, pasw, user, unfrnd_uuid))
				    if highuuid == unfrnd_host_uuid:
					# pick the next lowest memory host instead
					highuuid = highuuid2
			    # live migrate VM from low memory free host to high memory free host
			    commands.getoutput('xe vm-migrate -s %s -pw "%s" -u %s vm=%s host-uuid=%s live=true' % (host, pasw, user, vmlowuuid, highuuid))
			    # check for LOG_BALANCER=YES
			    if log_balancer == 'yes':
				logpath = os.path.join(log_path, 'xccs.log')
				log = open(logpath, 'a')
				log1 = commands.getoutput('xe vm-param-get -s %s -pw "%s" -u %s uuid=%s param-name=name-label --minimal' % (host, pasw, user, vmlowuuid))
				log2 = commands.getoutput('xe host-param-get -s %s -pw "%s" -u %s uuid=%s param-name=name-label --minimal' % (host, pasw, user, highuuid))
				log.write('%s load-balancer load-balancer.py move %s %s\n' % (datetime.today(), log1, log2))
				log.close()
			    # delay to allow host memory changes to settle
			    time.sleep(15)
			else:
			    break
		    else:
			break
		else:
		    break
	    else:
		break
	else:
	    break

class MyDaemon(Daemon):
    def run(self):
	# read variables from cloud.cfg
	config = open('/usr/local/etc/cloud.cfg', 'r')
	for line in config.xreadlines():
	    if line.split()[0] == 'NODES':
		nodes = line.split()[1]
	    elif line.split()[0] == 'HOST':
		host = line.split()[1]
	    elif line.split()[0] == 'USER':
		user = line.split()[1]
	    elif line.split()[0] == 'PASS':
		pasw = line.split()[1]
	    elif line.split()[0] == 'LOG_BALANCER':
		log_balancer = line.split()[1]
	    elif line.split()[0] == 'LOG_PATH':
		log_path = line.split()[1]
	config.close()
	while True:
	    balance(nodes, host, user, pasw, log_balancer, log_path)
	    time.sleep(900)

if __name__ == "__main__":
    daemon = MyDaemon('/tmp/load-balancer.pid')
    if len(sys.argv) == 2:
	if 'start' == sys.argv[1]:
	    daemon.start()
	elif 'stop' == sys.argv[1]:
	    daemon.stop()
	elif 'restart' == sys.argv[1]:
	    daemon.restart()
	else:
	    print "Unknown command"
	    sys.exit(2)
	sys.exit(0)
    else:
	print "usage: %s start|stop|restart" % sys.argv[0]
	sys.exit(2)