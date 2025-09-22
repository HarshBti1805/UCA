
import java.util.Stack;
public class LargestHistogram {
    public static int[] findPreviousSmaller(int[] arr){
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int[] pse = new int[n];
        for(int i = 0 ; i < n; i++){    
            while(!st.isEmpty() && arr[st.peek()] >= arr[i])
                st.pop();
            pse[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }
        return pse;
    }
    public static int[] findNextSmaller(int[] arr){
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int[] nse = new int[n];
        for(int i = n - 1 ; i >= 0 ; i--){
            while(!st.isEmpty() && arr[st.peek()] >= arr[i]) 
                st.pop();
            nse[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }
        return nse;
    }
    public int largestRectangleArea(int[] arr) {
        int n = arr.length;
        int[] nse = findNextSmaller(arr);
        int[] pse = findPreviousSmaller(arr);
        int res = 0;
        for(int i = 0 ; i < n ; i++){
            int height = arr[i];
            int width = nse[i] - pse[i] - 1;
            int area = height * width;
            res = Math.max(area, res);
        }
        return res;
    }
    public static void main(String[] args){
    }
    
}




// LARGEST RECTANGLE IN A HISTOGRAM 



