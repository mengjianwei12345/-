#include <iostream>
#include <string>


using namespace std;

//https://www.cnblogs.com/chengxiao/p/6194356.html
#define MAX 15

void MergeSort(int array[], int _iLeft, int _iMid, int _iRight, int _itemp[]){
	int i = _iLeft;
	int j = _iMid + 1;
	int t = 0;
	while(i <= _iMid && j <= _iRight){
		if(array[i] <= array[j]){
			_itemp[t++] = array[i++];
		}else{
			_itemp[t++] = array[j++];
		}
	}

	while(i <= _iMid){
		_itemp[t++] = array[i++];
	}

	while(j <= _iRight){
		_itemp[t++] = array[j++];
	}

	t = 0;

	while(_iLeft <= _iRight){
		array[_iLeft++] = _itemp[t++];
	}

	for(i = 0; i < _iRight; i++){
		printf("MergeSort array[%d] = %d\n",i,array[i]);
	}
}

void merge(int array[], int _iLeft, int _iRight, int temp[]){
	if(_iLeft < _iRight){
		int imid = (_iLeft + _iRight) / 2;

		printf("_iLeft = %d imid = %d\n",_iLeft, imid);
		merge(array, _iLeft, imid, temp);

		printf("imid + 1 = %d _iRight = %d\n",imid + 1, _iRight);
		merge(array,imid + 1, _iRight, temp);
  
		printf("_iLeft = %d imid = %d _iRight = %d\n",_iLeft, imid, _iRight);
		MergeSort(array, _iLeft, imid, _iRight, temp);
	}
}

void mergeSort(int array[], int _iBegin, int _iEnd){
	
	int *temp = (int *)malloc(sizeof(_iEnd));
	if(NULL == temp){
		printf("fail to malloc\n");
		return;
	}

	merge(array, 0, _iEnd, temp);

	//free(temp);
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

	mergeSort(array, 0, MAX);

	for(i = 0; i < iArraySize; i++){
		cout << array[i] << ' ';
	}
	cout << endl;
	return 0;
}
