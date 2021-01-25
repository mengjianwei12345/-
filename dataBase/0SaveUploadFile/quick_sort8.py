# -*- coding: utf-8 -*-

def get_pivot_element_index(lists, left, right):
    
    pivot_element = lists[left]

    while left < right:
        if lists[right] >= pivot_element:
            right -= 1
        else:
            lists[left] = lists[right]
            left += 1
            lists[right] = lists[left]

    lists[right] = pivot_element

    return right


def quick_sort(lists, left, right):
    
    if left < right:
        pivot_element_index = get_pivot_element_index(lists, left, right)
        quick_sort(lists, left, pivot_element_index - 1)
        quick_sort(lists, pivot_element_index + 1, right)



