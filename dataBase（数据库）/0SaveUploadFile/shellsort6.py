#!/usr/bin/env python
#-*- coding:utf-8 -*-
# author:hanzhengrong
# datetime:2018-07-13 15:46
# software: PyCharm

'''
希尔排序

算法思想：
先将待排序表分割成若干形如L[i,i+d,i+2d,...,i+kd]的"特殊"子表，分别进行直接插入排序，
当整个表中元素已呈"基本有序"时，面对全体记录进行一次直接插入排序

'''

def shell_sort(arr):
    #记录待排序列的长度
    arr_len=len(arr)
    #初始化步长为一半数组长度
    dk=int(arr_len/2)
    while dk>=1:
        for i in range(dk,arr_len):
            #对dk步长内的元素排序
            if arr[i]<arr[i-dk]:  #将arr[i]插入有序增量子表
                temp=arr[i]  #暂存arr[i]
                j=i-dk
                while j>=0 and temp<arr[j]:
                    arr[j+dk]=arr[j]  #记录后移，查找插入位置
                    j=j-dk
                arr[j+dk]=temp #插入
        dk=int(dk/2) #步长变化
    return arr

elements=[4,5,7,9,4,6,3,0]

print(shell_sort(elements))