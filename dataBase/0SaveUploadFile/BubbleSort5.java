


public class BubbleSort {
	public static void bubbleSortPrimary(int [] numbers){
		int size=numbers.length;
		for(int i=0;i<size-1;i++){
			for(int y=i+1;y<size;y++){
				if(numbers[i]>numbers[y]){
					swap(numbers,i,y);
				}
			}
		}
	}
	public static void bubbleSortNormal(int [] numbers){
		int size=numbers.length;
		for(int i=0;i<size-1;i++){
			for(int y=1;y<size;y++){
				if(numbers[y-1]>numbers[y]){
					swap(numbers,y-1,y);
				}
			}
		}
	}
	public static void bubbleSortOptimize(int [] numbers){
		int size=numbers.length;
		boolean flag=true;
		for(int i=0;i<size-1 && flag;i++){
			flag=false;
			for(int y=1;y<size-i;y++){
				if(numbers[y-1]>numbers[y]){
					swap(numbers,y-1,y);
					flag=true;
				}
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
