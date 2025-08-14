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
                    // In FCFS, we don't pause after each time unit
                    // The task runs continuously until completion
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

class FCFSScheduler implements Scheduler {
    private Map<Task, Thread> taskThreadMap = new HashMap<>();

    @Override
    public void execute(List<Task> taskList) throws InterruptedException {
        // Sort tasks by arrival time (FCFS principle)
        taskList.sort(Comparator.comparingInt(Task::getArrivalTime)
                .thenComparingInt(Task::getTaskId)); // tie-breaker by task ID

        // Create threads for all tasks
        for (Task t : taskList) {
            Thread thread = new Thread(t);
            thread.start();
            taskThreadMap.put(t, thread);
        }

        int currentTime = 0;
        int completedTasks = 0;
        int index = 0;

        // Ready queue ordered by arrival time (FCFS)
        Queue<Task> readyQueue = new LinkedList<>();

        while (completedTasks < taskList.size()) {
            // Add newly arrived tasks to ready queue
            while (index < taskList.size() && taskList.get(index).getArrivalTime() <= currentTime) {
                readyQueue.offer(taskList.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                // No tasks ready, advance time
                currentTime++;
                Thread.sleep(100);
                continue;
            }

            // Get the first task in queue (FCFS)
            Task currentTask = readyQueue.poll();
            
            // Resume the task and let it run to completion (non-preemptive)
            currentTask.resume();
            
            // Wait for the task to complete its full burst time
            Thread taskThread = taskThreadMap.get(currentTask);
            taskThread.join(); // Wait for task to finish completely
            
            // Update current time by the burst time of completed task
            currentTime += currentTask.getBurstTime();
            
            // Calculate completion times
            currentTask.calculateTimes(currentTime);
            completedTasks++;
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

public class FCFS {
    public static void main(String[] args) {
        List<Task> taskList = new ArrayList<>();
        // Task with different arrival times and burst times
        taskList.add(new Task(1, 5, 2, 1));   // Arrives at 5, completes at 7
        taskList.add(new Task(2, 0, 8, 2));   // Arrives at 0, completes at 8  
        taskList.add(new Task(3, 2, 1, 3));   // Arrives at 2, completes at 9
        taskList.add(new Task(4, 10, 3, 1));  // Arrives at 10, completes at 13
        taskList.add(new Task(5, 1, 4, 2));   // Arrives at 1, completes at 12

        Scheduler scheduler = new FCFSScheduler();
        try {
            scheduler.execute(taskList);
        } catch (InterruptedException e) {
            System.out.println("Scheduler interrupted: " + e.getMessage());
        }
    }
}