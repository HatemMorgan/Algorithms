package dynamicProgramming;

/**
 * Problem: Integer Partition without Rearrangement
 * Input: An arrangement S of nonnegative numbers {s1, . . . , sn} and an integer k.
 * Output: Partition S into k or fewer ranges, to minimize the maximum sum over all
 * the ranges, without reordering any of the numbers.
 *
 * Time complexity O(k* n^2) and space complexity O(n * k)
 */
public class Partitioning {

    private static void print(int[] nums, int s, int e){
        StringBuilder builder = new StringBuilder();

        // i -1 because we using length + 1 in our solution construction
        for(int i = s; i<=e; ++i)
            builder.append(nums[i - 1]).append(" ");

        System.out.println(builder.toString());
    }

    private static void reconstructPartitions(int[] nums, int[][] parent, int n, int k){
        if(k == 1){
            print(nums, 1, n);
            return;
        }

        reconstructPartitions(nums, parent, parent[n][k], k - 1);
        print(nums, parent[n][k] + 1, n);
    }

    public static void partition(int[] nums, int k){
        int n = nums.length;
        // dp[i][k] hold the minimum partition cost (largest sum of elements in a partition)
        // of all possible partitions of nums[0,...,i] into j partitions
        int[][] dp = new int[n + 1][k + 1];

        // parent[i][j] hold a pointer previous state. Used to construct path to solution
        int[][] parent = new int[n + 1][k + 1];
        int[] prefixSum = new int[n + 1];

        for(int i=0; i<n; ++i)
            prefixSum[i + 1] = prefixSum[i] + nums[i];

        for(int i=1; i<=n; ++i) // single partition remaining
            dp[i][1] = prefixSum[i];

        for(int i=1; i<=k; ++i) // one element remaining
            dp[1][i] = nums[0];

        for(int i=2; i<=n; ++i){
            for(int j=2; j<=k; ++j){
                dp[i][j] = Integer.MAX_VALUE;

                for(int l=1; l<i; ++l){
                    // cost = largest sum of elements in j partitions.
                    int cost = Math.max(dp[l][j - 1], prefixSum[i] - prefixSum[l]);

                    if(dp[i][j] > cost){
                        dp[i][j] = cost;
                        parent[i][j] = l; // update divider position
                    }
                }
            }
        }

        reconstructPartitions(nums, parent, n, k);
    }

    public static void main (String[] args) {

        int arr[] = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println("Partitions are : ");
        partition(arr, 3);

        System.out.println("\n =========================== ");

        arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Partitions are : ");
        partition(arr, 3);
    }
}
