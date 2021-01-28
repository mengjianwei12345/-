



public class StraightInsertionSort {
	
	public static void straightInsertionSort(int [] numbers){
		
		int temp=0; 
		
		for(int i=1;i<numbers.length;i++){
			int y;
			if(numbers[i-1]>numbers[i]){
				temp = numbers[i];
				for(y=i-1;y>=0 && numbers[y]>temp;y--){
					numbers[y+1]=numbers[y];
				}
				numbers[y+1]=temp;
			}
		}
	}

}
