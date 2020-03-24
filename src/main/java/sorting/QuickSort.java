package sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    private final static Random RAND = new Random();


    private static void swap(int[] arr, int fidx, int sidx) {
        int tmp = arr[fidx];
        arr[fidx] = arr[sidx];
        arr[sidx] = tmp;
    }

    private static void randomPivot(int[] arr, int low, int high) {
        int randPivotIdx = RAND.nextInt(high - low + 1) + low;
        swap(arr, randPivotIdx, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int firstHighIdx = low;
        randomPivot(arr, low, high);
        int pivot = arr[high];

        for (int i = low; i < high; ++i) {
            if (arr[i] <= pivot) {
                swap(arr, i, firstHighIdx);
                firstHighIdx++;
            }
        }

        swap(arr, firstHighIdx, high);
        return firstHighIdx;
    }

    /**
     * The worst-case time taken by a randomized quick-sort is O(n^2).
     * The worst case is when you want to select the minimum element and your pivot at every stage happens
     * to be the last element in the list at that stage.
     * <p>
     * However the expected time taken by a randomized quick-sort is O(nlogn).
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(arr, low, high);
            quickSort(arr, low, pivotIdx - 1); // sort left
            quickSort(arr, pivotIdx + 1, high); // sort right
        }
    }

    /**
     * The worst-case time taken by a randomized quick-select is O(n^2).
     * The worst case is when you want to select the minimum element and the pivot at every stage happens
     * to be the largest element in the list at that stage.
     * <p>
     * However the expected time taken by a randomized quick-select is O(n).
     */
    public static int quickSelect(int[] arr, int k) {
        k -= 1;
        int low = 0, high = arr.length - 1;
        int pivotIdx;
        while ((pivotIdx = partition(arr, low, high)) != k) {
            if (k < pivotIdx) // kth element in the left
                high = pivotIdx - 1;
            else // kth element in the right
                low = pivotIdx + 1;
        }

        return arr[pivotIdx];
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 1, 3, 9, 4, 6, 8, 7, 0};
        System.out.println("====== Before Sorting ======");
        System.out.println(Arrays.toString(arr));

        quickSort(arr);
        System.out.println("====== After Sorting ======");
        System.out.println(Arrays.toString(arr));


        arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        System.out.println("====== Before Sorting ======");
        System.out.println(Arrays.toString(arr));

        quickSort(arr);
        System.out.println("====== After Sorting ======");
        System.out.println(Arrays.toString(arr));

        System.out.println(quickSelect(arr, 1));
        System.out.println(quickSelect(arr, 3));
        System.out.println(quickSelect(arr, 5));

    }
}
