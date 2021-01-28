


public class RadixSort {
    public static void radixSort(String[] array) {
       String[] tmp = array;
        for (int i = tmp[0].length(); i > 0; i--) {

            String[] tmp2 = new String[tmp.length];

            int max = 0;
            for (int j = 0; j < tmp.length; j++) {
                int singleValue = Integer.parseInt(tmp[j].substring(i - 1, i));
                if (singleValue > max) {
                    max = singleValue;
                }
            }
            int[] indexArray = new int[max + 1];
            for (int j = 0; j < tmp.length; j++) {
                int singleValue = Integer.parseInt(tmp[j].substring(i - 1, i));
                indexArray[singleValue] = indexArray[singleValue] + 1;
            }
            int sum = 0;
            for (int j = 0; j < indexArray.length; j++) {
                indexArray[j] = indexArray[j] + sum;
                sum = indexArray[j];
            }

            for (int j = tmp.length - 1; j >= 0; j--) {
                int singleValue = Integer.parseInt(tmp[j].substring(i - 1, i));
                int index = indexArray[singleValue];
                tmp2[index - 1] = tmp[j];
                indexArray[singleValue]--;
            }

            tmp = tmp2;
        }

        for (int i = 0; i < tmp.length; i++) {
            array[i] = tmp[i];
        }
    }
}
