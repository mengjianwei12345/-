#include<iostream>
using namespace std;

void m_swap(int *a,int *b);

void  kuaipai(int *a, int left,int right) {
	//如果数组只有1个数字，或错误返回
	if (right <= left)return;
	//基准数
	int m_pivot = left;
	//数组左面第一个数的坐标
	int m_left = left;
	//数组右面第一个数的坐标
	int m_right = right;

	while (left < right)
	{		
		//从数组右面开始搜索，大于基准数的跳过，小于基准数的下标为right，如果搜索到基准数坐标跳出
		//此时，right=（1、小于基准数的下标。2、基准数的坐标）
		while (a[right] >= a[m_pivot] && right != m_pivot) {
			right--;
		}
		//从数组左面开始搜索，小于基准数的跳过，大于基准数的下标为left，如果搜索到right坐标跳出
		//此时，left=（1、大于基准数的下标。2、right的坐标）
		while (a[left] <= a[m_pivot] && left < right) {
			left++;
		}	
		//如果 left < right 即
		//1、right没到基准数
		//2、left 坐标没到 right 坐标
		//3、right找到 小于基准数 ，left找到 大于基准数，可以交换
		if (left < right) {
			m_swap(&a[left], &a[right]);
		}
	}
	//交换 right 和 基准数
	//此时 right=（1、小于基准数的下标。2、基准数的坐标）
	m_swap(&a[m_pivot], &a[right]);
	

	kuaipai(a, m_left, right - 1);
	kuaipai(a, right + 1, m_right);

}
void m_swap(int *a, int *b) {
	int t;
	t = *a;
	*a = *b;
	*b = t;
}

int main() {
	int a[] = { 9,8,4,6,2,1,3,5,7,0 };
	int len = sizeof(a) / sizeof(int);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	cout << endl;
	kuaipai(a, 0, len-1);
	for (int i = 0; i < len; i++)
	{
		cout << a[i] << " ";
	}
	system("pause");
	return 0;
}
