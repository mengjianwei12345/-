

public class ShellSort {
	public static void shellSort(int []array){
		
		int j = 0,gap = array.length;
		while(gap>0){
			gap = gap/2;
			for(int i=gap;i<array.length;i++){
				if(array[i-gap]>array[i]){
					int temp = array[i];
					for( j=i-gap;j>=0 && array[j]>temp;j-=gap){
						array[j+gap] = array[j];
					}
						array[j+gap] = temp;
						
				}
			}
		}
	}
}
