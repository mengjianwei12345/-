

public class BubbleSort {
	public static void bubbleSort(int [] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=i+1;j<array.length;j++){
				if(array[i]>array[j]){
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
	}
	
	public static void reBubbleSort(int [] array){
		for(int i=0;i<array.length-1;i++){
			for(int j=array.length-1;j>i;j--){
				if(array[j-1]>array[j]){
					int temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
				}
			}
		}
	}
}
