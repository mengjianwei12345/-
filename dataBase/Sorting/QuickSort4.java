

public class QuickSort {

	public static void quick_sort(int []array,int low,int high){
		if(low<high){
			int pos = partition(array,low,high);
			quick_sort(array,low,pos-1);
			quick_sort(array,pos+1,high);
		}
	}

	private static int partition (int[] array, int low, int high) {
		int key = array[low];
		while(low<high){
			while(low<high && array[high]>=key){
				high--;
			}
			if(low<high){
				array[low++] = array[high];
			}
			while(low<high && array[low]<=key){
				low++;
			}
			if(low<high){
				array[high--] = array[low];
			}
		}
		array[low] = key;
		return low;
	}
}
