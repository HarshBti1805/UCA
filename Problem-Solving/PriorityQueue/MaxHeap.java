import java.util.*;
class MaxPriorityQueue {
    private ArrayList<Integer> heap;
    public MaxPriorityQueue() {
        heap = new ArrayList<>();
    }
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    public void insert(int value) {
        heap.add(value);
        int i = heap.size() - 1;
        while (i != 0 && heap.get(parent(i)) < heap.get(i)) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    public int remove() {
        if (heap.isEmpty()) {
            System.out.println("Heap is empty.");
            return -1;
        }

        int max = heap.get(0);
        int last = heap.get(heap.size() - 1);
        heap.set(0, last);
        heap.remove(heap.size() - 1);
        heapify(0);
        return max;
    }
    private void heapify(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && heap.get(left) > heap.get(largest))
            largest = left;

        if (right < heap.size() && heap.get(right) > heap.get(largest))
            largest = right;

        if (largest != i) {
            swap(i, largest);
            heapify(largest);
        }
    }
    public int top() {
        if (heap.isEmpty()) {
            System.out.println("Heap is empty.");
            return -1;
        }
        return heap.get(0);
    }
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }
    public void printHeap() {
        System.out.println(heap);
    }
}
public class MaxHeap {
    public static void main(String[] args) {
        MaxPriorityQueue heap = new MaxPriorityQueue();

        heap.insert(50);
        heap.insert(30);
        heap.insert(20);
        heap.insert(40);
        heap.insert(70);

        System.out.println("Heap Elements:");
        heap.printHeap(); // Should show a valid Max-Heap structure

        System.out.println("\nExtract Max: " + heap.remove()); // Should extract 70
        System.out.println("Current Max: " + heap.top());       // Should be 50 now

        System.out.println("\nHeap after extraction:");
        heap.printHeap();
    }
}
