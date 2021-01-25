


public class QuickSort {
	public static void Qsort(int [] numbers,int low,int high){
		int pivot;
		if(low<high){
			pivot = partition(numbers,low,high);
			Qsort(numbers,low,pivot-1);
			Qsort(numbers,pivot+1,high);
		}
	}
	
	public static int partition(int [] numbers,int low,int high){
		
		int pivotValue=numbers[low];
		int temp=low;
	    while(low<high){
	    	while(low<high && numbers[high]>=pivotValue){
	    		high--;
	    	}
	    	if(numbers[high]<pivotValue){
	    		swap(numbers,temp,high);
	    		temp=high;
	    	}
	    	while(low<high && numbers[low]<=pivotValue){
	    		low++;
	    	}
	    	if(numbers[low]>pivotValue){
	    		swap(numbers,temp,low);
	    		temp=low;
	    	}
	    }
		return low;
	}
	public static void swap(int [] numbers,int i,int y){
		int temp=0;
		temp=numbers[i];
		numbers[i]=numbers[y];
		numbers[y]=temp;
	}
	public static void QsortOptimize(int [] numbers,int low,int high){
		
		int pivot;
		if(low<high){
			pivot = partitionOptimize(numbers,low,high);
			QsortOptimize(numbers,low,pivot-1);
			QsortOptimize(numbers,pivot+1,high);
		}
	}
	public static int partitionOptimize(int [] numbers,int low,int high){
		
		medianOfThree(numbers,low,high);
		int pivotValue = numbers[low];
		while(low<high){
			while(low<high && numbers[high]>=pivotValue){
				high--;
			}
			numbers[low] = numbers[high];
			while(low<high && numbers[low]<=pivotValue){
				low++;
			}
			numbers[high] = numbers[low];
		}
		
		numbers[low] = pivotValue;
		
		return low;
	}
	public static void medianOfThree(int [] numbers,int low,int high){
		int m = low+(high-low)/2;
		if(numbers[low]>numbers[high]){
			swap(numbers,low,high);
		}
		if(numbers[m]>numbers[high]){
			swap(numbers,m,high);
		}
		if(numbers[m]>numbers[0]){
			swap(numbers,m,low);
		}
	}
	
	public static void quickSortFinal(int [] numbers,int low,int high){
		int pivot;
		while(low<high){
			pivot=partitionOptimize(numbers, low, high);
			quickSortFinal(numbers,low,pivot-1);
			low=pivot+1;
		}
	}
}
