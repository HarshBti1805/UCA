import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
class Medicine {
    String name;
    int frequency;
    int priority;

    Medicine(String name, int frequency, int priority) {
        this.name = name;
        this.frequency = frequency;
        this.priority = priority; 
    }
}

class Schedule implements Comparable<Schedule> {
    int time;
    int priority;
    Medicine med;

    Schedule(int time, int priority, Medicine med) {
        this.time = time;
        this.priority = priority;
        this.med = med;
    }
    
    public int compareTo(Schedule o) {
        if (this.time != o.time) {
            return this.time - o.time; // earlier first
        }
        return this.priority - o.priority; // higher priority first
    }
}

public class Rockabye {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        for (int tc = 0; tc < t; tc++) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            scanner.nextLine(); // consume newline

            List<Medicine> meds = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                String[] parts = scanner.nextLine().split(" ");
                String name = parts[0];
                int freq = Integer.parseInt(parts[1]);
                meds.add(new Medicine(name, freq, j));
            }
            
            PriorityQueue<Schedule> pq = new PriorityQueue<>();
            for (Medicine m : meds) {
                pq.add(new Schedule(m.frequency, m.priority, m));
            }

            for (int step = 0; step < k; step++) {
                Schedule curr = pq.poll();
                System.out.println(curr.time + " " + curr.med.name);
                pq.add(new Schedule(curr.time + curr.med.frequency, curr.priority, curr.med));
            }
        }

        scanner.close();
    }
}
