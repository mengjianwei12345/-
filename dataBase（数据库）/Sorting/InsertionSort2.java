



public class InsertionSort {

    public static void insertionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int length = array.length;
        int insertNum;

        for (int i = 1; i < length; i++) {
            insertNum = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > insertNum) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = insertNum;
        }
    }

}
