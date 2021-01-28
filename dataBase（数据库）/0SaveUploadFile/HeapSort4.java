

public class HeapSort {

	private void swap(int[] a,int m,int n){
		int temp = a[m];
		a[m] = a[n];
		a[n] = temp;
	}
	private void bigHeap(int[] a,int parent,int length){
		int left = 2 * parent + 1;
		int right = 2 * parent + 2;
		int max = parent;
		if(left < length && a[left] > a[max]){
			max = left;
		}
		if(right < length && a[right] > a[max]){
			max = right;
		}
		if(max != parent){
			swap(a,max,parent);
			bigHeap(a, max, length);
		}
	}
	public void sort(int[] a,int length){
		for(int j=length/2-1;j>=0;j--){
			bigHeap(a, j, length);
		}
		for(int j=length-1;j>=0;j--){
			swap(a,j,0);
			bigHeap(a, 0, j);
		}
	}
	
}
