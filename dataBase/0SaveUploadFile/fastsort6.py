#!/usr/bin/env python
#-*- coding:utf-8 -*-
# author:hanzhengrong
# datetime:2018-07-13 15:46
# software: PyCharm


def Partition(arr,low,high):
    pivot=arr[low]  #将当前表中第一个元素设为枢轴值，对表进行划分
    while low<high:
        while low<high and arr[high]>=pivot:
            high=high-1
        arr[low]=arr[high]  #将比枢轴小的元素移动到左端

        while low<high and arr[low]<pivot:
            low=low+1
        arr[high]=arr[low] #将比枢轴大的元素移动到右端
    arr[low]=pivot #枢轴元素放到最终位置
    return low  #返回存放枢轴的最终位置

def quick_sort(arr,low,high):
    if low<high:
        pivotpos=Partition(arr,low,high) #划分
        quick_sort(arr,low,pivotpos-1)   #依次对两个子表进行递归排序
        quick_sort(arr,pivotpos+1,high)
    return arr

