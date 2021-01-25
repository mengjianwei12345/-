

        

public class HeapSort {

    public static void main(String[] args) {
        int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

        heapSort(array);

        System.out.println(Arrays.toString(array));
    }
    public static void heapSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, length);
        }
        for (int j = length - 1; j > 0; j--) {
            swap(array, 0, j);
            adjustHeap(array, 0, j);
        }

    }

    private static void adjustHeap(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            if (array[k] > temp) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        array[i] = temp;
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}
