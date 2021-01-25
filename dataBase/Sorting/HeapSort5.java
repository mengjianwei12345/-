


public class HeapSort {
	
	public static void heapSort(int [] numbers){
		for(int i=(numbers.length-1)/2;i>=0;i--){
			heapAbjust(numbers,i,numbers.length-1);
		}
		for(int y=numbers.length-1;y>0;y--){
			swap(numbers,0,y);
			heapAbjust(numbers,0,y-1);
		}
		
	}
	public static void heapAbjust(int [] numbers,int s,int m){
		int temp=numbers[s]; //临时变量
		for(int y=s*2;y<=m;y*=2){
			if(y<m && numbers[y]<numbers[y+1]){
				y++;
			}
			if(temp>=numbers[y]){
				break;
			}
			numbers[s]=numbers[y];
			s=y; 
		}
		numbers[s]=temp;
	}
	public static void swap(int [] numbers,int i,int y){
		int temp=0;
		temp=numbers[i];
		numbers[i]=numbers[y];
		numbers[y]=temp;
	}
}
