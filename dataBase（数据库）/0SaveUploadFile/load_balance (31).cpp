/**
	Load Balancing Algorithm Based on Estimating
   	Finish Time of Services in Cloud Computing
	
	Link: https://ieeexplore.ieee.org/document/7423340?arnumber=7423340

    	@author Aditi Gupta
    	@author Dibyadarshan Hota
    	@author Annappa .B 
*/

#include <bits/stdc++.h>
using namespace std;

#define INF 1000000000

class ClientRequest {
    public:
        int startTime;
        int instructionCount;

    ClientRequest(int start = 0, int instruction = 0) {
        startTime = start;
        instructionCount = instruction;
	}
};

bool clientComparator(const ClientRequest& firstRequest, const ClientRequest& secondRequest) {
    return (firstRequest.startTime < secondRequest.startTime);
}

class VirtualMachine {
    public:
        int coreCount;
        double capacity;
        vector<int> clockSpeed;

    VirtualMachine(int cores = 0, const vector<int>& speed = vector<int>()) {
        coreCount = cores;
        clockSpeed = speed;
        int totalCapacity = 0;
        for(auto itr: clockSpeed) totalCapacity += itr;
        capacity = (totalCapacity * 1.0) / coreCount;
    }
};

int main() {

    // Initialization
    int vmCount, clientRequestCount;
    cin >> vmCount >> clientRequestCount;

    vector<VirtualMachine> VMs(vmCount);
    vector<double> vmFinishTime(vmCount, 0);

    vector<ClientRequest> clientRequests(clientRequestCount);
    
    for(int i = 0; i < vmCount; ++i) {
        int coreCount;
        cin >> coreCount;
        vector<int> clockSpeed(coreCount);
        for(int j = 0; j < coreCount; ++j) {
            cin >> clockSpeed[j];
        }
        VirtualMachine VM(coreCount, clockSpeed);
        VMs[i] = VM;
    }
    
    for(int i = 0; i < clientRequestCount; ++i) {
        int startTime, instructionCount;
        cin >> startTime >> instructionCount;
        ClientRequest request(startTime, instructionCount);
        clientRequests[i] = request;
    }

    // Sort requests by time
    sort(clientRequests.begin(), clientRequests.end(), clientComparator);

    // Allot requests and process them in same order (FIFO)
    double totalResponse = 0;
    vector<double> throughput;
    for(int i = 0; i < clientRequestCount; ++i) {
		int leastFinishIndex = 0;
		double leastFinishTime = INF;
        for(int j = 0; j < vmCount; ++j) {
            double finishTime = max(vmFinishTime[j], double(clientRequests[i].startTime)) + (clientRequests[i].instructionCount * 1.0) / VMs[j].capacity;
            if(finishTime < leastFinishTime) {
                leastFinishTime = finishTime;
                leastFinishIndex = j;
            }
        }
        totalResponse += (leastFinishTime - clientRequests[i].startTime);
        vmFinishTime[leastFinishIndex] = leastFinishTime;
        throughput.push_back((leastFinishTime - clientRequests[i].startTime));
	}
    sort(throughput.begin(), throughput.end());

    // Calculate response time
    double netResponse = totalResponse / (clientRequestCount * 1.0);
    cout << "Net Response Time: " << fixed << setprecision(6) << netResponse << endl;
    cout << "Response Throughput: " << fixed << setprecision(6) << throughput[clientRequestCount / 2] << endl;
    return 0;
}
