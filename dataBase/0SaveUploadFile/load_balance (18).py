import socket

myserver=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
myserver.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
mysock1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = ' '
port = 5555 
size = 512
a = ['192.168.1.186', '192.168.1.187'] #these are the ip addresses of downstream servers
addr = (host,port) 
addr2 = (a[0], port)
addr3 = (a[1], port)
myserver.bind(addr) 
myserver.listen(10) # Loadbalancer listening on specific port and IP address. 
counter = 0
var = 0

def count(counter):
                  if counter == 2:
                      return (0)
                  else:
                       counter = counter + 1
                       return counter


try:
    while True:
             if  var == 2:
                 var = count(var)
             client, addr = myserver.accept()
             print ("Connection from client : ", addr[0])
             data = client.recv(size)
             if var == 0:
                        mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        mysock.connect(addr2)
                        mysock.sendall(data)
                        var = count(counter)
                        print ("connected to first server")
             elif var == 1:
                        mysock1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        mysock1.connect(addr3)
                        mysock1.sendall(data)
                        var = count(var)
                        print ("connected to second server")          

finally:
    mysock.close()
    mysock1.close()
    myserver.close()
    
