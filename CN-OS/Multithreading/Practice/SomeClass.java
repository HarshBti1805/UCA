class Demo extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + " running: " + i);
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
    }
}

public class SomeClass {
    public static void main(String[] args) throws InterruptedException {
        Demo t1 = new Demo();
        Demo t2 = new Demo();
        t1.start();
        t1.join(); // main waits until t1 finishes
        t2.start();
    }
}

