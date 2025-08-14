// // TC : O(1) || SC : O(3N)
// // INTUITION :: STORE THE ELEMENTS USING [CURR, MINI, MAXI]
// import java.util.*;
// class MinMaxStack {
//     private static class Element {
//         int val;
//         int currMin;
//         int currMax;
//         Element(int val, int currMin, int currMax) {
//             this.val = val;
//             this.currMin = currMin;
//             this.currMax = currMax;
//         }
//     }
//     private Stack<Element> stack;
//     public MinMaxStack() {
//         stack = new Stack<>();
//     }
//     public void push(int val) {
//         if (stack.isEmpty()) {
//             stack.push(new Element(val, val, val));
//         } else {
//             Element top = stack.peek();
//             int newMin = Math.min(val, top.currMin);
//             int newMax = Math.max(val, top.currMax);
//             stack.push(new Element(val, newMin, newMax));
//         }
//     }
//     public void pop() {
//         if (!stack.isEmpty()) {
//             stack.pop();
//         }
//     }
//     public int top() {
//         if (!stack.isEmpty()) {
//             return stack.peek().val;
//         }
//         return -1;
//     }
//     public int getMin() {
//         if (!stack.isEmpty()) {
//             return stack.peek().currMin;
//         }
//         return -1;
//     }
//     public int getMax() {
//         if (!stack.isEmpty()) {
//             return stack.peek().currMax;
//         }
//         return -1;
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         MinMaxStack st = new MinMaxStack();
//         st.push(3);
//         st.push(1);
//         st.push(5);
//         System.out.println(st.top());      // 5
//         System.out.println(st.getMin());   // 1
//         System.out.println(st.getMax());   // 5
//         st.pop();
//         System.out.println(st.top());      // 1
//         System.out.println(st.getMin());   // 1
//         System.out.println(st.getMax());   // 3
//     }
// }


// // SECOND APPROACH :: USING THREE DISTINCT STACKS TO STORE THE CURR, MIN AND MAX ELEMENTS 



// // THIRD APPROACH :: USING LINKED LIST 
// // TC : O(1) || SC : O(3N)
// class MinMaxStack {
//     private static class Node {
//         int curr, currMin, currMax;
//         Node next;
//         Node(int curr, int currMin, int currMax) {
//             this.curr = curr;
//             this.currMin = currMin;
//             this.currMax = currMax;
//             next = null;
//         }
//     }
//     Node head;
//     public MinMaxStack() {
//         this.head = null;
//     }
//     public void push(int val) {
//         if(head == null) head = new Node(val,val,val);
//         else {
//             Node newNode = new Node(val, Math.min(val, head.currMin), Math.max(val, head.currMax));
//             newNode.next = head;
//             head = newNode;
//         }
//     }
//     public void pop() {
//         if(head != null) head = head.next;
//     }
//     public int top() {
//         if(head == null) return -1;
//         return head.curr;
//     }
    
//     public int getMin() {
//         if(head == null) return -1;
//         return head.currMin;
//     }
    
//     public int getMax() {
//         if(head == null) return -1;
//         return head.currMax;
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         MinMaxStack st = new MinMaxStack();
//         st.push(3);
//         st.push(1);
//         st.push(5);
//         System.out.println(st.top());      // 5
//         System.out.println(st.getMin());   // 1
//         System.out.println(st.getMax());   // 5
//         st.pop();
//         System.out.println(st.top());      // 1
//         System.out.println(st.getMin());   // 1
//         System.out.println(st.getMax());   // 3
//     }
// }


// ******************************************************************************************************************************************************************************

// HOW DO THREADS HANDLE THE SAME RESOURCE 
// HOW DOES MUTEX LOCKING WORK IN JAVA 



// public class Main {
//     public static class CommonCounter {
//         int count;
//         CommonCounter() {
//             this.count = 0;
//         }
//         public void increment() {
//             this.count++;
//         }
//         public void increment(int val) {
//             this.count += val;
//         }
//         public void decrement() {
//             this.count--;
//         }
//         public void setZero() {
//             this.count = 0;
//         }
//     }
//     static class MyRunnable implements Runnable {
//         CommonCounter resource;
//         int n;
//         MyRunnable(CommonCounter resource, int n) {
//             this.resource = resource;
//             this.n = n;
//         }
//         @Override
//         public void run() {
//             for (int i = 0; i < n; i++) {
//                 resource.increment(); // no synchronization
//             }
//         }
//     }
//     public static void main(String[] args) throws InterruptedException {
//         CommonCounter resource = new CommonCounter();
//         Thread thread1 = new Thread(new MyRunnable(resource, 100000000));
//         Thread thread2 = new Thread(new MyRunnable(resource, 100000000));
//         thread1.start();
//         thread2.start();
//         thread1.join();
//         thread2.join();
//         System.out.println("Final count: " + resource.count);
//     }
// }





