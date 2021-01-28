


public class CountSort {
    public static void countSort(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        int[] indexArray = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            indexArray[value] = indexArray[value] + 1;
        }
        int sum = 0;
        for (int i = 0; i < indexArray.length; i++) {
            indexArray[i] = indexArray[i] + sum;
            sum = indexArray[i];
        }
        int[] tmp = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int index = indexArray[value];
            tmp[index - 1] = value;
            indexArray[value]--;
        }
        for (int i = 0; i < tmp.length; i++) {
            array[i] = tmp[i];
        }
    }
}
