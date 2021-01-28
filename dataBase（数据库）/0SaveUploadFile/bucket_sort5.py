def bucket_sort(arr):
     n = GetMax(arr)
     length = len(arr)
     x = 1
     l = [][]
     while n > 0:
          for i in range(0, length):
               index = a[i]/x % 10
               l.[index].append(arr[i])
          k = 0
          for j in range(0, 10):
               while !l[j].empty():
                    arr[k] = l[j].pop()
          x *= 10
               
def GetMax(arr):
     n = 1
     base = 10
     for i in range(0, len(arr)):
          while arr[i] >= base:
               n += 1
               base *= 10
     return n

