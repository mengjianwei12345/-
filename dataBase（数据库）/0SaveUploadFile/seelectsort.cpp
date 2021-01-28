#include <iostream>
using namespace std;

void xuanze(int *a, int len) {
	//每次循环得出数列最小的数字，并添加入有序数组
	//依次在数组的0、1、2、3...len的位置添加最小数
	for (int i = 0; i < len-1; i++)
	{
		//默认第一位数，是未排序队列中的最小数字
		int min = a[i];
		//判断值，默认-1，如果 第一位数是 真min ，则pivot不变。
		int pivot=-1;
		for (int j = i+1; j < len; j++)
		{
			//如果找到更小的数字，保存数值，并记录下标
			if (min > a[j]) {	//把 < 改成 > ，把 数组升序 变成 数组降序
				min = a[j];
				pivot = j;
			}
		}
		//如果pivot不等于-1.说明有更小的数字，
		//更新最小数字，和记录的pivot坐标位置数字
		if (pivot != -1) {
			a[pivot] = a[i];
			a[i] = min;
		}
		for (int k = 0; k < len; k++)
		{
			cout << a[k] << " ";
		}
		cout << endl;
	}

}
int main() {
	int a[] = { 9,8,4,6,2,1,3,5,7,0 };
	int len = sizeof(a) / sizeof(int);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;
	xuanze(a, len);
	system("pause");
	return 0;
}
