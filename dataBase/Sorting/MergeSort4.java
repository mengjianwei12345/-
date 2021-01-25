
public class MergeSort {

	public static void merging(int []array,int low,int mid,int high){
		int []temp = new int[10];
		int i = low;
		int j = mid + 1;
		int k = 0;
		while(i<=mid && j<=high){
			if(array[i]<array[j]){
				temp[k++] = array[i++];
			}else{
				temp[k++] = array[j++];
			}
		}
		while(i<=mid){
			temp[k++] = array[i++];
		}
		while(j<=high){
			temp[k++] = array[j++];
		}

		for(int m=0;m<k;m++){
			array[low+m] = temp[m];
		}
	}
	
	public static void merge_sort(int []array,int low,int high){
		if(low<high){
			int mid = (low+high)/2;
			merge_sort(array,low,mid);
			merge_sort(array,mid+1,high);
			merging(array,low,mid,high);
		}
	}
}
