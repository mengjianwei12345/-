

public class SelectSort {	
	public static void selectSort(int [] numbers){		
		int min;
		for(int i=0;i<numbers.length-1;i++){
			min=i;
			for(int y=i+1;y<numbers.length;y++){
				if(numbers[min]>numbers[y]){
					min=y;
				}
			}
			if(i!=min){
				swap(numbers,i,min);
			}
		}
	}
	public static void swap(int [] numbers,int i,int y){
		int temp=0;
		temp=numbers[i];
		numbers[i]=numbers[y];
		numbers[y]=temp;
	}
}
