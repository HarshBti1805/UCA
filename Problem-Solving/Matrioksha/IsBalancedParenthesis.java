import java.util.Stack;
import java.util.Scanner;
public class IsBalancedParenthesis {
    public static boolean isParenthesisBalanced(String s){
        Stack<Character> st = new Stack<>();
        for(char ch : s.toCharArray()){
            if(ch == '[' || ch == '(' || ch == '{') st.push(ch);
            else {
                if(st.isEmpty()) return false;
                char top = st.peek();
                if(ch == ']' &&  top != '[') return false;
                if(ch == ')' &&  top != '(') return false;
                if(ch == '}' &&  top != '{') return false;
                st.pop();
            }
        }
        return st.isEmpty() ? true : false;
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        long t = scanner.nextInt();
        scanner.nextLine();
        while(t-- != 0){
            String s = scanner.nextLine();
            System.out.println(isParenthesisBalanced(s));
        }
        scanner.close();
    }    
}