// // USING THREAD.JOIN AND SYNCHRONIZING METHODS :: SYNCHRONIZED 
// public class Main {
//     public static class CommonCounter {
//         int count;

//         CommonCounter() {
//             this.count = 0;
//         }
//         // Mutex locking using synchronized
//         public synchronized void increment() {
//             this.count++;
//         }

//         public synchronized void increment(int val) {
//             this.count += val;
//         }

//         public synchronized void decrement() {
//             this.count--;
//         }

//         public synchronized void setZero() {
//             this.count = 0;
//         }

//         public synchronized void printCount() {
//             System.out.println("Final Count: " + this.count);
//         }
//     }
//     static class MyRunnable implements Runnable {
//         CommonCounter resource;
//         int n;
//         MyRunnable(CommonCounter resource, int n) {
//             this.resource = resource;
//             this.n = n;
//         }
//         @Override
//         public void run() {
//             for (int i = 0; i < n; i++) {
//                 resource.increment();
//             }
//         }
//     }
//     public static void main(String[] args) {
//         CommonCounter resource = new CommonCounter();
//         Thread thread1 = new Thread(new MyRunnable(resource, 100000000));
//         Thread thread2 = new Thread(new MyRunnable(resource, 100000000));
//         thread1.start();
//         thread2.start();
//         try {
//             thread1.join();
//             thread2.join();
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         resource.printCount();  // Should be 3000 if synchronized properly
//     }
// }





// // USING REENTRANT_LOCK METHOD IN JAVA 
// import java.util.concurrent.locks.ReentrantLock;
// public class Main {
//     public static class CommonCounter {
//         int count;
//         public ReentrantLock lock;
//         CommonCounter() {
//             this.lock = new ReentrantLock();
//             this.count = 0;
//         }
//         public void increment() {
//             lock.lock();
//             try {
//                 this.count++;   
//             } finally {
//                 lock.unlock(); 
//             }
//         }
//         public void increment(int val) {
//             lock.lock();
//             try {
//                 this.count += val;   
//             } finally {
//                 lock.unlock(); 
//             }
//         }
//         public void decrement() {
//             while(!lock.tryLock()){
//                 //
//             }
//             this.count--;
//             lock.unlock();
//         }
//         public void setZero() {
//             lock.lock();
//             try {
//                 this.count = 0;
//             } finally {
//                 lock.unlock(); 
//             }
//         }
//     }
//     static class MyRunnable implements Runnable {
//         CommonCounter resource;
//         int n;
//         MyRunnable(CommonCounter resource, int n) {
//             this.resource = resource;
//             this.n = n;
//         }
//         @Override
//         public void run() {
//             for (int i = 0; i < n; i++) {
//                 resource.increment();
//             }
//         }
//     }
//     public static void main(String[] args) throws InterruptedException  {
//         CommonCounter resource = new CommonCounter();
//         Thread thread1 = new Thread(new MyRunnable(resource, 100000000));
//         Thread thread2 = new Thread(new MyRunnable(resource, 100000000));

//         thread1.start();
//         thread2.start();

//         thread1.join();
//         thread2.join();

//         System.out.println(resource.count);

//     }
// }






// USING ATOMIC INTEGER  
import java.util.concurrent.atomic.AtomicInteger;
public class Main {
    static class MyRunnable implements Runnable {
        AtomicInteger resource;
        int n;
        MyRunnable(AtomicInteger resource, int n) {
            this.resource = resource;
            this.n = n;
        }
        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                resource.incrementAndGet();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException  {
        AtomicInteger resource = new AtomicInteger(0);
        Thread thread1 = new Thread(new MyRunnable(resource, 100000000));
        Thread thread2 = new Thread(new MyRunnable(resource, 100000000));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(resource.get());

    }
}






// ******************************************************************************************************************************************************************************
