import math

def radix_sort(arr):
     radix = 10 #基数
     k = int(math.ceil(math.log(max(arr),radix)))#k可以表示任意整数
     #math.log对arr中最大的数取对数，log(max(arr),10),并对其取整得到最大值的位数
     bucket =[[] for i in range(radix)] 
     for i in range(1, k+1): 
          for  value in arr:
               bucket[int(value%(radix**i)/(radix**(i-1)))].append(value) #析取整数第k位数字（从低到高）10**2位10的二次方
          del arr[:]
          for each in bucket:
               arr.extend(each) #桶合并
          bucket = [[]for i in range(radix)]
