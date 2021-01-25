#!/usr/bin/env python
#-*- coding:utf-8 -*-
# author:hanzhengrong
# datetime:2018-07-13 15:45
# software: PyCharm

'''
直接插入排序

算法思想：
在每次将待排的记录按其关键字大小插入到前面已经排好序的子序列中，直到全部记录插入完成

'''


def insert_sort(lists):
    count = len(lists)
    for i in range(1, count):
        key = lists[i]
        j = i - 1
        while j >= 0:
            if lists[j] > key:
                lists[j + 1] = lists[j]
                lists[j] = key
            j -= 1
    return lists


def insertSort(arr):
    global location
    for i  in range(1,len(arr)):
        # 将待插入的元素与之前的所有已排好序的元素作比较，
        # 若大于待排序元素则向后移动，否则，则将带排元素插入到该元素后面。
        if arr[i]<arr[i-1]: #若arr[i]小于其前驱
            temp = arr[i]  # 保存arr[i]
            global j
            for j in range(i-1,-1,-1): #从后往前比较
                # print("element：%s"%j)
                if arr[j]>temp:
                    arr[j+1]=arr[j]
                location=j
            arr[location]=temp  #无法解决记录最终位置问题

    return arr

elements=[1,7,8,4,3,2,6,5]

print(insertSort(elements))
# print(insert_sort(elements))






