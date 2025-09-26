import java.util.*;
public class GenerateParenthesis {
    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     *
     * Constraints:
     * 1. 1 <= n <= 8
     * 2. The solution set must not contain duplicate combinations.
     *
     * Example:
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     *
     * @param n - Number of pairs of parentheses.
     * @returns List<String> - A list of all combinations of well-formed parentheses.
     */
    public static void dfs(int index, int n, int open, int close, StringBuilder temp, List<String> result){
        if(index == 2 * n){
            result.add(temp.toString());
            return ;
        }
        if(open < n){
            temp.append("(");
            dfs(index + 1, n, open + 1, close, temp , result);
            temp.deleteCharAt(temp.length() - 1);
        }
        if(close < open){
            temp.append(")");
            dfs(index + 1, n , open, close + 1, temp, result);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        dfs(0, n, 0, 0, new StringBuilder(), result);
        return result;
    }
    /**
     * Main method for testing the GenerateParenthesis class.
     */
    public static void main(String[] args) {
        // GenerateParenthesis gp = new GenerateParenthesis();
        int n = 3;
        // List<String> result = gp.generateParenthesis(n);
        List<String> result = generateParenthesis(n);
        List<String> expected1 = Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()");
        List<String> expected2 = Arrays.asList("((()))", "(()())", "(())()", "()(())"); // removed one


        // for(String s : result) System.out.println(s);
        assert result.size() == expected1.size() && result.containsAll(expected1) : "Test case failed";
        // assert result.size() == expected2.size() && result.containsAll(expected2) : "Test case failed";
    }
}
