#include <iostream>
using namespace std;
void m_swap(int *a, int *b);
void charu(int *a,int len) {
	//定义的一个基准点
	int pivot = 0;
	//每次比较的数变多，第一个形成容量为1的有序数组，依次容量变成2、3、4、5、.....、len
	for (int i = 0; i < len; i++)
	{
		//pivot是每次加入有序数组的数字下标。
		pivot = i;
		//从有序数组的最后一位数字，向数组头进行比较
		for (int j = i-1; j >= 0; j--)
		{
			//如果新加入的数字小于，数组中的数字，交换新数字与数组中数字的位置
			if (a[pivot] < a[j]) {		//把 < 改为 > 把 升序排序 变成 降序排序
				m_swap(&a[pivot], &a[j]);
				//更新基准点的坐标
				pivot = j;
			}
		}
		for (int i = 0; i < len; i++)
		{
			cout << a[i] << " ";
		}
		cout << endl;
	}
}
void m_swap(int *a, int *b) {
	int temp;
	temp = *a;
	*a = *b;
	*b = temp;
}
int main() {

	int a[] = { 9,8,4,6,2,1,3,5,7,0 };
	int len = sizeof(a) / sizeof(int);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;

	charu(a, len);
	system("pause");
	return 0;
}
