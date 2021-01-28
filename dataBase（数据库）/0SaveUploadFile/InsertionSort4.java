

public class InsertionSort {
	static void insertionsort(int arr[])
	{
		for(int j=1;j<arr.length;j++)
		{
			int key=arr[j];
			int i=j-1;
			while(i>-1 && arr[i]>key)
			{
				arr[i+1]=arr[i];
				i--;
			}
			arr[i+1]=key;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InsertionSort s=new InsertionSort();
		int arr[]= {2,5,3,6,7,31,1,2};
		s.insertionsort(arr);
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+" ");
		}

	}

}
