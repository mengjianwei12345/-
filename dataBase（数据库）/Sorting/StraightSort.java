

public class StraightSort {
	public static void straightSort(int[] a){
		for(int i=1;i<a.length;i++){
			int j=0;
			int temp=a[i];  
			for(j=i-1;j>=0;j--){
				 if(a[j]>temp){
					 a[j+1]=a[j];
				 }else{
					 break;
				 }
			}
		   a[j+1]=temp; 
		}
	}

}
