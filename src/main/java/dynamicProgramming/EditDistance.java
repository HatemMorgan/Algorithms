package dynamicProgramming;

import java.util.Arrays;

/**
 * Edit Distance problem is: given two strings str1 and str2 and below operations that can performed on str1.
 *      1- Insert
 *      2- Remove
 *      3- Replace
 *
 * Find minimum number of edits (operations) required to convert str1 into str2.
 *
 * DP has time complexity O(n * m) and space complexity O(n * m) (uses substring memoization)
 */
public class EditDistance {
    static final int INSERT = 0, DELETE = 1, REPLACE = 2, MATCH = 3;

    private static void printOperations(int[][] parent, int i, int j, StringBuilder builder){
        if(i < 0 || j < 0 || parent[i][j] == -1)
            return;

        switch (parent[i][j]){
            case MATCH: printOperations(parent, i - 1, j - 1, builder);
                        builder.append('M');
                        return;

            case REPLACE:   printOperations(parent, i - 1, j - 1, builder);
                            builder.append('S');
                            return;

            case INSERT:     printOperations(parent, i, j - 1, builder);
                             builder.append('I');
                             return;

            case DELETE:    printOperations(parent, i - 1, j,  builder);
                            builder.append('D');
        }
    }

    public static int editDistance(String s, String t, int[] cost){
        int m = s.length(), n = t.length();
        // memo[i][j] determines the minimum edit cost to match [s0 ... si] and [t0 ... tj]
        // this can be compressed to 1D array of size n + 1 as we require only one row the upper row.
        int[][] memo = new int[m + 1][n + 1];
        // parent[i][j] hold a pointer previous state. Used to construct path to solution
        int[][] parent = new int[m + 1][n + 1];

        for(int i=0; i<=m; ++i)
            Arrays.fill(parent[i], -1);

        // second string is empty thus we need to delete characters from first string to match
        for(int i=1; i<= m; ++i) {
            memo[i][0] = memo[i - 1][0] + cost[DELETE];
            parent[i][0] = DELETE;
        }
        // first string is empty thus we need to insert characters from second string to it to match
        for(int j=1; j<= n; ++j){
            memo[0][j] = memo[0][j - 1] + cost[INSERT];
            parent[0][j] = INSERT;
        }

        for(int i=1; i<= m; ++i){
            for(int j=1; j<=n; ++j){

                if(s.charAt(i - 1) == t.charAt(j - 1)){
                    memo[i][j] = memo[i - 1][j - 1];
                    parent[i][j] = MATCH;

                }else {
                    int[] opt = new int[3];

                    // insert jth character of t to s (in the i+1 index).
                    opt[INSERT] = (j - 1 >= 0)? memo[i][j - 1] + cost[INSERT] : cost[INSERT];
                    // delete ith character of s
                    opt[DELETE] = (i - 1 >= 0)? memo[i - 1][j] + cost[DELETE] : cost[DELETE];
                    // Replace jth character in t by ith character in s
                    opt[REPLACE] = (i -1 >=0 && j-1>=0)? memo[i - 1][j - 1] + cost[REPLACE]: cost[REPLACE];

                    int minCost = Integer.MAX_VALUE;
                    for(int idx=INSERT; idx <= REPLACE; ++idx){
                        if(minCost > opt[idx]){
                            minCost = opt[idx];
                            parent[i][j] = idx;
                        }
                    }

                    memo[i][j] = minCost;
                }
            }
        }

        StringBuilder path = new StringBuilder();
        printOperations(parent, m, n, path);
        System.out.println("Edit path: " + path.toString());
        return memo[m][n];
    }


    public static void main(String[] args) {
        String str1 = "thou shalt not";
        String str2 = "you should not";
        int[] cost = {1,1,1};
        int dist = editDistance(str1, str2, cost);
        System.out.println("Minimum edit distance to match two string is " + dist);

        System.out.println("\n======================");

        str1 = "sunday";
        str2 = "saturday";
        dist = editDistance(str1, str2, cost);
        System.out.println("Minimum edit distance to match two string is " + dist);

        System.out.println("\n======================");

        str1 = "Hatem";
        str2 = "ate";
        dist = editDistance(str1, str2, cost);
        System.out.println("Minimum edit distance to match two string is " + dist);
    }
}
