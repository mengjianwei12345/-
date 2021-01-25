
////////**********pseudo swap making of state random array;

#include<iostream>
#include<string.h>
using namespace std;
void pseudoswap(unsigned char state[], unsigned char key[], unsigned int len)
{
   int i,j=0,t; 
   
   for (i=0; i < 256; ++i)
      state[i] = i; 
   for (i=0; i < 256; ++i) {
      j = (j + state[i] + key[i % len]) % 256; 
      t = state[i]; 
      state[i] = state[j]; 
      state[j] = t; 
   }   
}

// output generator;  
void bytegeneretor(unsigned char state[], unsigned char ouputarr[], unsigned int len)
{  
   int i=0,j=0,x,t; 
   unsigned char key; 
   
   for (x=0; x < len; ++x)  {
      i = (i + 1) % 256; 
      j = (j + state[i]) % 256; 
      t = state[i]; 
      state[i] = state[j]; 
      state[j] = t; 
      ouputarr[x] = state[(state[i] + state[j]) % 256];
   }   
}  

void decrypt(unsigned char state[256],unsigned char outputarr[256],unsigned char decarr[256],unsigned char key[5] )
{
	
	//state^outputarr;
	for(int i=0;i<256;i++)
	{
		decarr[i]=state[i]^outputarr[i];
	}
	for(int i=0;i<5;i++)
	{
		decarr[i]=key[i];
	}
	
	
}
int main() 
{
   unsigned char state[256],key[5],outputarr[256],decarr[256];; 
   cout<<"input the key to encrpyt";
   cin>>key;
   int i=0;
   while(1)
   {
   	if(key[i++]=='\0')
   	break;
   }
   i--;
   unsigned int lens=i;
   pseudoswap(state,key,lens); 
   bytegeneretor(state,outputarr,len); 
   
   for (index=0; index < len; index++) 
     printf("%d\n",outputarr[index]);
     decrypt(state,outputarr,decarr,key);
     
     cout<<"decrypted data"<<endl;
     for (index=0; index < len; index++) 
     printf("%c",decarr[index]);
   return 0; 
}
