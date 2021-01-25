#辅助函数——交换两个数
def exchange(a,i,j):
    temp = a[i]
    a[i] = a[j]
    a[j] = temp

def partition(a,low,high):
    i = low
    j = high + 1
    while True:
        #左边指针往右移动
        i += 1
        while a[i] < a[low]:
            if i == high:
                break
            i += 1
        #右边指针往左移动
        j -= 1
        while a[low] < a[j]:
            if j == low:
                break
            j -= 1
        #如果左右指针交叉，说明已经排序好了
        if i >= j:
            break
        #交换a[i]、a[j]
        exchange(a,i,j)
    exchange(a,low,j)
    return j

def sort(a,low,high):
    if low >= high:
        return
    j = partition(a,low,high)
    sort(a,low,j-1)
    sort(a,j+1,high)

def quickSort(a):
    length = len(a)
    sort(a,0,length-1)

