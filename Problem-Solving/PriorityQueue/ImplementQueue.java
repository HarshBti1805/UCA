public class ImplementQueue {
    private int[] heap;    
    private int size;      
    private int capacity; 

    public ImplementQueue(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }
    public void enqueue(int val) {
        if (size == capacity) {
            throw new IllegalStateException("Queue is full");
        }
        heap[size] = val; 
        heapifyUp(size);     
        size++;
    }
    public int dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);              
        return min;
    }
    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] < heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }
    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    public static void main(String[] args) {
        ImplementQueue queue = new ImplementQueue(10);
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(8);
        queue.enqueue(1);

        System.out.println("Peek: " + queue.peek()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 2
        System.out.println("Peek: " + queue.peek()); // 5
        System.out.println("Size: " + queue.size()); // 2
    }
}
