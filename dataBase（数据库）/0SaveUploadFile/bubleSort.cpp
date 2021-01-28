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
	for(i = 0; i < iArraySize; i++){
		for(j = 0; j < iArraySize - 1 - i; j++){
			if(array[j] > array[j + 1]){
				array[j] ^= array[j + 1];
				array[j + 1] ^= array[j];
				array[j] ^= array[j + 1];
			}
		}
	}

	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	return 0;
}
