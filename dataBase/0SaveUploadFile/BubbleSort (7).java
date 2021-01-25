

/**
 * 冒泡排序，冒泡的思路是每次从数组底部将两两相近的元素对比，排序。
 * 
 * 顾名思义，冒泡的意思就是数据像气泡一样从底部升起，直到露出水面。
 * 在每次一排序中，通过元素的比较，逐渐找出最小的那一个，直到其露出水面。
 * @author kexun
 *
 */
public class BubbleSort {
	
	public static void main(String[] args) {
		
		int[] data = new int[100000];
		for (int i = 0; i < 100000; i++) {
			int num =(int) (Math.random() * 1000000);
			data[i] = num;
		}
		
		long startTime = System.currentTimeMillis();
		BubbleSort b = new BubbleSort();
//		b.sort(data);
		b.optimizedSort(data);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
//		for (int i : data) {
//			System.out.println(i);
//		}
	}
	
	// 这种是常规的冒泡排序方式，从我的测试数据中可以看出，[2,1,3,4,5,6,7,8,9,10]只需要交换2和1
	// 其余元素均是以排序，但是这个算法并没有考虑到这些情况，依然执行了45此运算比较。因此这个算法
	//　任有优化的余地
	public void sort(int[] data) {
		
		if (data.length == 0) {
			return;
		}
		
		int length = data.length;
		int index = 0;
		for (int i=0; i<length-1; i++) {
			
			for (int j=length-1; j>i; j--) {
				index++;
				if (data[j] < data[j-1]) {
					swap(data, j, j-1);
				}
			}
		}
		
		System.out.println("运行次数： "+index);
	}

	// 这种方式是对冒泡排序的优化， 通过增加一个flag状态控制，如果后面的元素全都是顺序的，
	// 那么后面就不需要在循环遍历了
	public void optimizedSort(int[] data) {
		
		if (data.length == 0) {
			return;
		}
		
		boolean flag = true;
		int length = data.length;
		int index = 0;
		
		for (int i=0; i<length-1 && flag; i++) {
			
			flag = false;
			for (int j=length-1; j>i; j--) {
				index++;
				if (data[j] < data[j-1]) {
					swap(data, j, j-1);
					if (j > i+1) {
						flag = true;
					}
				}
			}
		}
		
		System.out.println("运行次数： "+index);
	}
	
	private void swap(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
}
