#include "Sort.h"



char** BucketSort(char** in,int n,int current)
{
	//Look through the strings
	//Inisialisation of list of 0 pos chars

	List* values=new List;
	int k = 0;
	for (int i = 0; i < n; i++) {
		if (strlen(in[i]) < current) {
			in[k] = in[i];
			k++;
		}
		else
			values->push(in[i][current]);
	}

	//Initialisation of array of pointers to the array of string pointers for each 0 pos char

	char*** tmp=new char**[values->size];
	int*size = new int[values->size];
	values->iterator = values->begnode;
	for (int i = 0; i < values->size; i++) {
		tmp[i] = new char*[values->iterator->numb];
		values->next();
		size[i] = 0;
	}
	
	for (int i = k; i < n; i++) {
		values->iterator = values->begnode;
		for (int j = 0; j < n; j++) {
			if (values->iterator->data == in[i][current]) {
				tmp[j][size[j]] = in[i];
				size[j]++;
				break;
			}
			else
				values->next();
		}
	}

	
	for (int i = 0; i < values->size; i++) {
		int pntr = 0;
		if(size[i]>1)
			BucketSort (tmp[i],size[i],current+1);
		for (int j = 0; j < size[i]; j++) {
			in[j + k] = tmp[i][j];
			pntr++;
		}
		k += pntr;
	}
	return in;
}
