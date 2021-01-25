#!/usr/bin/env python
#-*- coding:utf-8 -*-
import random

def  bubleSort(list):
    for i in range(len(list)): #从前向后开始排序
        flag=False #标志是否还需要比较
        for j in range(len(list)-1,i,-1): #从后向前开始比较
            if list[j]<list[j-1]: #小的在前，大的在后
                list[j],list[j-1]=list[j-1],list[j]
                flag=True
        if flag==False:
            break
    return list

