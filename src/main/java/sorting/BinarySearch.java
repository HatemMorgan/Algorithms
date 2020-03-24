package sorting;

public class BinarySearch {

    private static int findLastOccurrence(int[] arr, int x){
        int low = 0, high = arr.length - 1;
        int res =  -1;
        while(low <= high){
            int mid = (high - low) / 2 + low;

            if(arr[mid] == x)
                res = mid;

            if(arr[mid] <= x)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return res;
    }

    private static int findFirstOccurrence(int[] arr, int x){
        int low = 0, high = arr.length - 1;
        int res = -1;

        while(low <= high){
            int mid = (high - low) / 2 + low;

            if(arr[mid] == x)
                res = mid;

            if(arr[mid] >= x)
                high = mid - 1;
            else
                low = mid + 1;
        }

        return res;
    }

    private static int findFirstSmallerNum(int[] arr, int x){
        int low = 0, high = arr.length - 1;
        int res = -1;

        while (low <= high){
            int mid = (high - low) / 2 + low;

            if(arr[mid] < x)
                res = mid;

            if(arr[mid] >= x)
                high = mid - 1;
            else
                low = mid + 1;
        }

        return res;
    }

    private static int findFirstLargerNum(int[] arr, int x){
        int low = 0, high = arr.length - 1;
        int res = -1;

        while(low <= high){
            int mid = (high - low) / 2 + low;

            if(arr[mid] > x)
                res = mid;

            if(arr[mid] <= x)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return res;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 2, 2, 2, 2, 5};
        System.out.println(findLastOccurrence(arr, 2));
        System.out.println(findFirstOccurrence(arr, 2));
        System.out.println(findFirstSmallerNum(arr, 2));
        System.out.println(findFirstLargerNum(arr, 1));
    }
}
