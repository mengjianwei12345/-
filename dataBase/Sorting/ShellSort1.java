
public class ShellSort {
	public static void shellSort(int[] a) {
		int size = a.length;
		for (int i = size / 2; i > 0; i /= 2) {
			for (int j = i; j < size; j++) {
				for (int k = j - i; k >= 0; k -= i) {
					int temp;
					if (a[k] > a[k + i]) {
						temp = a[k];
						a[k] = a[k + i];
						a[k + i] = temp;
					}
				}
			}
		}
	}
}
