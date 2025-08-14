// // MAX-HEAP 
// import java.util.PriorityQueue;
// class KthLargest {
//     public int findKthLargest(int[] arr, int k) {
//         PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
//         for(int i : arr) pq.offer(i);
//         k--;
//         while(k-- != 0){
//             pq.poll();
//         }
//         return pq.peek();
//     }
//     public static void main(String[] args){
//         int[] arr = {3,2,1,5,6,4};
//         int k = 2;
//         System.out.println(findKthLargest(arr,k));
//     }
// }


// MIN-HEAP 
import java.util.PriorityQueue;
public class KthLargest {
    public static int findKthLargest(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for(int i : arr){
            pq.offer(i);

            if(pq.size() > k) pq.poll();
        }
        return pq.peek();
    }
    public static void main(String[] args){
        int[] arr = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(findKthLargest(arr,k));
    }
}
