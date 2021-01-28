


public class ShellSort {
    public static void shellSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int length = array.length;
        int temp, gap = length / 2;

        while (gap > 0) {
            for (int i = gap; i < length; i++) {
                temp = array[i];
                int preIndex = i - gap;

                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;

            }
            gap /= 2;
        }
    }

}
