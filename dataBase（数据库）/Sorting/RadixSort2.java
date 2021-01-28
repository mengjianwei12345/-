


public class RadixSort {
    public static void radixSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int length = array.length;
        int radix = 10;
        int[] aux = new int[length];
        int[] count = new int[radix + 1];
        int x = Arrays.stream(array).map(s -> String.valueOf(s).length()).max().getAsInt();
        for (int d = 0; d < x; d++) {
            for (int i = 0; i < length; i++) {
                count[digitAt(array[i], d) + 1]++;
            }
            for (int i = 0; i < radix; i++) {
                count[i + 1] += count[i];
            }
            for (int i = 0; i < length; i++) {
                aux[count[digitAt(array[i], d)]++] = array[i];
            }
            for (int i = 0; i < length; i++) {
                array[i] = aux[i];
            }
            for (int i = 0; i < count.length; i++) {
                count[i] = 0;
            }

        }
    }
    private static int digitAt(int value, int d) {
        return (value / (int) Math.pow(10, d)) % 10;
    }

}
