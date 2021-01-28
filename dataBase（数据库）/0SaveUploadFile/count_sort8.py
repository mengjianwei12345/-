# -*- coding: utf-8 -*-


# 算法核心思想：使用一个额外的数组C，其中第i个元素是待排序数组A中值等于i的元素的个数，然后根据数组C来将A中的元素排到正确的位置。
# 算法介绍：https://zh.wikipedia.org/wiki/%E8%AE%A1%E6%95%B0%E6%8E%92%E5%BA%8F


def count_sort(lists):
   

    lists_min = min(lists)  # 待排序数组的最小值
    lists_max = max(lists)  # 待排序数组的最大值

    result_lists = [0 for _ in xrange(len(lists))]  # 排序结果数组
    c = [0 for _ in xrange(lists_max - lists_min + 1)]  # 辅助数组C

    # 计数
    for i in lists:
        c[i - lists_min] += 1

    # 累加
    for i in xrange(1, len(c)):
        c[i] = c[i - 1] + c[i]

    # 反向填充
    for i in lists:
        result_lists[c[i - lists_min] - 1] = i
        c[i - lists_min] -= 1

    return result_lists

