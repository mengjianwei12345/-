#辅助函数——交换两个数组
def exchange(a,i,j):
    temp = a[i]
    a[i] = a[j]
    a[j] = temp

#插入排序函数
def insertion(a):
    length = len(a)
    for i in range(1,length):
        j = i
        while (j>0 and a[j]<a[j-1]):
            exchange(a,j,j-1)
            j -= 1

