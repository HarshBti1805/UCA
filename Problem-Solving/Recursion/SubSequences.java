public class SubSequences {
    /**
     * If S and T are strings, we say that S is a subsequence of T if all
     * letters of S appear in T in the same order (not necessarily consecutively).
     * For example, "ace" is a subsequence of "abcde" while "aec" is not.
     * Given two strings S and T, checks whether S is a subsequence of T.
     *
     * Constraints:
     * 1. Your solution should be recursive and not use any loops.
     * 2. 0 <= len(S), len(T) <= 1000
     * 3. Strings are case-sensitive and contain only English letters.
     *
     * @param S - The subsequence string.
     * @param T - The target string.
     * @returns boolean - True if S is a subsequence of T, otherwise false.
     **/
    public boolean dfs(int i, int j, int n, int m, String s, String t){
        if(i == n) return true;
        if(j == m && i != n) return false;
        if(s.charAt(i) == t.charAt(j)) {
            return dfs(i + 1, j + 1, n, m ,s , t); // since match move forward in both
        }
        return dfs(i, j + 1, n, m , s, t); // skip one character in t
    }
     public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        if(n > m) return false;
        return dfs(0, 0, n, m, s, t);
     }

    /**
     * Main method for testing the SubSequences class.
     */
    public static void main(String[] args) {
        SubSequences subsequences = new SubSequences();
        assert subsequences.isSubsequence("ace", "abcde") == true : "Test case 1 failed";
        assert subsequences.isSubsequence("aec", "abcde") == false : "Test case 2 failed";
        assert subsequences.isSubsequence("", "abcde") == true : "Test case 3 failed";
        assert subsequences.isSubsequence("abc", "") == false : "Test case 4 failed";
        assert subsequences.isSubsequence("abc", "ahbgdc") == true : "Test case 5 failed";
        assert subsequences.isSubsequence("axc", "ahbgdc") == false : "Test case 6 failed";
    }
}
