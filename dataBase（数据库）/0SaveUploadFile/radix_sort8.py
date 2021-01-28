# -*- coding: utf-8 -*-

import math


def radix_sort_nums(lists, radix):
    """ 获取待排序数据中最大位数

    Args:
        lists: list
            带排序的数组

        radix: int
            基数排序中的基数

    Returns:
        k: int
            待排序数据中最大位数
    """

    max_num = max(lists)

    k = int(math.ceil(math.log(max_num, radix)))

    return k


def get_num_pos(num, pos):
    """ 获取指定元素在某一位的数值

    Args:
         num: int
            指定的元素

         pos: int
            指定的位置

    Returns:
        num_pos: int
            指定元素在某一位的数值
    """

    num_pos = (num / (10 ** (pos - 1))) % 10

    return num_pos


def radix_sort(lists, radix=10):
    """ 实现基数排序

    Args:
        lists: list
            带排序的数组

        radix: int
            基数，默认为10
    """

    k = radix_sort_nums(lists, radix)  # 最大位数

    for pos in range(1, k + 1):
        buckets = [[] for _ in range(radix)]

        for j in lists:
            num_pos = get_num_pos(j, pos)

            buckets[num_pos].append(j)

        del lists[:]

        for bucket in buckets:
            lists.extend(bucket)


