import java.util.*;
public class AllOne {
    class Node {
        int freq;
        Set<String> products;
        Node prev, next;
        Node(int freq) {
            this.freq = freq;
            this.products = new HashSet<>();
        }
    }
    private Node head, tail;
    private Map<String, Node> productNodeMap;

    public AllOne() {
        head = new Node(-1); 
        tail = new Node(-1);  
        head.next = tail;
        tail.prev = head;
        productNodeMap = new HashMap<>();
    }
    public void inc(String key) {
        if (!productNodeMap.containsKey(key)) {
            if (head.next == tail || head.next.freq != 1) {
                Node newNode = new Node(1);
                insertNodeAfter(head, newNode);
            }
            head.next.products.add(key);
            productNodeMap.put(key, head.next);
        } else {
            Node curr = productNodeMap.get(key);
            int newFreq = curr.freq + 1;
            if (curr.next == tail || curr.next.freq != newFreq) {
                Node newNode = new Node(newFreq);
                insertNodeAfter(curr, newNode);
            }
            Node nextNode = curr.next;
            curr.products.remove(key);
            nextNode.products.add(key);
            productNodeMap.put(key, nextNode);
            if (curr.products.isEmpty()) {
                removeNode(curr);
            }
        }
    }

    public void dec(String key) {
        if (!productNodeMap.containsKey(key)) return;

        Node curr = productNodeMap.get(key);
        int newFreq = curr.freq - 1;

        if (newFreq == 0) {
            curr.products.remove(key);
            productNodeMap.remove(key);
        } else {
            if (curr.prev == head || curr.prev.freq != newFreq) {
                Node newNode = new Node(newFreq);
                insertNodeAfter(curr.prev, newNode);
            }
            Node prevNode = curr.prev;
            curr.products.remove(key);
            prevNode.products.add(key);
            productNodeMap.put(key, prevNode);
        }
        if (curr.products.isEmpty()) {
            removeNode(curr);
        }
    }
    public String getMaxKey() {
        if (tail.prev == head) return "";
        return this.tail.prev.products.stream().findFirst().get();
    }

    public String getMinKey() {
        if (head.next == tail) return "";
        return this.head.next.products.stream().findFirst().get();
    }

    private void insertNodeAfter(Node prevNode, Node newNode) {
        newNode.next = prevNode.next;
        newNode.prev = prevNode;
        prevNode.next.prev = newNode;
        prevNode.next = newNode;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
    public static void main(String[] args) {
        AllOne allOne = new AllOne();

        allOne.inc("apple");
        allOne.inc("banana");
        allOne.inc("apple");

        System.out.println("Max Key: " + allOne.getMaxKey());
        System.out.println("Min Key: " + allOne.getMinKey());

        allOne.dec("apple");
        System.out.println("Max Key after decrement: " + allOne.getMaxKey());
        System.out.println("Min Key after decrement: " + allOne.getMinKey());

        allOne.inc("banana");
        allOne.inc("banana");
        System.out.println("Max Key now: " + allOne.getMaxKey());
        System.out.println("Min Key now: " + allOne.getMinKey());

        allOne.dec("apple");
        allOne.dec("banana");
        System.out.println("Max Key after more decrements: " + allOne.getMaxKey());
        System.out.println("Min Key after more decrements: " + allOne.getMinKey());
    }

}

