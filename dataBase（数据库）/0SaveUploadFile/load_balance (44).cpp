/**
	Load Balancing Algorithm for Better Response Time using Improved Queuing (BRIQ)	    

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
        double age;

    ClientRequest(int start = 0, int instruction = 0, double age = 0) {
        startTime = start;
        instructionCount = instruction;
        this->age = age;
    }
};

bool clientComparator(const ClientRequest& firstRequest, const ClientRequest& secondRequest) {
    return (firstRequest.startTime < secondRequest.startTime);
}

bool sjfOrderingComparator(const ClientRequest& firstRequest, const ClientRequest& secondRequest) {
    return ((firstRequest.instructionCount - firstRequest.age) < (secondRequest.instructionCount - secondRequest.age));
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
    vector<ClientRequest> allotedRequest[vmCount];

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

    // Allot requests
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
        vmFinishTime[leastFinishIndex] = leastFinishTime;
        allotedRequest[leastFinishIndex].push_back(clientRequests[i]);
	}

    // SJF + Ageing
    double totalResponse = 0;
    vector<double> throughput;
    for(int i = 0; i < vmCount; ++i) {
        double timer = 0;
        for(int j = 0; j < allotedRequest[i].size(); ++j) {
            timer = max(timer, double(allotedRequest[i][j].startTime)) + (allotedRequest[i][j].instructionCount * 1.0) / VMs[i].capacity;
            totalResponse += (timer - allotedRequest[i][j].startTime);
            throughput.push_back((timer - allotedRequest[i][j].startTime));
            int beginSort = j + 1, endSort = j + 1;
            for(int k = j + 1; k < allotedRequest[i].size(); ++k) {
                if(allotedRequest[i][k].startTime <= timer) {
                    endSort = k;
                    allotedRequest[i][k].age = (timer - allotedRequest[i][k].startTime);
                }
            }
            sort(allotedRequest[i].begin() + beginSort, allotedRequest[i].begin() + endSort + 1, sjfOrderingComparator);
        }
    }
    sort(throughput.begin(), throughput.end());

    // Calculate response time
    double netResponse = totalResponse / (clientRequestCount * 1.0);
    cout << "Net Response Time: " << fixed << setprecision(6) << netResponse << endl;
    cout << "Response Throughput: " << fixed << setprecision(6) << throughput[clientRequestCount / 2] << endl;
    return 0;
}
