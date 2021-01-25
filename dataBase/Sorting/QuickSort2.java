



public class QuickSort {
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }


    private static void quickSort(int[] array, int left, int right) {
        if (array == null || left >= right || array.length <= 1) {

            return;
        }
        int mid = partition(array, left, right);
        quickSort(array, left, mid);
        quickSort(array, mid + 1, right);
    }
private static int partition(int[] array, int left, int right) {
        int temp = array[left];
        while (right > left) {
            while (temp <= array[right] && left < right) {
                --right;
            }
            if (left < right) {
                array[left] = array[right];
                ++left;
            }
            while (temp >= array[left] && left < right) {
                ++left;
            }
            if (left < right) {
                array[right] = array[left];
                --right;
            }
        }
        array[left] = temp;
        return left;
    }


}
