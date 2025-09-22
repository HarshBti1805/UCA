import java.util.LinkedList;
public class HashMapSC<K, V> {
    // Node class to store key-value pair
    private static class Node<K, V> {
        K key;
        V value;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int SIZE = 16;  // number of buckets
    private LinkedList<Node<K, V>>[] buckets;

    // Constructor
    @SuppressWarnings("unchecked")
    public HashMapSC() {
        buckets = new LinkedList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    // Hash function
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % SIZE);
    }

    // Put key-value pair
    public void put(K key, V value) {
        int index = getBucketIndex(key);
        LinkedList<Node<K, V>> bucket = buckets[index];

        // check if key already exists -> update
        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // otherwise insert new node
        bucket.add(new Node<>(key, value));
    }

    // Get value by key
    public V get(K key) {
        int index = getBucketIndex(key);
        LinkedList<Node<K, V>> bucket = buckets[index];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null; // not found
    }

    // Remove key
    public boolean remove(K key) {
        int index = getBucketIndex(key);
        LinkedList<Node<K, V>> bucket = buckets[index];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                bucket.remove(node);
                return true;
            }
        }
        return false;
    }

    // Print HashMap (for testing)
    public void printMap() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print("Bucket " + i + ": ");
            for (Node<K, V> node : buckets[i]) {
                System.out.print("[" + node.key + "=" + node.value + "] ");
            }
            System.out.println();
        }
    }

    // Main test
    public static void main(String[] args) {
        HashMapSC<String, Integer> map = new HashMapSC<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("A", 10);  // update A
        map.put("Harsh",223);
        map.put("A",55);

        System.out.println("Value of A: " + map.get("A")); // 10
        System.out.println("Value of B: " + map.get("B")); // 2

        map.remove("B");
        System.out.println("Value of B after remove: " + map.get("B")); // null
        System.out.println("Value of Harsh : " + map.get("Harsh"));
        map.printMap();
    }
}

