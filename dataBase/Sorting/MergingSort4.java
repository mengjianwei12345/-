


public class MergingSort {
	public static void mergingSort(int [] SR,int [] TR1,int s,int n){
		
		int [] TR2=new int[SR.length];
		
		if(s==n){
			TR1[s]=SR[s];
		}else{
			int m=(s+n)/2;
			mergingSort(SR,TR2,s,m);
			mergingSort(SR,TR2,m+1,n);
			merge(TR2,TR1,s,m,n);
		}
	}
	

	public static void mergeSort(int [] numbers){
		int k=1;
		int [] TR=new int[numbers.length];
		while(k<numbers.length-1){
			mergePass(numbers,TR,k,numbers.length-1);
			k=k*2;
			mergePass(TR,numbers,k,numbers.length-1);
			k=k*2;
		}
	}
	
	public static void mergePass(int [] SR,int []TR,int k, int n){
		int i=0;
		int j;
		while(i<=n-2*k+1){
			merge(SR,TR,i,i+k-1,i+2*k-1);
			i=i+2*k;
		}
		
		if(i<n-k+1){ 
			merge(SR,TR,i,i+k-1,n);
		}else{
			for(j=i;j<=n;j++){ 
				TR[j]=SR[j];
			}
		}
	}
	
	public static void merge(int [] SR,int [] TR,int s,int m,int n){
		int j,k,c;
		for(j=m+1,k=s;s<=m && j<=n;k++){
			if(SR[s]<SR[j]){
				TR[k]=SR[s++];
			}else{
				TR[k]=SR[j++];
			}
		}
		
		if(s<=m){
			for(c=0;c<=m-s;c++){
				TR[k+c]=SR[s+c];
			}
		}
		
		if(j<=n){
			for(c=0;c<=n-j;c++){
				TR[k+c]=SR[j+c];
			}
		}
	}

}
