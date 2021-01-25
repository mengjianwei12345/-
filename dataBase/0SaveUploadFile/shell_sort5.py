def shell_sort(a):
     l = int(len(a)/2)
     #生成增量列表
     gap_list = []
     while l > 0:
          gap_list.append(l)
          l = int(l/2)
     print(gap_list)
     for gap in gap_list:#增量gap，并逐步缩小增量 
          #print(gap)
          for i in range(gap, len(a)):#从第gap个元素，逐个对其所在组进行直接插入排序操作
               j = i
               print(gap, j, j-gap)
               while j -gap >= 0 and a[j-gap] > a[j]:
                    swap(a, j, j-gap)  #交换两个元素
                    j = j - gap
                              
     for i in range(0,len(a)):
          print(a[i])

def swap(arr, a, b):
     arr[a] = arr[a] + arr[b]
     arr[b] = arr[a] - arr[b]
     arr[a] = arr[a] - arr[b]


