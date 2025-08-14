// BASIC EXAMPLE OF MULTITHREADING IN JAVA
// This example demonstrates how to create a thread by extending the Thread class.

// class MyThread extends Thread {
//     public void run() {
//         for (int i = 1; i <= 5; i++) {
//             System.out.println("From MyThread: " + i);
//             try {
//                 Thread.sleep(500); // sleep for 500 milliseconds
//             } catch (InterruptedException e) {
//                 System.out.println(e);
//             }
//         }
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         MyThread t1 = new MyThread();
//         t1.start(); // start the thread
//         for (int i = 1; i <= 5; i++) {
//             System.out.println("From Main Thread: " + i);
//             try {
//                 Thread.sleep(500);
//             } catch (InterruptedException e) {
//                 System.out.println(e);
//             }
//         }
//     }
// }


// // THREAD CLASS 
// // MAIN THREAD :: The main thread is the thread that runs the main method of a Java application.
// public class Main {
//     public static void main(String[] args) {
//         System.out.println(Thread.currentThread().getName()); // prints the name of the main thread
//     }
// }



// // LAMBDA EXPRESSION TO CREATE A THREAD AND HOW TO START A THREAD 
// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             System.out.println(Thread.currentThread().getName() + " is running");
//         });
//         System.out.println(Thread.currentThread().getName() + " is running");
//     }
// }



// // LAMBDA EXPRESSION TO CREATE A THREAD AND HOW TO START A THREAD 
// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             System.out.println(Thread.currentThread().getName());
//         });
//         t1.setName("MyThread"); // setting the name of the thread
//         t1.start(); // start the thread
//         System.out.println(Thread.currentThread().getName());
//     }
// }




// // THREAD PRIORITY
// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             System.out.println(Thread.currentThread().getName());
//         });
//         Thread[] t = new Thread[1000];
//         t1.setPriority(10);
//         t1.setName("MyThread"); // setting the name of the thread
//         t1.start(); // start the thread
//         System.out.println(Thread.currentThread().getName());
//         t[0] = new Thread();
//         t[0].setPriority(1); // setting the priority of the thread
//     }
// }





// // T1.RUN() -> // This will not start a new thread, it will just execute the run method in the current thread.
// // T1.START() -> // This will start a new thread and execute the run method in that thread.
// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             System.out.println(Thread.currentThread().getName());
//         });
//         t1.run();
//         System.out.println(Thread.currentThread().getName());
//     }
// }




// public class Main {
//     public static void main(String[] args) {
//         // Create an array of threads
//         Thread[] threads = new Thread[5];

//         // Initialize each thread with a name and set priority
//         for (int i = 0; i < threads.length; i++) {
//             threads[i] = new Thread("Thread-" + i);
//             threads[i].setPriority( i + 6); // priority from 6 to 10
//         }
//         for (Thread t : threads) {
//             System.out.println(t.getName() + " with priority " + t.getPriority());
//             t.start();
//         }
//     }
// }



// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             int s = 0;
//             for(int i = 0 ; i < 1000; i++) {
//                 s += i;
//             }
//             System.out.println(Thread.currentThread().getName() + " sum: " + s);
//         });
//         t1.run();
//         System.out.println(Thread.currentThread().getName());
//     }
// }




// public class Main {
//     public static void main(String[] args) {
//         Thread t1 = new Thread(() -> {
//             int s = 0;
//             for(int i = 0 ; i < 1000; i++) {
//                 s += i;
//             }
//             System.out.println(Thread.currentThread().getName() + " sum: " + s);
//         });
//         t1.start();
//         System.out.println(Thread.currentThread().getName());
//     }
// }





// class CalculateSumThread extends Thread {
//     private int sumUpto;
//     public CalculateSumThread(int sumUpto) {
//         this.sumUpto = sumUpto;
//     }
//     @Override
//     public void run(){
//         int s = 0;
//         for(int i = 0 ; i <= sumUpto ; i++) {
//             s += i;
//         }
        
//         try {
//             Thread.sleep(1000); // sleep for 1 second
//         } catch (InterruptedException e) {
//             System.out.println(e);
//         }

//         System.out.println(Thread.currentThread().getName() + " sum: " + s);
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         CalculateSumThread t1 = new CalculateSumThread(10);
//         CalculateSumThread t2  = new CalculateSumThread(100);
//         CalculateSumThread t3 = new CalculateSumThread(1000);
//         // t1.run();
//         // t2.run();
//         // t3.run();
//         t1.run();
//         t1.run();
//         t1.run();
//         t1.start();
//         t1.start();
//         // t2.start();
//         // t3.start();
//     }
// }





// RUNNABLE INTERFACE : ANOTHER WAY TO CREATE A THREAD IS BY IMPLEMENTING THE RUNNABLE INTERFACE
class CalculateSumRunnable implements Runnable {
    private int sumUpto;
    public CalculateSumRunnable(int sumUpto) {
        this.sumUpto = sumUpto;
    }
    @Override
    public void run() {
        int s = 0;
        for (int i = 0; i <= sumUpto; i++) {
            s += i;
        }
        System.out.println(Thread.currentThread().getName() + " sum: " + s);
    }
}
public class Main {
    public static void main(String[] args) {
        CalculateSumRunnable runnable1 = new CalculateSumRunnable(10);
        CalculateSumRunnable runnable2 = new CalculateSumRunnable(100);
        CalculateSumRunnable runnable3 = new CalculateSumRunnable(1000);

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);

        t1.start();
        t2.start();
        t3.start();
    }
}