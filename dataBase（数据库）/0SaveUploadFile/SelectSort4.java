
public class SelectSort {
	public static void selectSort(int[] array){
		for(int i=0;i<array.length-1;i++){
			int min = i;
			for(int j=i+1;j<array.length;j++){
				if(array[min]>array[j]){
					min = j;
				}
			}
			if(min != i){
				int temp = array[min];
				array[min] = array[i];
				array[i] = temp;
			}
		}
	}
}
