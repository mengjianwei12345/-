#include <iostream>
#include <string>


using namespace std;

#define MAX 15

void quickSort(int array[], int _iBegin, int _iEnd){
	if(_iBegin > _iEnd || NULL == array){
		return;
	}

	//partition
	if(_iBegin < _iEnd){
		int iBaseValue = array[_iBegin];
		int iTail = _iBegin;
		int iEnd = _iEnd;
		
		while(iTail < iEnd){
			while(iTail < iEnd && array[iEnd] >= iBaseValue){
				iEnd--;
			}
			array[iTail] = array[iEnd];
			while(iTail < iEnd && array[iTail] <= iBaseValue){
				iTail++;
			}
			array[iEnd] = array[iTail];
		}

		array[iTail] = iBaseValue;
		quickSort(array, _iBegin, iTail - 1);
		quickSort(array, iTail + 1, _iEnd);
	}
	return;
}

int main(int argc, const char *argv[])
{
	int array[MAX] = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
	int iArraySize = 0;
	iArraySize = sizeof(array) / sizeof(int);
	int i = 0;
	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	
	quickSort(array, 0, MAX - 1);	

	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	return 0;
}
