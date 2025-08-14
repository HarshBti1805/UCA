import java.util.*;
class Task implements Runnable  {
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
    
    public Task(int taskId, int arrivalTime, int burstTime, int priority){
        this.taskId = taskId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    } 
    
    public void resume(){
        synchronized (lock) {
            isPaused = false;
            lock.notify();
        }
    }
    
    public void pause(){
        synchronized (lock) {
            isPaused = true;
        }
    }
    
    @Override 
    public void run(){
        try {
            while(remainingTime > 0){
                synchronized (lock) {
                    while(isPaused) lock.wait();
                    // Execute one unit of work
                    remainingTime--;
                    Thread.sleep(100);
                    isPaused = true;
                }
            }
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public boolean isCompleted() { return remainingTime == 0; }
    public int getRemainingTime() { return remainingTime; }
    
    public void calculateTimes(int currentTime) {
        this.completionTime = currentTime;
        this.turnaroundTime = currentTime - arrivalTime;
        this.waitingTime = turnaroundTime - burstTime;
    }    
    
    public int getArrivalTime(){  return arrivalTime ;}
    public int getTaskId(){ return taskId;}
    public int getBurstTime(){ return burstTime;}
    public int getPriority(){ return priority;}
    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getCompletionTime() { return completionTime; }
}

interface Scheduler {
    public void execute(List<Task> taskList) throws InterruptedException ;
}

class RoundRobinScheduler implements  Scheduler {
    int timeQuantum;
    Map<Task,Thread> taskThreadMap;

    public RoundRobinScheduler(int timeQuantum){
        this.timeQuantum = timeQuantum;
        this.taskThreadMap = new HashMap<>();
    }
    
    @Override 
    public void execute(List<Task> taskList) throws InterruptedException {
        // Sort by arrival time first
        taskList.sort(Comparator.comparingInt(Task::getArrivalTime));
        
        Queue<Task> q = new LinkedList<>();

        for(Task t : taskList) {
            Thread thread = new Thread(t);
            thread.start(); 
            taskThreadMap.put(t, thread);
        }

        int currentTime = 0; 
        int completedTasks = 0; 
        int index = 0;

        // adding the queue, the tasks that have arrived 
        while(completedTasks < taskList.size()) {
            while(index < taskList.size() && taskList.get(index).getArrivalTime() <= currentTime){
                q.offer(taskList.get(index));
                index++;
            }  
            
            if(q.isEmpty()) {
                currentTime++;
                Thread.sleep(100);
                continue;
            }

            Task currentTask = q.poll();
            int units = 0;

            while(units < timeQuantum && !currentTask.isCompleted()){
                currentTask.resume(); // execute one unit 
                Thread.sleep(100);
                currentTime++;
                units++; 
                
                // Other tasks might arrive at the same time
                while(index < taskList.size() && taskList.get(index).getArrivalTime() <= currentTime){
                    q.offer(taskList.get(index));
                    index++;
                } 
            }
            
            if(currentTask.isCompleted()) {
                currentTask.calculateTimes(currentTime);
                completedTasks++;
            }
            else {
                // task is paused and not completed 
                q.offer(currentTask);
            }
        }
        
        // Join threads 
        for (Thread t : taskThreadMap.values()) {
            t.join();
        }
        printStatistics(taskList);
    }
    
    private void printStatistics(List<Task> taskList){
        // Sort tasks by completion time (first to finish to last to finish)
        List<Task> sortedByCompletion = new ArrayList<>(taskList);
        sortedByCompletion.sort(Comparator.comparingInt(Task::getCompletionTime));
        
        int totalWT = 0, totalTAT = 0;

        System.out.printf("\n%-6s %-8s %-8s %-9s %-14s %-17s %-15s\n", 
            "Task", "Arrival", "Burst", "Priority", "Waiting Time", "Turnaround Time", "Completion Time");
        System.out.println("--------------------------------------------------------------------------------");

        for(Task task : sortedByCompletion){
            System.out.printf("%-6d %-8d %-8d %-9d %-14d %-17d %-15d\n",
                task.getTaskId(), task.getArrivalTime(), task.getBurstTime(), task.getPriority(),
                task.getWaitingTime(), task.getTurnaroundTime(), task.getCompletionTime());

            totalWT += task.getWaitingTime();
            totalTAT += task.getTurnaroundTime();
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", (double)totalWT / taskList.size());
        System.out.printf("Average Turnaround Time: %.2f\n", (double)totalTAT / taskList.size());
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, 0, 5, 5));
        taskList.add(new Task(2, 1, 1, 4));
        taskList.add(new Task(3, 2, 4, 3));
        taskList.add(new Task(4, 3, 6, 2));
        taskList.add(new Task(5, 4, 4, 1));

        int timeQuantum = 4;
        Scheduler sc = new RoundRobinScheduler(timeQuantum);
        try {
            sc.execute(taskList);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}