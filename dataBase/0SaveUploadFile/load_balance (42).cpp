// yuntang
// file:balace_writing.cpp
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <map>
#include "balance_writing.h"
#define TOTAL_RESOURCE 10000
using std::vector;
using std::map;
namespace load_balance
{
LoadBalance::LoadBalance()
{
	srand(time(0));
}
LoadBalance::~LoadBalance()
{
}
void LoadBalance::Init(const vector<int> 
		&server_resource_used)
{
	CalculateWeight(server_resource_used);
}
void LoadBalance::Write(vector<int> 
		&server_resource_used, int request)
{
	printf("Writing.....\n");
	for (int i = 0; i < request; ++i) {
		WriteOnce(server_resource_used);
	}
	printf("write over!\n");
}
void LoadBalance::Delete(vector<int> &server_resource_used, int idx)
{
	if (server_resource_used[idx] > 0) {
		--server_resource_used[idx];
		CalculateWeight(server_resource_used);
	} else { 
		printf("this server is null, you can not delete from it!\n");
	}
}
void LoadBalance::WriteOnce(vector<int> &server_resource_used)
{
	double total = 0;
	map<double, int> tmp;
	for (auto iter = weight_.begin(); iter != weight_.end(); ++iter) {
		total += iter->second;
		tmp[total] = iter->first;
	}
	auto select = tmp.upper_bound(rand_range(0, total));
	++server_resource_used[select->second];
	CalculateWeight(server_resource_used);
}
double LoadBalance::rand_range(double front, double end)
{
	double tmp = rand();
	if (tmp > 1) {
		tmp = tmp - 1;
	}
	return tmp/(RAND_MAX)*(end-front)+front;
}
void LoadBalance::CalculateWeight(const vector<int> 
		&server_resource_used)
{
	int i = 0;
	weight_.clear();
	for (auto iter = server_resource_used.begin();
			iter != server_resource_used.end(); ++iter) {
		double m = static_cast<double>(TOTAL_RESOURCE - *iter + 0.001);
		weight_[i] = m * m;
		++i;
	}
}
} // namespace load_balance
