//============================================================================
//****************************************************************************
//
//  Purpose:
//
//    MAIN is the main program for load_balance.
//
//  Discussion:
//
//    This is a program for load balancing using MPI.
//    Each MPI process may have unknown number of angles to compute sin(x)
//    First , the number of angles are redistributed equally among the processes.
//    Then sin(x) is computed on each balanced process.
//    Finally, the computed results are sent back to their original processes.
//
//  Modified:
//
//    July 8, 2017
//
//  Author:
//
//    Pratanu Roy
//
//****************************************************************************
//============================================================================

# include <cstdlib>
# include <iomanip>
# include <iostream>
# include <time.h>
# include <math.h>
# include <vector>
# include  <numeric>
# include <mpi.h>

using namespace std;

#define PI 3.14159265
#define DEBUG 0

// function declaration

void intialize_data(const int taskid, const int maxval, double *&angles, int &numvals);
int globalrank(const int globalpsum, const int nvalspertask);
void rebalance_data(double *&angles, const int total_elements, const int numvals, const int globalstart, const int taskid, const int numproc, int &modified_numvals, double *&results, double *&originalresults, int &total_oldelements );


// Main program

int main ( int argc, char *argv[] )
{
	int taskid;
	int ierr;
	int numproc;
	double wtime;
	//
	//  Initialize MPI.
	//
	ierr = MPI_Init ( &argc, &argv );
	//
	//  Get the number of processes.
	//
	ierr = MPI_Comm_size ( MPI_COMM_WORLD, &numproc );
	//
	//  Get the individual process taskid.
	//
	ierr = MPI_Comm_rank ( MPI_COMM_WORLD, &taskid );
	//
	//  Process 0 prints an some introductory information.
	//
	if ( taskid == 0 )
	{
		cout << endl;
		cout << "  An MPI example program for load balancing.\n";
		cout << endl;
		cout << "  The number of processes is " << numproc << "\n";
		cout << endl;
	}

	if ( taskid == 0 )
	{
		wtime = MPI_Wtime ( );
	}

	// Maximum number of angles in each rank
	int maxval = 20;
	double * angles;
	double * newresults;
	double * originalresults;
	int currentnval, sum_inclusive, sum_exclusive, total_elements, finalnval, numelements_old;

	// Get the initial distribution of angles
	intialize_data(taskid, maxval, angles, currentnval);

	//if (DEBUG) cout << "  Number of elements in Process " << taskid << " = " << currentnval << "\n";

	// Get the inclusive prefix sum and calculate the exclusive prefix sum

	MPI_Scan(&currentnval, &sum_inclusive, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);

	if (taskid == numproc - 1) total_elements = sum_inclusive;

	sum_exclusive = sum_inclusive - currentnval;

	// Broadcast the exclusive prefix sum

	MPI_Bcast(&total_elements, 1, MPI_INT, numproc-1, MPI_COMM_WORLD);

	cout << "  Number of elements in Process " << taskid << " = " << currentnval << endl;

	rebalance_data(angles, total_elements, currentnval, sum_exclusive, taskid, numproc, finalnval, newresults, originalresults, numelements_old);

	cout << " After redistribution, number of elements in Process " << taskid << " = " << finalnval << endl;
	
	for (int i = 0; i < numelements_old ; ++i)
	{
		cout<< "Process = " << taskid << " Total oldelements = "<< numelements_old << " Angle number  " << i << " = " << originalresults[i]  << endl;
	}

	// Process 0 gets the elapsed wall clock time
	if ( taskid == 0 )
	{
		wtime = MPI_Wtime ( ) - wtime;
		cout << "  Elapsed wall clock time = " << wtime << " seconds.\n";
	}

	//
	//  Terminate MPI.
	//
	MPI_Finalize( );

	return 0;
}

