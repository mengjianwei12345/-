# -*- coding: utf-8 -*-

def bubble_sort(lists):
   
    length = len(lists)

    for i in range(0, length):
        did_swap = False

        for j in range(0, length - 1 - i):
            if lists[j] > lists[j + 1]:
                lists[j], lists[j + 1] = lists[j + 1], lists[j]
                did_swap = True

        if did_swap is False:
            return


