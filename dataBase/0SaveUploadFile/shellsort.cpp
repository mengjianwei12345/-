#include<iostream>
using namespace std;

void m_swap(int *a, int *b);
//输入，数组*a，数组长度len，间距t（初始 t1=len/2，t2=t1/2，......，1）
void xi_er(int *a,int len,int t) {
	//间隔等于0跳出
	if (t == 0)return;
	//因为当有间距分组时，需要用到简单插入排序
	//所以需要 基准
	int pivot;
	//与简单插入类似的方法
	for (int i = 0; i < len; i++)
	{
		pivot = i;
		//从后面增添新的数字进入前面的数组。
		//数字的分组根据间距t划分
		for (int j = i; j >=0; j-=t)
		{
			if (a[pivot] > a[j]) {	//把 < 改成 > ，把 数组升序 变成 数组降序
				m_swap(&a[pivot], &a[j]);
				pivot = j;
			}
		}
	}
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;
	xi_er(a,len,t/2);
}
void m_swap(int *a,int *b) {
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
	xi_er(a, len,len/2);
	
	system("pause");
	return 0;
}
