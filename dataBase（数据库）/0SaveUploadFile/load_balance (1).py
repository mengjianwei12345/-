'''
Author: Nanak Tattyrek, Patrick Muehl
Implementierung des LoadBalancers
'''
from http.server import HTTPServer
from http.server import BaseHTTPRequestHandler
import time
import threading
import requests
import datetime as date

#IPs und Ports von LoadBalancer und Servern werden festgelegt
balancer_host = '127.0.0.1'
balancer_port = 12345
server_addr = 'http://127.0.0.1'
server_port = '8000'
server = server_addr+":"+server_port
useMethod = None
globalTime = [0,0,0,0]

server_port1 = '8001'
server_port2 = '8002'
server_port3 = '8003'
servers = [server, server_addr+":"+server_port1, server_addr+":"+server_port2, server_addr+":"+server_port3]

fastestServer = None

#Diese Klasse behandelt die Weiterleitung der Anfragen an die Webserver
class RedirectHandler(BaseHTTPRequestHandler):
	#Diese Methode wird verwendet wenn ServerProbes gewuenscht ist
	def do_HEAD_ServerProbes(s):
		s.send_response(301)
		s.send_header("Location", fastestServer)
		print("Server "+fastestServer)
		s.end_headers()
	#Diese Methode wird verwendet wenn ResponseTime gewuenscht ist
	def do_HEAD_ResponseTime(s):
		index = globalTime.index(min(globalTime))
		starttime = time.time()
		s.send_response(301)
		s.send_header("Location", servers[index])
		print("Server "+servers[index])
		s.end_headers()
		endtime = time.time()
		globalTime[index] = endtime - starttime
	#do_GET() wird vom Server Element des Loadbalancers aufgerufen.
	#hier verzweigt die Entscheidung welche Art von LoadBalancing gewuenscht ist.
	def do_GET(s):
		if useMethod == 1:
			s.do_HEAD_ServerProbes()
		if useMethod == 2:
			s.do_HEAD_ResponseTime()

#Diese Klasse definiert die Funktionalitaet des Threads fuer ServerProbes
class ThreadClass(threading.Thread):
	def run(self):
		while True:
			response = date.timedelta(0,1,1)
			for server in servers[:]:
				newResponse = requests.get(server).elapsed
				if (newResponse < response):
					global fastestServer
					fastestServer = server
				response = newResponse
			time.sleep(0.5)

#Wird bei aufruf des Programms ausgefuehrt
if __name__ == '__main__':
	useMethod = int(input("Type \'1\' for Server Probes or \'2\' for Response Time: "))
	#hier wird der Thread fuer ServerProbes gestartet,
	#wenn diese Art gewaehlt wurde.
	if useMethod == 1:	
		t = ThreadClass()
		t.daemon = True
		t.start()

	serverClass = HTTPServer
	httpd = serverClass((balancer_host, balancer_port), RedirectHandler)
	print(time.asctime(), "Server starting - %s:%s" % (balancer_host, balancer_port))
	try:
		httpd.serve_forever()
	except KeyboardInterrupt:
		pass
	httpd.server_close()
	print(time.asctime(), "Server starting - %s:%s" % (balancer_host, balancer_port))