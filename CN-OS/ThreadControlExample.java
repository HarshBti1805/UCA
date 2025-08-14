import java.util.*;

class CounterThread extends Thread {
    private int count = 1;
    private boolean running = true;
    private boolean paused = true; // initially paused
    private final Object lock = new Object();

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (lock) {
                    while (paused && running) {
                        lock.wait(); // wait until notified
                    }
                    if (!running) break; // check after waking up
                }

                System.out.println(count++);
                Thread.sleep(500); // slow down counting for visibility
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void startCounting() {
        synchronized (lock) {
            paused = false;
            lock.notify();
        }
    }

    public void stopCounting() {
        synchronized (lock) {
            paused = true;
        }
    }

    public void exitCounting() {
        synchronized (lock) {
            running = false;
            paused = false; // in case it is waiting
            lock.notify();
        }
    }
}

public class ThreadControlExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CounterThread counter = new CounterThread();
        counter.start();

        System.out.println("Commands: start | stop | exit");

        while (true) {
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "start":
                    counter.startCounting();
                    break;
                case "stop":
                    counter.stopCounting();
                    break;
                case "exit":
                    counter.exitCounting();
                    return; // exit main loop
                default:
                    System.out.println("Invalid command. Use: start | stop | exit");
            }
        }
    }
}