void intialize_data(const int taskid, const int maxval, double *&angles, int &numvals)
{
	// Initialize random seed:
	srand(unsigned(time (NULL)* (taskid+1)));

	// Generate a number between 1 and maxval
	numvals = rand()%maxval + 1;

	angles = new double [numvals];

	double max_angle = 360.0;

	// Fill up the angles array

	for (int i = 0; i < numvals ; i++)
	{
		angles[i] = (double)rand()/RAND_MAX *max_angle;
		if (DEBUG == 1) cout << "Process = " << taskid << " sin(x) = "<< sin(angles[i]*PI/180.0) << "\n";
	}
}

int globalrank(const int globalpsum, const int nvalspertask)
{
	int newrank = globalpsum/nvalspertask;
	return newrank;
}

void rebalance_data(double *&angles, const int total_elements, const int numvals, const int globalstart,
		const int rank, const int numproc, int &modified_numvals, double *&results, double *&originalresults, int &total_oldelements)
{

	const int stag = 1;
	int nvalspertask = ceil(double(total_elements)/double(numproc));
	modified_numvals = nvalspertask;

	int ierror = 0;
	if (rank == numproc-1)
	{
		modified_numvals = total_elements - (numproc-1)*nvalspertask;

		if (modified_numvals <= 0)
		{
			ierror = 1;
			MPI_Bcast(&ierror, 1, MPI_INT, rank, MPI_COMM_WORLD);
			cout << "Negative values for task = " << rank <<" is not allowed! Please choose another distribution. Exiting program!!";
			MPI_Abort(MPI_COMM_WORLD, ierror);
		}
	}

	if (ierror != 0)
	{
		MPI_Finalize();
		exit(ierror);
	}

	if (DEBUG == 2) cout << "balanced nval = " << modified_numvals << " from rank = " << rank << " total elements  = " << total_elements << "\n";
	double * modified_angles;
	modified_angles = new double [modified_numvals+1];

	MPI_Request requests[numvals];

	int nmsgs=0;

	//if (rank == 0) cout << endl << "Rebalancing the data ..." << endl;

	// Redistribute data to balance the load
	std::vector< int > dest_rank;
	std::vector< int > dest_count;
	std::vector< int > dest_startindex;
	int start=0;
	int nelements_sent = 0;

	int newrank = globalrank(globalstart, nvalspertask);
	for (int val=1; val < numvals; val++) {
		int nextrank = globalrank(globalstart+val, nvalspertask);
		if (nextrank != newrank)
		{
			nelements_sent =  (val-1)-start+1;
			MPI_Isend(&(angles[start]), nelements_sent, MPI_DOUBLE, newrank, stag, MPI_COMM_WORLD, &(requests[nmsgs]));
			nmsgs++;
			// Keep track of the destination ranks and number of elements sent
			{
				dest_rank.push_back(newrank);
				dest_count.push_back(nelements_sent);
				dest_startindex.push_back(start);
			}
			start = val;
			newrank = nextrank;
		}
	}
	nelements_sent  = numvals-start;
	MPI_Isend(&(angles[start]), nelements_sent, MPI_DOUBLE, newrank, stag, MPI_COMM_WORLD, &(requests[nmsgs]));
	nmsgs++;
	{
		dest_rank.push_back(newrank);
		dest_count.push_back(nelements_sent);
	}

	// Receive all data
	int cumulativevals= 0;
	int count;
	int recv_count = 0;
	int total_recv_requests = 0;
	int original_rank;
	std::vector< int > source_rank;
	std::vector< int > source_count;
	std::vector< int > source_startindex;
	MPI_Status status;
	while (cumulativevals != modified_numvals) {
		MPI_Recv(&(modified_angles[cumulativevals]), modified_numvals - cumulativevals, MPI_DOUBLE, MPI_ANY_SOURCE, stag, MPI_COMM_WORLD, &status);
		MPI_Get_count(&status, MPI_DOUBLE, &count);
		original_rank = status.MPI_SOURCE;
		// Keep track of the source ranks and number of elements
		if (original_rank != rank)
		{
			source_rank.push_back(original_rank);
			source_count.push_back(count);
			source_startindex.push_back(cumulativevals);
			total_recv_requests++;
		}
		cumulativevals += count;

	}

	// Wait until all of the sends have been received
	MPI_Status statuses[numvals];
	MPI_Waitall(nmsgs, requests, statuses);

	if (DEBUG == 2)
	{
		double * originalresults_verify;
		originalresults_verify = new double[numvals];
		for (int i = 0; i < numvals ; i++)
		{
			originalresults_verify[i] = sin(angles[i]*PI/180.0);
		}
	}

	// Now we can replace the angles with modified_angles
	delete[] angles;
	angles = modified_angles;

	//if (rank == numproc-1) cout << endl << "Calculating sin(x)..." << endl;
	// Calculate sin(x) on each balanced rank
	//double * results;
	//double * originalresults;
	results = new double [modified_numvals+1];
	for (int i = 0; i < modified_numvals ; i++)
	{
		results[i]  = (sin(angles[i]*PI/180.0));
	}

	// Send back the results to their original rank

	// First send the data using the previously stored source information
	nmsgs=0;
	std::vector<int>::size_type iter;
	//MPI_Request requests[numvals];
	for (iter = 0; iter < source_rank.size(); iter++)
	{
		if (DEBUG == 2) cout << "I am rank = " << rank << "   from rank = " << source_rank[iter] << " Numbers received = " << source_count[iter] << "source start = " << source_startindex[iter] << endl;

		if (rank != source_rank[iter])
		{
			//start = modified_numvals - source_count[iter];
			MPI_Isend(&(results[source_startindex[iter]]), source_count[iter], MPI_DOUBLE, source_rank[iter], stag, MPI_COMM_WORLD, &(requests[nmsgs]));
			nmsgs++;
		}

	}

	// Then receive all data using previously stored destination information
	cumulativevals= 0;
	count = 0;

	total_oldelements = 0;
	for (iter = 0; iter < dest_rank.size(); iter++)

	{
		if (DEBUG == 2) cout << "I am rank = " << rank << " Sent data to rank = " << dest_rank[iter] << " Numbers sent = " << dest_count[iter] << endl;
		if (rank != dest_rank[iter])  
		{
		   total_oldelements += dest_count[iter];
		   if (DEBUG == 0) cout << "I am rank = " << rank << " Sent data to rank = " << dest_rank[iter] << " Numbers sent = " << dest_count[iter] << endl;
		}   
	}
	originalresults = new double[total_oldelements];
	//originalresults = new double[numvals];
	for (iter = 0; iter < dest_rank.size(); iter++)
	{
		if (rank != dest_rank[iter])
		{
			MPI_Recv(&(originalresults[cumulativevals]), total_oldelements - cumulativevals, MPI_DOUBLE, dest_rank[iter], stag, MPI_COMM_WORLD, &status);
			//MPI_Recv(&(originalresults[dest_startindex[iter]]), total_oldelements - cumulativevals, MPI_DOUBLE, dest_rank[iter], stag, MPI_COMM_WORLD, &status);
			MPI_Get_count(&status, MPI_DOUBLE, &count);
			cumulativevals += count;
			if (DEBUG == 2) cout << "I am rank = " << rank << " from original rank = " << dest_rank[iter] << " Numbers received = " << count << endl;

		}
	}
	// Wait until all of the sends have been received
	//MPI_Status statuses[numvals];
	MPI_Waitall(nmsgs, requests, statuses);

	if (DEBUG == 2)
	{
		cout<< "Process = " << rank << " Total old elements = " << total_oldelements  << endl;
		for (int i = 0; i < total_oldelements ; i++)
		{
			cout<< "Process = " << rank << " Angles number  " << i << " = " << originalresults[i]  << endl;
		}
	}
}

