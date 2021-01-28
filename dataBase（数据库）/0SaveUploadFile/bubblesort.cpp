#include<iostream>    
using namespace std;  
int main() {  
	int a[] = { 9,8,4,6,2,1,3,5,7,0 };  
	int len = sizeof(a) / sizeof(int);  
	int temp;  
	for (int i = 0; i <len; i++)  
	{  
		//共有10个数字，每一轮分别比较9、8、7、6、5、4、3、2、1次。  
		for (int j=0; j < len-i-1; j++)  
		{  
			//从小到大排序  
			if (a[j]>a[j+1])  
			{  
				temp = a[j];  
				a[j] = a[j+1];  
				a[j + 1] = temp;  
			}  
			//从大到小排序
			//if (a[j]<a[j + 1])
			//{
			//	temp = a[j];
			//	a[j] = a[j + 1];
			//	a[j + 1] = temp;
			//}
		}  
	}  
	for (int i = 0; i < len; i++)  
	{  
		cout << a[i] << " ";  
	}  
	system("pause");  
	return 0;  
}  
