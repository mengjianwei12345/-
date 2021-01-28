



public class HeapSort {
     final static int MAX=10000; //待排序数组大小
    public static void adjustHeap(int array[],int top,int length){
        int temp=array[top]; //暂存堆顶元素
        //比较左右子树根结点，从大的子树向下遍历调整堆
        for(int i=2*top+1;i<length;i=i*2+1){
            //保证i为较大的子树下标
            if(i<length-1&&array[i]<array[i+1]){
                i++;
            }
            if(temp>array[i]){
                break;
            }
            array[top]=array[i];
            top=i;//向下搜索
        }
        array[top]=temp;
    }

    public static void heapSort(int array[]){
        int length=array.length;
        //初始化大顶堆
        for(int i=(length-2)/2;i>=0;i--){
            adjustHeap(array,i,length);
        }

        //每次取堆顶元素与堆尾元素交换，再重新调整成大顶堆
        for(int i=length-1;i>0;i--){
            int temp=array[0];
            array[0]=array[i];
            array[i]=temp;
            adjustHeap(array,0,i);
        }

    }
}
