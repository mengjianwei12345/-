#辅助函数——交换两个数
def exchange(a,i,j):
    temp = a[i]
    a[i] = a[j]
    a[j] = temp

#选择排序函数(无返回值)
def selection(a):
    length = len(a)
    for i in range(length):
        minIndex = i
        for j in range(i+1,length):
            if a[j] < a[minIndex]:
                minIndex = j
        exchange(a,i,minIndex)


