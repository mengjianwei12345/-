def Merge_Sort(lists):
    if len(lists) <= 1:
        return lists
    num = int(len(lists)/2)
    left = Merge_Sort(lists[:num]) #将列表从中间分为两部分
    right = Merge_Sort(lists[num:])
    return Merge(left, right) #合并两个列表

def Merge(left,right):
    r, l=0, 0
    result=[]
    while l<len(left) and r<len(right):
        if left[l] < right[r]:
            result.append(left[l])
            l += 1
        else:
            result.append(right[r])
            r += 1
    result += left[l:]
    result += right[r:]
    return result

