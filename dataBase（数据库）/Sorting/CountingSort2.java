


public class CountingSort {

    public static void countingSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        int max = array[0];
        int min = array[0];
        for (int i = 0; i < length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        } 
        int offset = max - min + 1;
        int[] count = new int[offset + 1];
        for (int i = 0; i < length; i++) {
            count[array[i] - min + 1]++;
        }
        for (int i = 0; i < offset; i++) {
            count[i + 1] += count[i];
        }

        int[] aux = new int[length];
        for (int i = 0; i < length; i++) {
            aux[count[array[i] - min]++] = array[i];
        }
        for (int i = 0; i < length; i++) {
            array[i] = aux[i];
        }
    }

}
