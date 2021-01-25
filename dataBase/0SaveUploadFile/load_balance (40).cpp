#include <iostream>
#include <algorithm>
#include <iterator>
#include <random>
#include <vector>
#include <chrono>
#include <iomanip>
#include <thread>
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */

using namespace std;

#define REPLICAS 100


vector<vector<int>>  create_perm_matrix(int num_backend);
vector<int> fill_random_matrix(int rows);
void print_matrix(std::vector<std::vector<int>> matrix, int rows);
std::vector<int> get_xdp_table(std::vector<std::vector<int>> v,int rows);
void load_balance(int modulo,vector<int> v);
void print_vector();

//int REPLICAS=0;
vector<int> backends(10) ;


int main(int argc, char* argv[])
{
    // Check the number of parameters
    if (argc < 2) {

        cerr << "Insert number of backends (Example) ./algorithm 5 10" << endl;
      	/*

			5 svc with  10 backends 

      	*/
        return 1;
    }

   // REPLICAS=atoi(argv[2]);


   	int svc=atoi(argv[1]);
   	int bck=atoi(argv[2]);

    vector<vector<int>> mat = create_perm_matrix(bck);
   // print_matrix(mat,atoi(argv[1])*atoi(argv[2]));

    std::vector<int> vet;

    vet = get_xdp_table(mat,bck*REPLICAS);



    for(int i=0; i<bck; i++)
        backends[i]=0;


    thread first (load_balance,bck*REPLICAS,vet);  
    thread print_thread(print_vector);
   

cout<<"-------------------\n";
    for (auto i = vet.begin(); i != vet.end(); ++i)
        std::cout << *i << endl;


    std::vector<int> vet1,v2(bck*REPLICAS);
    mat.erase (mat.begin()+1,mat.begin()+2);

 
 for (int i = 0; i<vet.size();i++)
    {
       v2[i]=-2;
    }

       mat.insert (mat.begin()+1,v2);


    vet1 = get_xdp_table(mat,bck*REPLICAS);
    cout<<"-------------------a\n";
    int c=0;

    for (int i = 0; i<vet1.size();++i)
    {
        if(vet[i] != vet1[i])
        {
            std::cout << vet[i] <<" "<< vet1[i]<<"    X"<<endl;
            c++;
        }
        else
            std::cout << vet[i] <<" "<< vet1[i]<<endl;
    }

    cout<<"Total changes = "<<c<<endl;



 	first.join();
    print_thread.join();


    return 0;
}

void print_vector()
{
    while(true)
    {
        for (auto i = backends.begin(); i != backends.end(); ++i)
            std::cout << *i << ' ';

        cout<<endl;
        std::this_thread::sleep_for (std::chrono::seconds(2));
    }
  
}


void load_balance(int modulo,vector<int> v)
{

    srand (time(NULL));
    int r=0;

    while(true)
    {
          r = rand() % modulo;
          int backend = v.at(r);
          backends[backend]++;

          std::this_thread::sleep_for (std::chrono::milliseconds(300));
    }
}

std::vector<int> get_xdp_table(std::vector<std::vector<int>> matrix,int rows)
{
    vector<int> vett(rows);
    for(int i=0;i<rows;i++)
        vett[i]=-1;

   // cout<<"---------------------vet size "<<vett.size()<<" rows : "<<rows<<vett.at(0)<<vett.at(19)<<"\n";
    int index=0;
    int cnt=0;

    for(int j=0; j<rows ; j++)
    {
        int x=0;
        index=j;
        for ( const auto &row : matrix )
        {
                
            if(cnt == rows)
                goto exit;
               
 ret:          int num = row[index];

           // cout<<"NUM: "<<num<<endl;

            if(num==-2)
            {
                x++;
                continue;
            }
            else
            {
               if(vett.at(num) != -1)
               {
                    index++;
               //      cout<<"Found at "<<num<<" Backend "<<vett.at(num)<<endl;

                    goto ret;
               }else if(vett.at(num) == -1)
               {
                    vett[num]=x;
                //    cout<<"Added at "<<num<<" Backend "<<vett[num]<<endl;
                    x++;
                    //vett.insert(num,j);
                    cnt++;
                    index=j;
               }
        }
        }  
    }

exit:
    return vett;
}

vector<vector<int>> create_perm_matrix(int num_backend)
{
   // cout << "#Backends = " <<num_backend<< endl;

    vector<vector<int>> matrix;

    int rows = num_backend*REPLICAS;

    for(int i=0; i<num_backend;i++)
    {
       // cout<<"Filling Backend "<<i;
        matrix.push_back(fill_random_matrix(rows));
    }

    
    return matrix;
}


void print_matrix(std::vector<std::vector<int>> matrix, int rows)
{
    for(int j=0; j<rows ; j++)
    {
        for ( const auto &row : matrix )
        {
           std::cout <<setw(5)<< row[j] << setw(5);
        }
        std::cout << endl;
    }
}


vector<int> fill_random_matrix(int rows)
{
    random_device rd;
    mt19937 mt(rd());

    vector<int> elements ;

    for(int i=0; i<rows;i++)
        elements.push_back(i);


  //  random_shuffle ( elements.begin(), elements.end() );

  unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();

  shuffle (elements.begin(), elements.end(), std::default_random_engine(seed));


    return elements;
}