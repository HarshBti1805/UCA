import java.util.ArrayList;
class Node {
    int data;
    int priority;

    public Node(int data, int priority) {
        this.data = data;
        this.priority = priority;
    }
}
class PriorityQueue {
    private ArrayList<Node> heap;
    public PriorityQueue() {
        heap = new ArrayList<>();
    }
    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    public void enqueue(int data, int priority) {
        Node node = new Node(data, priority);
        heap.add(node);
        int i = heap.size() - 1;

        // Heapify up
        while (i != 0 && heap.get(parent(i)).priority > heap.get(i).priority) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    public int dequeue() {
        if (heap.isEmpty()) {
            System.out.println("Priority Queue is empty.");
            return -1;
        }
        Node root = heap.get(0);
        Node lastNode = heap.get(heap.size() - 1);
        heap.set(0, lastNode);
        heap.remove(heap.size() - 1);
        heapify(0);
        return root.data;
    }
    private void heapify(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && heap.get(left).priority < heap.get(smallest).priority)
            smallest = left;

        if (right < heap.size() && heap.get(right).priority < heap.get(smallest).priority)
            smallest = right;

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }
    public int peek() {
        if (heap.isEmpty()) {
            System.out.println("Priority Queue is empty.");
            return -1;
        }
        return heap.get(0).data;
    }
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }
    public void printQueue() {
        for (Node node : heap) {
            System.out.println("Data: " + node.data + " Priority: " + node.priority);
        }
    }
}
public class MinHeap {
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        pq.enqueue(10, 2);
        pq.enqueue(30, 4);
        pq.enqueue(20, 1);
        pq.enqueue(40, 3);

        System.out.println("Priority Queue elements:");
        pq.printQueue();

        System.out.println("\nDequeue: " + pq.dequeue()); // Should remove data with priority 1 (20)
        System.out.println("Peek: " + pq.peek());         // Should show data with next highest priority (10)

        System.out.println("\nPriority Queue after Dequeue:");
        pq.printQueue();
    }
}
