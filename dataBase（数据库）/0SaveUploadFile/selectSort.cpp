#include <iostream>
#include <string>


using namespace std;

#define MAX 15

int main(int argc, const char *argv[])
{
	int array[MAX] = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
	int iArraySize = 0;
	iArraySize = sizeof(array) / sizeof(int);
	int i = 0, j = 0;
	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	
	int iNum = 0;
	int iIndex = 0;
	for(i = 0; i < iArraySize; i++){
		iNum = array[i];
		iIndex = i;
		for(j = i; j < iArraySize; j++){
			if(array[j] < iNum){
				iNum = array[j];
				iIndex = j;
			}
		}
		if(array[i] != array[iIndex]){
			array[i] ^= array[iIndex];
			array[iIndex] ^= array[i];
			array[i] ^= array[iIndex];
		}
	}

	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	return 0;
}
