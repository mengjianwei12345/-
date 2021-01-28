#!/usr/bin/env python
#-*- coding:utf-8 -*-
# author:hanzhengrong
# datetime:2018-07-13 15:45
# software: PyCharm


def select_sort(arr):
    arr_len=len(arr)
    for i in range(arr_len-1):
        min_arr = arr[i]
        for j in range(i+1,arr_len):
            if arr[j]<arr[i]:
                arr[j],arr[i]=arr[i],arr[j]
    return arr
