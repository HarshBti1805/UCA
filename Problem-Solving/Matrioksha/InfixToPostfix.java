// INFIX TO POSTFIX 

import java.util.Scanner;
import java.util.Stack;
public class InfixToPostfix {
    public static int priority(char ch){
        if(ch == '^') return 3;
        if(ch == '*' || ch == '/') return 2;
        if(ch == '+' || ch == '-') return 1;
        return -1;
    }
    public static String infixToPostfix(String s) {
        Stack<Character>  st = new Stack<>(); // operators  
        StringBuilder res = new StringBuilder(); // result 
        for(int i = 0 ; i < s.length() ; i++){
            char ch = s.charAt(i);
            if(Character.isLetterOrDigit(ch)) {
                res.append(ch);
            }
            else if(ch == '(') st.push(ch);
            else if(ch == ')') {
                while(!st.isEmpty() && st.peek() != '(') res.append(st.pop());
                st.pop(); // pop the '('
            }
            else {
                // while(!st.isEmpty() && 
                //       (priority(ch) < priority(st.peek()) || 
                //       (priority(ch) == priority(st.peek()) && ch != '^'))) {
                //     res.append(st.pop());
                // }
                while(!st.isEmpty() && priority(ch) <= priority(st.peek())) res.append(st.pop());
                st.push(ch);
            }
        }
        while(!st.isEmpty()) res.append(st.pop());
        return res.toString();
    }
    public static void main(String[] args){
        System.out.println(infixToPostfix("(3+2)*5"));
    }
}
