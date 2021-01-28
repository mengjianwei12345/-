

public class MergeSort {
   
    public static void mergeSort(int[] nums) {
        sort(nums, 0, nums.length - 1);
    }

    public static void sort(int[] nums, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = (low + high) / 2;
        sort(nums, low, mid);
        sort(nums, mid + 1, high);
        merge(nums, low, mid, high);
//   
    }

    public static void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[nums.length];
        for (int k = low; k <= high; k++) {
            temp[k] = nums[k];
        }
        int i = low; 
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                nums[k] = temp[j];
                j++;
            } else if (j > high) {
                nums[k] = temp[i];
                i++;
            } else if (temp[i] > temp[j]) {
                nums[k] = temp[j];
                j++;
            } else {
                nums[k] = temp[i];
                i++;
            }
        }
    }
}
