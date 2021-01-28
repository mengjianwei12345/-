def j_sort(a):
      l = len(a)
      temp = 0
      
      for j in range(0,l-1):
            count = j  #记录最小元素下标
            #每次找出最小元素
            for i in range(j,l-1):
                  if a[count] > a[i+1]:
                        count = i+1
            #交换最小元素和待排序元素中最前一个
            '''if count != j:
                  temp = a[j]
                  a[j] = a[count]
                  a[count] = temp '''
            a[j], a[count] = a[count], a[j] #实现跟上述代码一样
      for i in range(0,l):
            print(a[i])


            
     

                  
                       
            
            
