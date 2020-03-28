package dynamicProgramming;

/**
 * The Longest Increasing Subsequence (LIS) problem is to find the length of the longest
 * subsequence of a given sequence such that all elements of the subsequence are sorted in
 * increasing order. For example, the length of LIS for {10, 22, 9, 33, 21, 50, 41, 60, 80}
 * is 6 and LIS is {10, 22, 33, 50, 60, 80}.
 *
 * The dp solution has time complexity O(n^2) and space complexity O(n)
 */
public class LongestIncreasingSubsequence {


    private static void printLIS(int[] nums, int[] predecessor, int k){
        if(k == -1)
            return;

        printLIS(nums, predecessor, predecessor[k]);
        System.out.print(nums[k] + " ");
    }

    public static int lis (int[] nums){
        int[] dp = new int[nums.length];
        int[] predecessor = new int[nums.length];

        predecessor[0] = -1;
        dp[0] = 1;

        int k = 0; // index of last element in LIS
        for(int i=1; i<nums.length; ++i){
            int maxIdx = -1;
            int max = 0;
            for(int j=0; j<i; ++j){
                if(nums[j] < nums[i] && dp[j] > max){
                    max = dp[j];
                    maxIdx = j;
                }
            }

            predecessor[i] = maxIdx;
            dp[i] = max + 1;
            k = (dp[i] > dp[k])? i: k;
        }

        System.out.print("Longest increasing subsequence is : ");
        printLIS(nums, predecessor, k);
        System.out.println("");
        return dp[k];
    }

    public static void main(String args[]) {
        int arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 };
        System.out.println("Length of lis is " + lis(arr) + "\n");

        System.out.println("\n =========================== ");

        arr = new int[]{3, 10, 2, 1, 20};
        System.out.println("Length of lis is " + lis(arr) + "\n");

        System.out.println("\n =========================== ");

        arr = new int[] {3, 2};
        System.out.println("Length of lis is " + lis(arr) + "\n");

        System.out.println("\n =========================== ");

        arr = new int[] {50, 3, 10, 7, 40, 80};
        System.out.println("Length of lis is " + lis(arr) + "\n");

    }
}
