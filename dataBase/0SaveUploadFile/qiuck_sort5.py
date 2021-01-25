def quick_sort(arr, low, high):
     #temp = a[0]
     i = low
     j = high
     if i >= j:
          return arr
     temp = arr[i]
     while i < j:
          while i < j and arr[j] >= temp:
               j = j-1
          arr[i] = arr[j]
          while i < j and arr[i] <= temp:
               i = i + 1
          arr[j] = arr[i]
     arr[i] = temp
     quick_sort(arr, low, i-1)
     quick_sort(arr, j+1, high)
     return arr

