#include <iostream>
#include <mpi.h>
#include <ctime>
#include <unistd.h>
#include <fstream>
#include <complex>
#include <vector>
#include <chrono>
#include <string>
#include <queue>

#define MCW MPI_COMM_WORLD
#define MASTAH 0

using namespace std;

int main(int argc, char **argv){
	queue<int> myqueue;
	int pid, np, data, iterations;
	int flag = 0;

	MPI_Init(&argc, &argv);MPI_Comm_rank(MCW, &pid);MPI_Comm_size(MCW, &np);
	bool terminate = false;
	srand(pid);
	int maxTasksGenerated = rand()%2048 + 1024;
	int tasksGenerated = 0;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	if(pid == 0){
		// generate first task
		int task = rand()%1024 + 1;
		myqueue.push(task);
	}

	while(!terminate){
		// listen for any new tasks
		MPI_Iprobe(MPI_ANY_SOURCE, 1, MCW, &flag, MPI_STATUS_IGNORE);
		if(flag){
			MPI_Recv(&data, 1, MPI_INT, MPI_ANY_SOURCE, 1, MCW, MPI_STATUS_IGNORE);
			cout << pid << " got something!\n";
			flag = 0;
		}
		// do work if there is any on the queue
		if(!myqueue.empty()){
			int task = myqueue.front();
			myqueue.pop();
			for(int i = 0; i < task*task; i++){}
			// cout << "Process " << pid <<  " is doing some work " << endl;
		}

		// add tasks to myqueue
		if(tasksGenerated < maxTasksGenerated){
			int numberOfTasksToCreate = rand()%3+1;
			for(int i=0; i<numberOfTasksToCreate; i++){
				myqueue.push(rand()%1024 + 1);
			}
			tasksGenerated += numberOfTasksToCreate;
		}

		// send out tasks if queue size is greater than 16
		while(myqueue.size() > 16){
			data = myqueue.front();
			int receivingProcess = pid;
			while(receivingProcess == pid){receivingProcess = rand()%(np-1);}
			MPI_Send(&data, 1, MPI_INT, receivingProcess, 1, MCW);
			myqueue.pop();
		}

		//cout << "I am pid " << pid << " and I have " << myqueue.size() << " tasks on my queue.\n";
		if(myqueue.size() == 0 && tasksGenerated >= maxTasksGenerated){
			terminate = true;

			cout << "Process " << pid << " is done and generated " << tasksGenerated << " new tasks in total\n";
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 	MPI_Finalize();
	return 0;
}
