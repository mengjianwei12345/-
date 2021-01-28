import math
import copy as cp


	
def select_sort(array):
	n=len(array)
	for i in range(n):
		min_index=i
		j=i+1
		while j<n:
			if array[j]<array[min_index]:
				min_index=j
			j=j+1
		array[i],array[min_index]=array[min_index],array[i]


	

	




