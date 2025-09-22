import java.util.LinkedList;
public class HashTable<Key, Value> {

    private static class Node<Key, Value> {
        Key key;
        Value value;
        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int N = 16;  // number of buckets
    private LinkedList<Node<Key, Value>>[] table;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = (LinkedList<Node<Key, Value>>[]) new LinkedList[N];
        for (int i = 0; i < N; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(Key key) {
        return Math.abs(key.hashCode()) % N;
    }

    public void put(Key key, Value value) {
        int i = hash(key);
        for (Node<Key, Value> node : table[i]) {
            if (node.key.equals(key)) {
                node.value = value; // update existing
                return;
            }
        }
        table[i].add(new Node<>(key, value)); // new entry
    }
    public Value get(Key key) {
        int i = hash(key);
        for (Node<Key, Value> node : table[i]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null; // not found
    }
    public boolean remove(Key key) {
        int i = hash(key);
        for (Node<Key, Value> node : table[i]) {
            if (node.key.equals(key)) {
                table[i].remove(node);
                return true;
            }
        }
        return false;
    }

    public void printTable() {
        for (int i = 0; i < N; i++) {
            System.out.print("Bucket " + i + ": ");
            for (Node<Key, Value> node : table[i]) {
                System.out.print("[" + node.key + "=" + node.value + "] ");
            }
            System.out.println();
        }
    }

    // test
    public static void main(String[] args) {
        HashTable<String, Integer> ht = new HashTable<>();
        ht.put("A", 1);
        ht.put("B", 2);
        ht.put("C", 3);
        ht.put("A", 99); // update A

        System.out.println("A -> " + ht.get("A")); // 99
        System.out.println("B -> " + ht.get("B")); // 2
        ht.remove("B");
        System.out.println("B after remove -> " + ht.get("B")); // null

        ht.printTable();
    }
}

