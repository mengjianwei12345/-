

public class BinaryTree {
    public static void preOrder(String[] array, int index) {
        if (index >= array.length) {
            return;
        }
        String value = array[index];
        System.out.print(value + " ");
        preOrder(array, 2 * index);
        preOrder(array, 2 * index + 1);
    }

    public static void midOrder(String[] array, int index) {
        if (index >= array.length) {
            return;
        }
        midOrder(array, 2 * index);
        String value = array[index];
        System.out.print(value + " ");
        midOrder(array, 2 * index + 1);
    }

   
    public static void postOrder(String[] array, int index) {
        if (index >= array.length) {
            return;
        }
        postOrder(array, 2 * index);
        postOrder(array, 2 * index + 1);
        String value = array[index];
        System.out.print(value + " ");
    }


    public static void main(String[] args) {
        String[] array = {"", "a", "b", "c", "d", "e", "f", "g"};
        //preOrder(array, 1);
        //midOrder(array, 1);
        postOrder(array,1);
    }

}
