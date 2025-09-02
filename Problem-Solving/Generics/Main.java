import java.util.*;

class PriorityQueue<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    // Insert element
    public void add(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    // Remove highest priority element
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T root = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return root;
    }

    // Peek highest priority element
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(0);
    }

    // Check if heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Return heap size
    public int size() {
        return heap.size();
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Heapify Up (for insertion)
    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;

        while (index > 0 && heap.get(index).compareTo(heap.get(parent)) < 0) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    // Heapify Down (for polling)
    private void heapifyDown(int index) {
        int left, right, smallest;

        while (true) {
            left = 2 * index + 1;
            right = 2 * index + 2;
            smallest = index;

            if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
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
}

public class Main {
    public static void main(String[] args) {
        // Integer PQ (Min-Heap)
        PriorityQueue<Integer> intPQ = new PriorityQueue<>();
        intPQ.add(5);
        intPQ.add(1);
        intPQ.add(3);

        System.out.println("Integer PQ:");
        while (!intPQ.isEmpty()) {
            System.out.println(intPQ.poll());
        }

        // String PQ (Alphabetical order)
        PriorityQueue<String> strPQ = new PriorityQueue<>();
        strPQ.add("Banana");
        strPQ.add("Apple");
        strPQ.add("Cherry");

        System.out.println("\nString PQ:");
        while (!strPQ.isEmpty()) {
            System.out.println(strPQ.poll());
        }
    }
}

