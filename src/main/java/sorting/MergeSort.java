package sorting;

import java.util.Arrays;

public class MergeSort {

    private static void merge(int[] arr, int s, int m, int e) {
        int[] temp = new int[e - s + 1];
        int i = s, j = m + 1, k = 0;

        while (i <= m && j <= e) {
            if (arr[i] < arr[j])
                temp[k] = arr[i++];
            else
                temp[k] = arr[j++];

            k++;
        }

        while (i <= m)
            temp[k++] = arr[i++];

        while (j <= e)
            temp[k++] = arr[j++];

        for (i = s; i <= e; ++i)
            arr[i] = temp[i - s];
    }


    /**
     * the recursion goes log(n) levels deep, and a linear amount of work is done per level in merging,
     * mergesort takes O(n log n) time in the worst case.
     *
     * Mergesort is a great algorithm for sorting linked lists, because it does not rely on
     * random access to elements as does heapsort or quicksort. Its primary disadvantage
     * is the need for an auxilliary buffer when sorting arrays.  It is easy to merge two
     * sorted linked lists without using any extra space, by just rearranging the pointers.
     */
    private static void mergeSort(int[] arr, int s, int e) {
        if (s < e) {
            int mid = (e - s) / 2 + s;
            mergeSort(arr, s, mid);
            mergeSort(arr, mid + 1, e);
            merge(arr, s, mid, e);
        }
    }


    public static void sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 2, 1, 944, 24, 12, 221, 0, 20, 83, 7, 9};
        MergeSort.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
