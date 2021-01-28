#include <iostream>
#include <mpi.h>
#include <unistd.h>
#include <bits/stdc++.h> 
#include <stdlib.h>
#include <time.h>
#include <chrono> 

#define MCW MPI_COMM_WORLD

using namespace std;
using namespace std::chrono; 


// TODO: Get dual pass ring termination working
// TODO: Get random send and receive working
// TODO: Auto generate numbers and do auto send and receive

void printTokenData(int rank, int size, int token, int special, int work_performed) {
  string print_token;
  switch(token) {
    case 2: print_token = "White"; break;
    case 1: print_token = "Black"; break;
    case 0: print_token = "Terminate"; break;
  }
  
  cout << "Rank    : " << rank << " / " << size-1
       << "      Token   : " << print_token
       << "      Special : " << special
       << "      Work Perf: " <<  work_performed
       << endl;
  sleep(1);
}

void print(string toPrint) {
  cout << toPrint << endl;
}
void print(string toPrint, int pInt) {
  cout << toPrint << ": " << pInt << endl;
}
void print(int rank, string toPrint, int pInt) {
  cout << "Rank " << rank << " " << toPrint << ": " << pInt << endl;
}


int main(int argc, char **argv) {

  int rank, size;
  int data;
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MCW, &rank);
  MPI_Comm_size(MCW, &size);
  MPI_Status token_status;
  MPI_Status work_status;
  srand(time(0));

  const int TOKEN_TAG = 0;
  const int WORK_TAG = 1;

  stack <int> work;
  int incoming_work[2];
  int work_to_generate = rand() % 1024 + 1024;
  const int WORK_TO_PERFORM = 1;
  const int WORK_THRESHHOLD = 16;
  int work_performed = 0;

  int special = 0;
  int token = 2;  // 2 is white, 1 is black, 0 is terminate
  int tokenStarted = false;
  
  int token_flag;
  int work_flag;

  // <<<<<<<<<<<<<<<<<<<<<<<< Seed the Program >>>>>>>>>>>>>>>>>>>>>>>>>>>
  if(!rank) {
    work.push(1);
  }

  while(token) {
    // <<<<<<<<<<<<<<  Gather data from other processes >>>>>>>>>>>>>>>>>>>
    token_flag = 0;
    MPI_Iprobe((rank - 1 + size) % size, TOKEN_TAG, MCW, &token_flag, &token_status);
    if(token_flag) {
      print("");
      MPI_Recv(&token, 1, MPI_INT, (rank - 1 + size) % size, TOKEN_TAG, MCW,MPI_STATUS_IGNORE);
      print("");
    }

    for(int i = 0; i < size; i++) {
      work_flag = 0;
      MPI_Iprobe(i, WORK_TAG, MCW, &work_flag, &work_status);
      if(work_flag) {
        MPI_Recv(&incoming_work, 2, MPI_INT, i, WORK_TAG, MCW,MPI_STATUS_IGNORE);
        for(int j = 0; j < 2; j++) {
          work.push(incoming_work[j]);
        }
      }
    }

    // <<<<<<<<<<<<<<<<<<<<<< Send excess work out >>>>>>>>>>>>>>>>>>>>>>>>>>
    if(work.size() > WORK_THRESHHOLD) {
      int pop1 = work.top();
      work.pop();
      int pop2 = work.top();
      work.pop();
      int to_send[2] = {pop1, pop2};

      int dest = rank;
      while(dest == rank) {
        dest = rand() % size;
      }
      if(dest < rank) {
        special = 1;
      }

      MPI_Send(to_send, 2, MPI_INT, dest, WORK_TAG, MCW);
    }

    // <<<<<<<<<<<<<<<<<<  Dual-pass ring termination >>>>>>>>>>>>>>>>>>>>>>>
    if(work.empty()) {
      if(!rank){
        if(tokenStarted) {
          if(token_flag) {
            if(token == 2) {
              token = 0;
              MPI_Send(&token, 1, MPI_INT, (rank + 1) % size, TOKEN_TAG, MCW);
            }
            else {
              token = 2;
              MPI_Send(&token, 1, MPI_INT, (rank + 1) % size, TOKEN_TAG, MCW);
            }
            print("--------------------------------------------------------------------------");
            printTokenData(rank, size, token, special, work_performed);
          }
        }
        else {
          tokenStarted = true;
          token = 2;
          print("--------------------------------------------------------------------------");
          printTokenData(rank, size, token, special, work_performed);
          MPI_Send(&token, 1, MPI_INT, (rank + 1) % size, TOKEN_TAG, MCW);
        }
      }
      else if(token_flag) {
        printTokenData(rank, size, token, special, work_performed);

        if(token == 0) {
          MPI_Send(&token, 1, MPI_INT, (rank + 1) % size, TOKEN_TAG, MCW);
          break;
        }
        else if(token == 2) 
          token = token - special;

        special = 0;
        MPI_Send(&token, 1, MPI_INT, (rank + 1) % size, TOKEN_TAG, MCW);
      }

      token_flag = 0;
    }

    else {
      // <<<<<<<<<<<<<<<<<<<<<<<< Perform Work >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
      for(int i = 0; !work.empty() && i < WORK_TO_PERFORM; i++) {
        work_performed++;
        int work_item = work.top();
        work.pop();
        int tmp = work_item;
        for(int j = 0; j < work_item; j++) {
          for(int k = 0; k < work_item; k++) {
            tmp++;
          }
        }
        work_item = tmp;
      }

      // <<<<<<<<<<<<<<<<<<<<<<< Generate Work >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
      for(int i = 0; i < rand() % 3 + 1 && work_to_generate > 0; i++) {
        work_to_generate--;
        work.push(rand() % 1024 + 1);
      }


    }

  }

  if(rank == size-1)
    print("--------------------------------------------------------------------------");
  MPI_Finalize();
  return 0;
}