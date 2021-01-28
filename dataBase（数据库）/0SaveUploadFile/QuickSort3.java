

public class QuickSort {

    public static void quickSort(int[] nums) {
        partition(nums, 0, nums.length - 1);
    }

    public static void partition(int[] array, int left, int right) {
        if (left >= right)
            return;
        int target = array[left];
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && array[j] >= target)
                j--;
            if (array[j] < target) {
                array[i] = array[j];
                i++;
            }
            while (i < j && array[i] <= target) {
                i++;
            }
            if (array[i] > target) {
                array[j] = array[i];
                j--;
            }
        }
        array[i] = target;
        partition(array, left, i - 1);
        partition(array, i + 1, right);
    }
}
