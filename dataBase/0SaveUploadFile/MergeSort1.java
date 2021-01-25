
public class MergeSort {
	public static void merge(int[] a,int low,int mid,int high){
		int i=low;
		int j=mid+1;
		int k=0;
		int[] temp=new int[high-low+1]; 
	    while(i<=mid&&j<=high){
	    	if(a[i]<a[j]){
	    		temp[k++]=a[i++];
	    	}else{
	    		temp[k++]=a[j++];
	    	}
	    }
	    while(i<=mid){temp[k++]=a[i++];}
	    while(j<=high){temp[k++]=a[j++];}
	    for(int k1=0;k1<temp.length;k1++){
	    	a[k1+low]=temp[k1];
	    }
	}
	public static void mergeSort(int[] a,int low,int high){
		int mid=(low+high)/2;
		if(low<high){
			mergeSort(a,low,mid);
			mergeSort(a,mid+1,high);
			merge(a,low,mid,high);
		}
	}
}
