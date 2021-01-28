#include<iostream>
using namespace std;

void m_swap(int *a, int* b);
//先组成大堆，然后把最大数（根），放到队尾。
void dui(int *a,int len) {
	if (len == 1)return;
	// len/2-1 可以找到最后一个父节
	for (int i = len/2-1; i >=0; i--)
	{
		//以下是从父节点和两个子节点中挑出最大的数来当新的父节点
		//通过迭代使根节点是最大数
		if (a[i] < a[i * 2 + 1]) {  //把 < 改成 > ，把 数组升序 变成 数组降序
			m_swap(&a[i], &a[i * 2 + 1]);
		}
		if (a[i] < a[i * 2 + 2] && len>i*2+2) {  //把 < 改成 > ，把 数组升序 变成 数组降序
			m_swap(&a[i], &a[i * 2 + 2]);
		}
	}
	
	m_swap(&a[0] , &a[len - 1]);
	//已知最后一个最大数，所以长度-1，迭代。
	dui(a, len-1);
}

void m_swap(int *a, int* b) {
	*a = *a^*b;
	*b = *a^*b;
	*a = *a^*b;
}

int main() {
	int a[] = { 9,8,4,6,2,1,3,5,7,0 };
	int len = sizeof(a) / sizeof(int);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;

	dui(a, len);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;
	system("pause");
	return 0;
}
