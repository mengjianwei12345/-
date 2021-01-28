def zc_sort(a): 
	l = len(a) 
	j=0 
	for i in range(1,l): 
		temp = a[i] 
		for j in range(i-1, -1, -1): 
			if temp < a[j]: #如果第i个元素大于前i个元素中的第j个 
				a[j+1] = a[j] #则第j个元素先后移1位 
				a[j] = temp #将i个元素赋值给空着的位置 
			else: #如果第i个元素小于等于前i个元素中的第j个则结束循环 
				break 
	for i in range(0,l): 
		print(a[i]) 

if __name__ == "__main__": 
	a = [1,7,3,6,2,6] 
	zc_sort(a)