

public class InsertSort {
	public static void insertSort(int []array){
		int j = 0;
		for(int i=0;i<array.length-1;i++){
			if(array[i]>array[i+1]){
				int temp = array[i+1];
				for( j=i;j>=0 && array[j]>temp;j--){
					array[j+1] = array[j];
				}
					array[j+1] = temp;
					
			}
		}
	}
}
