



public class MergeSort {

    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        sort(array, left, mid);
        sort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            temp[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
        }
        while (p1 <= mid) {
            temp[i++] = array[p1++];
        }
        while (p2 <= right) {
            temp[i++] = array[p2++];
        }
        for (i = 0; i < temp.length; i++) {
            array[left + i] = temp[i];
        }
    }

}
