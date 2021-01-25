# -*- coding: utf-8 -*-
# 插入排序，时间复杂度为O(n^2)
def  insertSort(numbers):
	# 插入排序的实现
	count = len(numbers)
	for i in range(1, count):
		key = numbers[i]
		print('key is',key)
		j = i-1
		while j>=0:
			if numbers[j] > key:
				numbers[j + 1] = numbers[j]
				numbers[j] = key
			j -= 1
			print(numbers)
			
