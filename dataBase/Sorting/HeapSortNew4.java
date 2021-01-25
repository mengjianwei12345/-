


public class HeapSortNew {
    public static void maxHeap(int[] array) {
        for (int i = (array.length - 2); i >= 0; i--) {
            moveNode(array, i, array.length - 1);
        }
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i);
            moveNode(array, 0, i - 1);
        }

    }
    public static void moveNode(int[] array, int current, int length) {
        int temp = array[current];
        for (int y = 2 * current + 1; y <= length; y = 2 * y + 1) {
            if (y < length && array[y] < array[y + 1]) {
                y++;
            }
            if (temp > array[y]) {
                break;
            }
            array[current] = array[y];
            current = y;
        }
        array[current] = temp;
    }

    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
