import java.util.PriorityQueue;
import java.util.Collections;
public class StreamMedian {
    private PriorityQueue<Integer> maxHeap; // Lower half (as Max-Heap)
    private PriorityQueue<Integer> minHeap; // Higher half (as Min-Heap)

    public StreamMedian() {
        maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
        minHeap = new PriorityQueue<Integer>();
    }
    public void addNum(int num) {
        maxHeap.add(num);
        minHeap.add(maxHeap.poll());

        if (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }
    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }
    public static void main(String[] args) {
        StreamMedian medianFinder = new StreamMedian();
        int[] nums = {5, 15, 1, 3};

        for (int num : nums) {
            medianFinder.addNum(num);
            System.out.println("Added: " + num + ", Current median: " + medianFinder.findMedian());
        }
    }
}

