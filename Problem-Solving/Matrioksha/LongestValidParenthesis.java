// // LONGEST VALID PARENTHESIS 
public class LongestValidParenthesis {
    public int longestValidParentheses(String s) {
        int left = 0, right = 0, maxLen = 0;
        for(int i = 0 ; i < s.length() ; i++){
            char ch = s.charAt(i);
            if(ch == '(') left++;
            else right++;
            if(left == right) maxLen = Math.max(maxLen, left * 2);
            else if(right > left) {
                left = 0;
                right = 0;
            }
        }
        left = 0; 
        right = 0;
        for(int i = s.length() - 1 ; i >= 0; i--){
            char ch = s.charAt(i);
            if(ch == '(') left++;
            else right++;
            if(left == right) maxLen = Math.max(maxLen, left * 2);
            else if(left > right) {
                left = 0;
                right = 0;
            }
        }
        return maxLen;    
    }
}


// import java.util.*;
// public class LongestValidParenthesis {
//     public int longestValidParentheses(String s) {
//         Stack<Integer> stack = new Stack<>();
//         stack.push(-1);  // Base index
//         int maxLen = 0;
//         for (int i = 0; i < s.length(); i++) {
//             char ch = s.charAt(i);
//             if (ch == '(') stack.push(i);
//             else {
//                 stack.pop();
//                 if (stack.isEmpty()) 
//                     stack.push(i);  // New base index
//                 else 
//                     maxLen = Math.max(maxLen, i - stack.peek());
//             }
//         }
//         return maxLen;
//     }
// }
