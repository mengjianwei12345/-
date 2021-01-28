



public class SelectionSort {
    public static void selectionSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;

        for (int i = 0; i < length - 1; i++) {
            // 保存最小数的索引
            int minIndex = i;

            for (int j = i + 1; j < length; j++) {
                // 找到最小的数
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // 交换元素位置
            if (i != minIndex) {
                swap(array, minIndex, i);
            }
        }

    }
    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}
