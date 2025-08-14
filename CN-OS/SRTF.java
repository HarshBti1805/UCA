import java.util.*;
class Task implements Runnable {
    private int taskId;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int priority;
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;
    private final Object lock = new Object();
    private boolean isPaused = true;

    public Task(int taskId, int arrivalTime, int burstTime, int priority) {
        this.taskId = taskId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

    public void resume() {
        synchronized (lock) {
            isPaused = false;
            lock.notify();
        }
    }

    public void pause() {
        synchronized (lock) {
            isPaused = true;
        }
    }

    @Override
    public void run() {
        try {
            while (remainingTime > 0) {
                synchronized (lock) {
                    while (isPaused) lock.wait();
                    remainingTime--;
                    Thread.sleep(100);
                    isPaused = true;   
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isCompleted() { return remainingTime == 0; }
    public int getRemainingTime() { return remainingTime; }
    public int getArrivalTime() { return arrivalTime; }
    public int getTaskId() { return taskId; }
    public int getBurstTime() { return burstTime; }
    public int getPriority() { return priority; }
    public int getCompletionTime() { return completionTime; }

    public void calculateTimes(int currentTime) {
        this.completionTime = currentTime;
        this.turnaroundTime = currentTime - arrivalTime;
        this.waitingTime = turnaroundTime - burstTime;
    }

    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
}

interface Scheduler {
    void execute(List<Task> taskList) throws InterruptedException;
}

class SRTFScheduler implements Scheduler {
    private Map<Task, Thread> taskThreadMap = new HashMap<>();

    @Override
    public void execute(List<Task> taskList) throws InterruptedException {
        taskList.sort(Comparator.comparingInt(Task::getArrivalTime));

        for (Task t : taskList) {
            Thread thread = new Thread(t);
            thread.start();
            taskThreadMap.put(t, thread);
        }

        int currentTime = 0;
        int completedTasks = 0;
        int index = 0;

        PriorityQueue<Task> readyQueue = new PriorityQueue<>(
                Comparator.comparingInt(Task::getRemainingTime)    
                .thenComparingInt(Task::getPriority)        // 2. then by priority
                .thenComparingInt(Task::getArrivalTime)     // 3. then by arrival time
        );

        while (completedTasks < taskList.size()) {
            // ADDING NEW TASKS TO THE READY_QUEUE 
            while (index < taskList.size() && taskList.get(index).getArrivalTime() <= currentTime) {
                readyQueue.offer(taskList.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                Thread.sleep(100);
                continue;
            }
            Task currentTask = readyQueue.poll();
            currentTask.resume();
            Thread.sleep(100);
            currentTime++;

            if (currentTask.isCompleted()) {
                currentTask.calculateTimes(currentTime);
                completedTasks++;
            } else {
                // IF NOT COMPLETED, PUT IT BACK
                readyQueue.offer(currentTask);
            }
        }

        for (Thread t : taskThreadMap.values()) {
            t.join();
        }

        printStatistics(taskList);
    }

    private void printStatistics(List<Task> taskList) {
        // Sort tasks by completion time (first to finish to last to finish)
        List<Task> sortedByCompletion = new ArrayList<>(taskList);
        sortedByCompletion.sort(Comparator.comparingInt(Task::getCompletionTime));
        
        int totalWT = 0, totalTAT = 0;

        System.out.printf("\n%-6s %-8s %-8s %-9s %-14s %-17s %-15s\n",
                "Task", "Arrival", "Burst", "Priority", "Waiting Time", "Turnaround Time", "Completion Time");
        System.out.println("--------------------------------------------------------------------------------");

        for (Task task : sortedByCompletion) {
            System.out.printf("%-6d %-8d %-8d %-9d %-14d %-17d %-15d\n",
                    task.getTaskId(), task.getArrivalTime(), task.getBurstTime(), task.getPriority(),
                    task.getWaitingTime(), task.getTurnaroundTime(), task.getCompletionTime());
            totalWT += task.getWaitingTime();
            totalTAT += task.getTurnaroundTime();
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", (double) totalWT / taskList.size());
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTAT / taskList.size());
    }
}

public class SRTF {
    public static void main(String[] args) {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, 0, 5, 2));
        taskList.add(new Task(2, 1, 3, 1));
        taskList.add(new Task(3, 2, 8, 3));
        taskList.add(new Task(4, 3, 6, 2));
        taskList.add(new Task(5, 4, 4, 1));

        Scheduler sc = new SRTFScheduler();
        try {
            sc.execute(taskList);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}