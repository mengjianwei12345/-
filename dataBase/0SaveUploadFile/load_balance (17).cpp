#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>

#include "qos_calculator.h"
#include "FixedSizeHeap.h"

// =A256/(log(A256,2)*2) + log(32,2)
// A256 number_of_submission_queues, 32 number_of_channels
// number_of_submission_queues/(log2(number_of_submission_queues)*2) + log2(number_of_channels)
using namespace std;

class load_balancing: qos_calculator<uint32_t, uint32_t> {
public:

	load_balancing(uint32_t qd):number_of_channels(32), 
	                 number_of_loops(50000),
	                 number_of_submission_queues(qd), indx(0),
	                 channel_queue(number_of_channels,0),
	                 cmd_latency(5, 99)
	                 {
	                 	srand(time(NULL));
	                 };

	~load_balancing(){};

	virtual uint32_t rand_number() {

		uint32_t data = 69069*rand()*time(NULL) + (1234567*indx);
		++indx;

		return data;
	}

	virtual uint32_t get_command() {return static_cast<uint32_t>(rand_number()%number_of_channels);} // rand()

	virtual uint32_t get_balanced_cmd() {

		 uint32_t cmd = indx % number_of_channels;
		 ++indx;

		 return cmd;
	}

	virtual uint32_t command_fetch_random() {

		for (int i=0; i<number_of_submission_queues; ++i)
		{
			uint32_t cmd = get_command(); //get_balanced_cmd(); //get_command();
			channel_queue.at(cmd) += 1;
		}

		uint32_t max_load = *max_element(begin(channel_queue), end(channel_queue)); 

		for (int i=0; i<number_of_channels; ++i) 
		{
			max_loading_overall.push_back(channel_queue.at(i));
			cmd_latency.AddSample(channel_queue.at(i));
			channel_queue.at(i) = 0;
		}

		return max_load;
	}

	virtual void execute_workload_random()
	{
		uint32_t loading_per_loop;

		for(int k=0; k<number_of_loops; ++k)
		{
			loading_per_loop = command_fetch_random();
			max_loading_overall.push_back(loading_per_loop);
		}

		uint32_t max_overall = *max_element(begin(max_loading_overall), end(max_loading_overall)); 

		cout << number_of_submission_queues << " 999 " << cmd_latency.GetMinRightTail() << " max " << max_overall << endl;
	}

private:
	uint32_t number_of_channels;
	uint32_t number_of_loops;
	uint32_t number_of_submission_queues;
	uint32_t indx;
	vector <uint32_t> channel_queue;
	vector <uint32_t>  max_loading_per_cmd_fetch;
	vector <uint32_t>  max_loading_overall;
	qos_calculator cmd_latency;

};



int main(int argc, const char* argv[])
{

	for (int j=1; j<=256; ++j)
	{
		load_balancing* simulator = new load_balancing(8*j);
		simulator->execute_workload_random();		
	}

	return 0;
}