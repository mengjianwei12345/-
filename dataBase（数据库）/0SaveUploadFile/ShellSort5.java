


public class ShellSort {
	
	public static void shellSort(int [] numbers){
		
		int increment=numbers.length; 
		int temp; 
		do{
			increment = increment/3+1;
			int y;
			for(int i=increment;i<numbers.length;i++){
				temp=numbers[i];
				for(y=i-increment;y>=0 && numbers[y]>temp;y-=increment){
					numbers[y+increment]=numbers[y];
				}
				numbers[y+increment]=temp;
			}
		}while(increment>1);
	}
}
