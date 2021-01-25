//The load balancing problem. Simulated annealing
#include<bits/stdc++.h>
using namespace std;
#define mp make_pair
#define fi first
//#define se second
const int nmax=10009;
const int mmax=5009;
double k=800000;
int M;//Number of processors
int N;//Number of jobs to be assigned
long long t[nmax];//The time needed for each task to be completed

double Temp;

int opLimit=100;
int L=2;
//const double e=2.718281828;
double getProbability(long long energyDifference){
   return exp(-(double)energyDifference/(k*Temp));
}
bool executeOperations(double prob){
   double r=(double)rand()/(double)RAND_MAX;
   return r<=prob;
}
struct processor{
   int idx;
   long long T;//The workload of each processor
   processor(){}
   processor(int _idx,long long _T){
      idx=_idx;
      T=_T;
   }
   set<pair<long long,int> > A;//The set of jobs for each processor
   bool operator < (const processor &x)const{
      return T>x.T;
   }
};
struct solution{
   processor P[mmax];
   long long energy;
};
struct job{
   long long time;
   int idx;
};
solution currSol;
solution bestSol;
bool cmp(processor fi,processor se){
   return fi.T<se.T;
}
bool cmpJobs(job fi,job se){
   return fi.time<se.time;
}
void read(){
   cin>>M>>N;
   for(int i=1;i<=N;i++){
      cin>>t[i];
   }
}
priority_queue<processor> pq;
void initialiseWithGreedy(solution &curr){
   job t2[nmax];
   for(int i=1;i<=N;i++){
      t2[i].time=t[i];
      t2[i].idx=i;
   }
   sort(t2+1,t2+N+1,cmpJobs);
   reverse(t2+1,t2+N+1);
   for(int i=1;i<=M;i++){
      curr.P[i].idx=i;
      curr.P[i].T=0;
      pq.push(curr.P[i]);
   }
   for(int i=1;i<=N;i++){
      processor currProc=pq.top();
      pq.pop();
      currProc.T+=t2[i].time;
      currProc.A.insert(mp(t2[i].time,t2[i].idx));
      curr.P[currProc.idx]=currProc;
      pq.push(currProc);
   }
}
void initialise(solution &curr){
   for(int i=1;i<=M;i++){
      curr.P[i].A.clear();
      curr.P[i].T=0;
   }
   curr.energy=0;
   for(int i=1;i<=N;i++){
      int idxOfProc=rand()%M+1;
      curr.P[idxOfProc].A.insert(mp(t[i],i));
      curr.P[idxOfProc].T+=t[i];
   }
}
long long evaluateSolution(solution curr){
   long long mx=0;
   for(int i=1;i<=M;i++){
      mx=max(mx,curr.P[i].T);
   }
   return mx;
}
void calcProc(processor curr){
   set<pair<long long,int> >::iterator it;
   long long sum=0;
   for(it=curr.A.begin();it!=curr.A.end();it++){
      sum+=it->first;
   }
   assert(sum==curr.T);
}
int debu;
void makeOperation2(solution &curr){
   int proc1;
   int proc2;
   proc1=rand()%M+1;
   proc2=rand()%M+1;
   while(curr.P[proc1].A.size()==0)proc1=rand()%M+1;
   while(curr.P[proc2].A.size()==0)proc2=rand()%M+1;
   assert(curr.P[proc1].A.size()!=0);
   assert(curr.P[proc2].A.size()!=0);
   if(curr.P[proc1].T>curr.P[proc2].T)swap(proc1,proc2);
   set<pair<long long,int> >::iterator it=curr.P[proc2].A.begin();
   debu=0;
   do{
      it=curr.P[proc2].A.begin();
      int jobID=it->second;
      curr.P[proc2].A.erase(mp(t[jobID],jobID));
      curr.P[proc2].T-=t[jobID];
      curr.P[proc1].A.insert(mp(t[jobID],jobID));
      curr.P[proc1].T+=t[jobID];
   }while(curr.P[proc1].T+(*curr.P[proc2].A.begin()).fi<curr.P[proc2].T);
}
void makeOperation(solution &curr){
   int proc1=1;
   int proc2=M;

   for(int j=1;j<=M;j++){
      if(curr.P[j].T<curr.P[proc1].T){
         proc1=j;
      }
      if(curr.P[j].T>curr.P[proc2].T){
         proc2=j;
      }
   }
   if(curr.P[proc1].T>curr.P[proc2].T)swap(proc1,proc2);
   set<pair<long long,int> >::iterator it=curr.P[proc2].A.begin();
   debu=0;
   do{
      it=curr.P[proc2].A.begin();
      int jobID=it->second;
      cout.flush();
      curr.P[proc2].A.erase(mp(t[jobID],jobID));
      curr.P[proc2].T-=t[jobID];
      curr.P[proc1].A.insert(mp(t[jobID],jobID));
      curr.P[proc1].T+=t[jobID];
   }while(curr.P[proc1].T+(*curr.P[proc2].A.begin()).fi<curr.P[proc2].T);


}
void change(solution &curr,int numOfOperations){
   for(int i=1;i<=numOfOperations;i++){
      if(!(rand()%8))
         makeOperation2(curr);
      makeOperation(curr);
   }
}
long long mn=1e18;
solution startSol;
void solve(){
   currSol=startSol;
   solution nxtSol;
   Temp=1000.0*((int)log2(M));
   Temp=max(Temp,1000.0);
   do{
      for(int i=1;i<=L;i++){
         nxtSol=currSol;
         change(nxtSol,164);
         nxtSol.energy=evaluateSolution(nxtSol);
         long long eDiff=nxtSol.energy-currSol.energy;
         if(currSol.energy<mn){
            mn=currSol.energy;
            bestSol=currSol;
         }
         if(executeOperations(getProbability(eDiff))){
            currSol=nxtSol;
         }
      }
      Temp=0.9*Temp;
   }while(Temp>=0.1);
}
int main(){
   srand(47);
   ios_base::sync_with_stdio(false);
   cin.tie(0);
   read();
   k/=4*log2(M);

   L+=(int)log2(M)-1;
   initialise(startSol);
   startSol.energy=evaluateSolution(startSol);
   bestSol=startSol;
   int cnt=0;
   for(int j=1;j<=(int)(10+log2(M));j++){
      solve();
      if(currSol.energy<mn){
         mn=currSol.energy;
         bestSol=currSol;
      }
      initialise(startSol);
      startSol.energy=evaluateSolution(startSol);
   }
   for(int i=1;i<=M;i++){
      calcProc(bestSol.P[i]);
   }
   cout<<mn<<"\n";
}
