

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SortAlgorithm
 */
public class SelectionSort implements SortAlgorithm {

  /**
   * This method swaps the two elements in the array
   *
   * @param <T>
   * @param arr, i, j The array for the swap and the indexes of the to-swap elements
   */
  public <T> void swap(T[] arr, int i, int j) {
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  /**
   * This method implements the Generic Selection Sort
   *
   * @param arr The array to be sorted Sorts the array in increasing order
   */
  @Override
  public <T extends Comparable<T>> T[] sort(T[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      // Initial index of min
      int min = i;

      for (int j = i + 1; j < n; j++) {
        if (arr[min].compareTo(arr[j]) < 0) {
          min = j;
        }
      }

      // Swapping if index of min is changed
      if (min != i) {
        swap(arr, i, min);
      }
    }

    return arr;
  }

  // Driver Program
  public static void main(String[] args) {

    Integer[] arr = {4, 23, 6, 78, 1, 54, 231, 9, 12};

    SelectionSort selectionSort = new SelectionSort();

    Integer[] sorted = selectionSort.sort(arr);

    // Output => 1	  4	 6	9	12	23	54	78	231
    SortUtils.print(sorted);

    // String Input
    String[] strings = {"c", "a", "e", "b", "d"};
    String[] sortedStrings = selectionSort.sort(strings);

    // Output => a	b	 c  d	e
    SortUtils.print(sortedStrings);
  }
}
