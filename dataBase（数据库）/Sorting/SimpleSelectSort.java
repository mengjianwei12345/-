
public class SimpleSelectSort {
    public static void simpleSelectSort(int[] a){
    	for(int i=0;i<a.length-1;i++){
    		int temp;
    		int min=a[i];
    		int k=i;
    		for(int j=i+1;j<a.length;j++){
    			 if(a[j]<min){
    				 min=a[j];
    				 k=j;
    			 }
    		}
    		temp=a[i];
    		a[i]=a[k];
    		a[k]=temp;
    	}
    }
}
