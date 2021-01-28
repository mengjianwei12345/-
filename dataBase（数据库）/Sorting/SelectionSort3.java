

public class SelectionSort {
	public static void selectionSort(int[] nums) {
		int n = nums.length;
		int temp;
		for (int i = 0; i < n-1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (nums[i] > nums[j]) {
					temp = nums[i];
					nums[i] = nums[j];
					nums[j] = temp;
				}
			}
		}
	}
}
