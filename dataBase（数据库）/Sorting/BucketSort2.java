



public class BucketSort {

    public static void bucketSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;
        
        LinkedList<Integer>[] bucket = (LinkedList<Integer>[]) new LinkedList[length];
        int maxValue = Arrays.stream(array).max().getAsInt();
        for (int i = 0; i < array.length; i++) {
            int index = toBucketIndex(array[i], maxValue, length);)
            if (bucket[index] == null) {
                bucket[index] = new LinkedList<>();
            }
            bucket[index].add(array[i]);
        }

        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (bucket[i] != null) {
                Collections.sort(bucket[i]);
                temp.addAll(bucket[i]);
            }
        }
        for (int i = 0; i < length; i++) {
            array[i] = temp.get(i);
        }
    }


    private static int toBucketIndex(int value, int maxValue, int length) {
        return (value * length) / (maxValue + 1);
    }

}
