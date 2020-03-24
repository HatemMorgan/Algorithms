package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class InsertionSort {

    private static void swap (ArrayList<Comparable> arr, int i , int j){
        Comparable tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    public static void insertionSort(ArrayList<Comparable> arr){
        for (int i = 0; i < arr.size(); ++i){
            int j = i;

            while(j > 0 && arr.get(j).compareTo(arr.get(j - 1)) < 0){
                swap(arr, j,  j-1);
                j--;
            }
        }
    }

    public static void main(String[] args){
        String[] s = new String[]{"3", "30", "34", "5", "9"};

        Comparator x = new Comparator<String>() {
            public int compare(String s1, String s2) {
                int i = 0;
                while (s1.length() > s2.length() && s1.length() - i != s2.length())
                    ++i;

                int j = 0;
                while (s2.length() > s1.length() && s1.length() != s2.length() - j)
                    ++j;

                while (i < s1.length() - 1 && s1.charAt(i) == s2.charAt(j)) {
                    ++i;
                    ++j;
                }

                System.out.println(s1 + " " + s1.charAt(i) + " ___ " + s2 + " " + s2.charAt(j) + "===> " + (s1.charAt(i) - s2.charAt(j)));
                return s1.charAt(i) - s2.charAt(j);
            }
        };

        Arrays.sort(s, x);
//        System.out.println(x.compare("9", "34"));
        System.out.println(Arrays.toString(s));
//        int[] a = new int[]{5,1,7,8,313,77,2,0, 2};
//        ArrayList<Comparable> arr = new ArrayList<Comparable>();
//        for(int x : a)
//            arr.add(x);
//
//        insertionSort(arr);
//        System.out.println(arr);
//
//        String[] s = new String[]{"x", "t", "i", "a", "d", "k", "m", "h"};
//        arr = new ArrayList<Comparable>();
//        for(String x: s)
//            arr.add(x);
//
//        insertionSort(arr);
//        System.out.println(arr);
    }
}
