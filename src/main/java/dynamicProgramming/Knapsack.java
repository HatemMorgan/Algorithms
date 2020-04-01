package dynamicProgramming;

import java.util.Arrays;
import java.util.Comparator;

public class Knapsack {

    private static void printKnapsac(int[][] dp, int[] nums, int[] weights, int W){
        int w = W, n = nums.length;

        int res = dp[n][W];
        for(int i=n; i > 0 && res > 0; --i){
            if(dp[i - 1][w] == res) // this item was skipped
                continue;

            System.out.print(nums[i - 1] + " "); // element taken
            res-= nums[i - 1]; // subtract it from knapsack sum of values
            w -= weights[i - 1]; // subtract its weight from total weight of knapsack
        }
    }

    private static int knapscakRec(int[] nums, int[] weights, int W, int idx, int[][] dp){
        if(idx < 0 || W < 0)
            return 0;

        if(dp[idx][W] != -1)
            return dp[idx][W];

        if(weights[idx - 1] > W) // we cannot take this element into our knapsack
            dp[idx][W] = knapscakRec(nums, weights, W, idx - 1, dp);
        else
            dp[idx][W] = Math.max(knapscakRec(nums, weights, W, idx - 1, dp), // skip this element
                knapscakRec(nums, weights, W - weights[idx - 1], idx - 1, dp) + nums[idx - 1] // take element
                );

        return dp[idx][W];
    }


    public static int knapsack01Rec(int[] nums, int[] weights, int W){
        int n = nums.length;
        int[][] dp = new int[n + 1][W + 1];
        for(int i = 0; i <=n; ++i)
            Arrays.fill(dp[i], -1);

        for(int i=0; i <=n; ++i) // maximum sum of values of knapsack with zero weight is 0
            dp[i][0] = 0;

        for(int i=0; i <= W; ++i) // maximum sum of empty set with any weight is 0
            dp[0][i] = 0;


        int res = knapscakRec(nums, weights, W, n, dp);
        System.out.println("knapsack contains ");
        printKnapsac(dp, nums, weights, W);
        return res;
    }

    public static int knapsack01Ierative(int[] nums, int[] weights, int W){
        int n = nums.length;
        int[][] dp = new int[n + 1][W + 1];

        for(int i=1; i<=n; ++i){
            for(int w=1; w<=W; ++w){
                if(weights[i - 1] > w)
                    dp[i][w] = dp[i - 1][w]; // skip it
                else
                    dp[i][w] = Math.max(dp[i - 1][w], // skip it
                            dp[i - 1][w - weights[i - 1]] + nums[i - 1]); // take it
            }
        }

        System.out.println("knapsack contains ");
        printKnapsac(dp, nums, weights, W);
        return dp[n][W];
    }

    public static int knapsack01DPSpaceOptimized(int[] nums, int[] weights, int W){
        int n = nums.length;
        int[] dp = new int[W + 1]; // initially all elements = 0

        for(int i=0; i< n; ++i)
            for(int w = W; w >= weights[i]; --w)
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + nums[i]);

        return dp[W];
    }


    private static class Item{
        private int val, weight, ratio;
        public Item(int val, int weight){
            this.val = val;
            this.weight = weight;
            this.ratio = val / weight;
        }
    }

    /**
     * In Fractional Knapsack, we can break items for maximizing the total value of knapsack.
     * This problem in which we can break an item is also called the fractional knapsack problem.
     *
     * Greedy approach. The basic idea of the greedy approach is to calculate the ratio
     * value/weight for each item and sort the item on basis of this ratio. Then take the
     * item with the highest ratio and add them until we can’t add the next item as a whole
     * and at the end add the next item as much as we can. Which will always be the optimal
     * solution to this problem.
     */
    private static int fractionalKnapsack(int[] nums, int[] weights, int W){
        int N = nums.length;

        Comparator<Item> cmp = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o2.ratio - o1.ratio; // sort in descending order
            }
        };

        Item[] items = new Item[N];
        for(int i=0; i<N; ++i)
            items[i] = new Item(nums[i], weights[i]);

        Arrays.sort(items, cmp);

        int res = 0;
        int currW = W;
        for(int i=0; i<N; ++i){
            if(currW - items[i].weight > 0){ // item can fit completely
                currW -= items[i].weight;
                res += items[i].val;
            }else{ // take fraction of item
                double factor = ((double) currW / (double) items[i].weight );
                res += (int) (items[i].val * factor);
                break;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int nums[] = new int[]{60, 100, 120};
        int weights[] = new int[]{10, 20, 30};
        int  W = 40;
        System.out.println("\nDP Recursive: " + knapsack01Rec(nums, weights, W)); // solution is 180
        System.out.println("\nDP Iterative: " + knapsack01Ierative(nums, weights, W));
        System.out.println("\nDp optimized: " + knapsack01DPSpaceOptimized(nums, weights, W));

        System.out.println("\n===============");

        nums = new int[]{7, 8, 4};
        weights = new int[]{3, 8, 6};
        W = 10;
        System.out.println("\nDP Recursive: " + knapsack01Rec(nums, weights, W)); // solution is 180
        System.out.println("\nDP Iterative: " + knapsack01Ierative(nums, weights, W));
        System.out.println("\nDp optimized: " + knapsack01DPSpaceOptimized(nums, weights, W));

        System.out.println("\n======= Fractional knapsack ========");

        int[] wt = {10, 40, 20, 30};
        int[] val = {60, 40, 100, 120};
        int capacity = 50;

        int maxValue = fractionalKnapsack(val, wt, capacity);
        System.out.println("Maximum value we can obtain = " + maxValue); // solution is 240

    }
}
