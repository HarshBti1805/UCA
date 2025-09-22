// MATRIOKSHA SEQUENCE (SIMILAR TO IS VALID PARENTHESIS BUT WITH MODIFICATIONS)
import java.util.Stack;
import java.util.Scanner;

public class Matrioksha {
    static boolean isValidMatrioshka(String[] arr) {
        Stack<Integer> st = new Stack<>();
        Stack<Integer> sumStack = new Stack<>();
        for (String s : arr) {
            int val = Integer.parseInt(s);
            if (val < 0) {
                st.push(val);
                sumStack.push(0);
            } else {
                if (st.isEmpty() || st.peek() != -val) return false; 
                st.pop();
                int innerSum = sumStack.pop();
                if (innerSum >= val) return false;
                if (!sumStack.isEmpty()) sumStack.push(sumStack.pop() + val);
            }
        }
        return st.isEmpty();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
	    while (t-- != 0) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] arr = line.split("\\s+");
            if (isValidMatrioshka(arr)) {
                System.out.println(":-) Matrioshka!");
            } else {
                System.out.println(":-( Try again.");
            }
        }
        sc.close();
    }
}






