package dynamicProgramming;

/**
 * LCS Problem Statement: Given two sequences, find the length of longest subsequence present
 * in both of them. A subsequence is a sequence that appears in the same relative order,
 * but not necessarily contiguous. For example, “abc”, “abg”, “bdf”, “aeg”, ‘”acefg”, .. etc are
 * subsequences of “abcdefg”.
 *
 * a string of length n has (2^m)-1 different possible subsequences since we do not consider the
 * subsequence with length 0. This implies that the time complexity of the brute force approach
 * will be O(n * 2^m). Note that it takes O(n) time to check if a subsequence of string s matches
 * in as a subsequence in string t.
 *
 * DP solution has time complexity O(n*m) and space complexity O(n * m) (uses substring memoization)
 */
public class LongestCommonSubsequence {

    private static void printLCS(int[][] dp, String s, String t){
        char[] lcs = new char[Math.min(s.length(), t.length())];

        int i = dp.length - 1, j = dp[0].length - 1, idx = lcs.length - 1;
        while(i > 0 && j > 0){
            if(s.charAt(i -  1) == t.charAt(j - 1)){
                lcs[idx] = s.charAt(i - 1);
                idx--;
                i--;
                j--;
            }else{
                if(dp[i - 1][j] > dp[i][j - 1]){
                    i--;
                }else{
                    j--;
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for(int k=idx+1; k<lcs.length; ++k)
            builder.append(lcs[k]);

        System.out.println("LCS of "+ s +" and "+ t +" is " + builder.toString());
    }

    public static int lcs(String s, String t){
        int m = s.length();
        int n = t.length();

        // dp[i][j] holds the max lcs found in s[0,...,i] and t[0,..,j]
        int[][] dp = new int[m + 1][n + 1];

        for(int i=1; i<=m; ++i){
            for(int j=1; j<=n; ++j){
                if(s.charAt(i - 1) == t.charAt(j - 1)) // same last character
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        printLCS(dp, s, t);
        return dp[m][n];
    }

    public static void main(String[] args) {
        String s = "AGGTAB";
        String t = "GXTXAYB";

        System.out.println("Length of LCS is" + " " + lcs(s, t) );


        System.out.println("\n ======================== ");
        s = "abcdefge";
        t = "adsaessdfdsge";

        System.out.println("Length of LCS is" + " " + lcs(s, t) );
    }
}
