
public class QuickSort {
	public static int quickSort(int[] a,int low,int high){
		int i=low;
		int j=high;
		while(i<j){
			int temp;
			while(i<j&&a[i]<=a[j]){j--;}
			if(i<j){
				temp=a[i];
				a[i]=a[j];
				a[j]=temp;
				i++;
			}
			while(i<j&&a[i]<=a[j]){i++;}
			if(i<j){
				temp=a[i];
				a[i]=a[j];
				a[j]=temp;
				j--;
			}
		}
		return i;
	}
	public static void quickSortGo(int[] a,int start,int end){
		if(start<end){
		int pioviate=quickSort(a,start,end);
		quickSortGo(a,start,pioviate-1);
		quickSortGo(a,pioviate+1,end);
		}
	}

}
