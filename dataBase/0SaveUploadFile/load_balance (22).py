import json
import sys
import os.path
from os import path,remove
from heapq import heapify, heappush, heappop

BASE_FILE_PATH = 'Files/'
SERVER_FILE_PATH = BASE_FILE_PATH + 'server.txt' # File used for saving current state of MaxHeap
INITIAL_FILE_PATH = BASE_FILE_PATH + 'initial.txt' # File used to restore initial state of MaxHeap
RESULT_FILE_PATH = BASE_FILE_PATH + 'result.txt' # File used to save the console output


class MaxHeap:
    def __init__(self):
        self.heap = []
    
    def add(self, element):
        heappush(self.heap,element)

    def getMax(self):
        return self.heap[0]
    
    def deleteMax(self):
        element = heappop(self.heap)
        return element
    
    def getHeap(self):
        return self.heap

    def isEmpty(self):
        return True if len(self.heap) ==0 else False

def read_file(file_name):
    content = []
    with open(file_name, 'r') as reader:
        content.append(reader.read())
    res = ''
    if content is not None and len(content) > 0:
        res = json.loads(content[0])
    return res

def write_file(file_name, content):
    if path.exists(file_name):remove(file_name)
    content = json.dumps(content)
    with open(file_name, 'w') as writer:
        writer.write(content)

def append_file(file_name, content):
    with open(file_name, 'a+') as writer:
        writer.write(content)

def delete_file(file_name):
    remove(file_name)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print('\nNo arguments passed!! Please add some arguments\n')
        print('1. Reset the load balancer to change the load balancer configuration')
        print('     $ LoadBalancing.py reset')
        print('2. Initililze or run the load balancer')
        print('     $ LoadBalancing.py X:1 Y:3')
    else:
        arguments = sys.argv
        if 'reset' == arguments[1]:
            write_file(INITIAL_FILE_PATH, [])
            write_file(SERVER_FILE_PATH,[])
            delete_file(RESULT_FILE_PATH)
        else:
            max_heap = MaxHeap()
            # check if initial.txt is empty
            if path.exists(INITIAL_FILE_PATH):
                initial_load_balancer_details = read_file(SERVER_FILE_PATH)
            if initial_load_balancer_details is None or len(initial_load_balancer_details) == 0:
                    server_data = arguments[1:]
                    for curr in server_data:
                        x, y = curr.split(':')
                        max_heap.add([-int(y), x])
                    write_file(INITIAL_FILE_PATH, max_heap.getHeap())
                    x, y = max_heap.deleteMax()
                    print(y)
                    append_file(RESULT_FILE_PATH, y+"\n")
                    x+=1
                    if x < 0:
                        max_heap.add([x,y])
                    write_file(SERVER_FILE_PATH, max_heap.getHeap())
            else:
                if path.exists(SERVER_FILE_PATH):
                    load_balancer_details = read_file(SERVER_FILE_PATH)
                if load_balancer_details is None or len(load_balancer_details) == 0: # check if server file is not empty
                    server_data = read_file(INITIAL_FILE_PATH)
                    for curr in server_data:
                        x, y = curr.split(':')
                        max_heap.add([-int(y), x])
                    x, y = max_heap.deleteMax()
                    print(y)
                    append_file(RESULT_FILE_PATH, y+"\n")
                    x+=1
                    if x < 0:
                        max_heap.add([x,y])
                    write_file(SERVER_FILE_PATH, max_heap.getHeap())
                else:
                    server_data = read_file(SERVER_FILE_PATH)
                    for curr in server_data:
                        x, y = curr
                        max_heap.add([x, y])
                    x, y = max_heap.deleteMax()
                    print(y)
                    append_file(RESULT_FILE_PATH, y+"\n")
                    x+=1
                    if x < 0:
                        max_heap.add([x,y])
                    write_file(SERVER_FILE_PATH,max_heap.getHeap())