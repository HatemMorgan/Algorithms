package sorting;

import dataStructures.MinHeap;

import java.util.Arrays;

/**
 * HeapSort O(nlogn) time complexity and O(1) space complexity (in place sorting)
 */
public class HeapSort {

    private static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void sort(int[] arr) {
        MinHeap heap = new MinHeap(arr);

        // building heap O(nlogn) time complexity and O(1) space complexity
//        for (int x : arr)
//            heap.insert(x); // O(logn) operation

        // sorting by finding minimum as in selection sort: O(nlogn) time complexity and O(1) space complexity
        for (int i = 0; i < arr.length; ++i)
            heap.getMin(); // O(logn) operation

        for(int i=0; i<arr.length/2; ++i) // O(n) time complexity and O(1) space complexity
            swap(arr, i, arr.length - 1 - i);

    }

    public static void main(String[] args){
        int[] arr = new int[] {1,5,2,1, 944, 24, 12, 221, 0, 20, 83, 7, 9};
        HeapSort.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}



