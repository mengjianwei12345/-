

public class BubbleSort {
	public static void bubbleSort(int[] a){
		for(int i=0;i<a.length-1;i++){
			for(int j=1;j<a.length-i;j++){
				int temp;
				if(a[j]<a[j-1]){
					temp=a[j];
					a[j]=a[j-1];
					a[j-1]=temp;
				}
			}
		}
	}

}
