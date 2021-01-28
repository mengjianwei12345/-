# -*- coding: utf-8 -*-

def merge(left_sub_sort_lists, right_sub_sort_lists):
   

    merge_result = []

    i, j = 0, 0

    while i < len(left_sub_sort_lists) and j < len(right_sub_sort_lists):
        if left_sub_sort_lists[i] <= right_sub_sort_lists[j]:
            merge_result.append(left_sub_sort_lists[i])

            i += 1
        else:
            merge_result.append(right_sub_sort_lists[j])

            j += 1

    merge_result += left_sub_sort_lists[i:]
    merge_result += right_sub_sort_lists[j:]

    return merge_result


def merge_sort(lists):
   

    if len(lists) <= 1:
        return lists

    mid = len(lists) / 2

    left_sub_sort_lists = merge_sort(lists[:mid])
    right_sub_sort_lists = merge_sort(lists[mid:])

    merge_result = merge(left_sub_sort_lists, right_sub_sort_lists)

    return merge_result


