

public class BubbleSort {

	public static void BubbleSortImprove(int[] nums) {
		int n = nums.length;
		for (int i = 0; i < n - 1; i++) {
			int flag = 0;
			for (int j = 0; j < n - i - 1; j++) {
				if (nums[j] > nums[j + 1]) {
					int temp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = temp;
					flag = 1;// 进行了元素交换的标志
				}
			}
			if (flag == 0) {
				break;
			}
		}
	}

}
