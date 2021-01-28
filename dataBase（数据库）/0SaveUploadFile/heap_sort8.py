# -*- coding: utf-8 -*-


# 算法核心思想：将待排序的数组构建大（小）顶堆，然后利用堆的性质进行排序
# 算法介绍网站：https://zh.wikipedia.org/wiki/%E5%A0%86%E6%8E%92%E5%BA%8F


def shift_down(lists, start, end):
   

    root = start

    while True:
        child = 2 * root + 1

        if child > end:
            break

        if child + 1 <= end and lists[child] < lists[child + 1]:
            child += 1

        if lists[root] < lists[child]:
            lists[root], lists[child] = lists[child], lists[root]
            root = child
        else:
            break


def build_max_heap(lists):
   
    for i in range(len(lists) / 2 - 1, -1, -1):
        shift_down(lists, i, len(lists) - 1)


def heap_sort(lists):
   
    build_max_heap(lists)

    for i in range(len(lists) - 1, 0, -1):
        lists[0], lists[i] = lists[i], lists[0]

        shift_down(lists, 0, i - 1)


