

/**
 * 插入排序
 */
public class InsertSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i;
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != i) {
                arr[j] = tmp;
            }

        }
        return arr;
    }
}
