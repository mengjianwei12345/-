def d_sort(arr):
      l = len(arr)
      for i in range(int(l/2-1), -1, -1):
            adjustHead(arr,i,l)
      # 交换堆顶和最后一个元素，并调整堆结构
      for j in range(l-1, 0, -1):
            arr[0], arr[j] = arr[j], arr[0] #将堆顶元素和末尾元素进行交换
            adjustHead(arr, 0, j) #重新对对进行调整
      for k in range(0,l):
            print(arr[k])

#构造大顶堆            
def adjustHead(a, i, l):
      temp = a[i] #取出当前元素
      k = 2*i + 1 #从左子节点开始，即2*i+1
      while k < l:
            if k+1 < l & a[k] < a[k+1]: #若果左子节点小于右子节点，k指向右子节点
                  k=k+1
            if a[k] > temp: #如果子节点大于父节点，将子节点赋值给父节点，并将子节点下标记录下来，后面将父节点值赋值给该子节点
                  a[i] = a[k]
                  i = k
            else:
                  break
            k = 2*k + 1 #把该节点当作父节点，继续操作
      a[i] = temp #将父节点值赋值给该子节点
                  
