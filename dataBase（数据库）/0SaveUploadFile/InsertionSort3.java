

public class InsertionSort {
    public static void insertionSort(int[] nums) {
        int n = nums.length;
        int j;
        for (int i = 1; i < n; i++) {
            int temp = nums[i];
            for (j = i - 1; j >= 0 && temp < nums[j]; j--) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = temp;
        }
    }
}
