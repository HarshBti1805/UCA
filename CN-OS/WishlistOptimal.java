import java.util.*;

public class WishlistOptimal {
    private final Map<String, Integer> mp; // product -> count
    private final PriorityQueue<Map.Entry<String, Integer>> maxHeap;
    private final PriorityQueue<Map.Entry<String, Integer>> minHeap;

    public WishlistOptimal () {
        mp = new HashMap<>();
        maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue()); // max count first
        minHeap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue)); // min count first
    }

    private void pushToHeaps(String product, int count) {
        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>(product, count);
        maxHeap.offer(entry);
        minHeap.offer(entry);
    }

    public void addProduct(String product) {
        int newCount = mp.getOrDefault(product, 0) + 1;
        mp.put(product, newCount);
        pushToHeaps(product, newCount);
    }

    public void delList(String product) {
        if (!mp.containsKey(product)) return;
        int newCount = mp.get(product) - 1;
        if (newCount <= 0) {
            mp.remove(product);
        } else {
            mp.put(product, newCount);
            pushToHeaps(product, newCount);
        }
    }

    public String getMaxProduct() {
        while (!maxHeap.isEmpty()) {
            Map.Entry<String, Integer> top = maxHeap.peek();
            if (mp.containsKey(top.getKey()) && mp.get(top.getKey()).equals(top.getValue())) {
                return top.getKey();
            }
            maxHeap.poll(); // outdated, remove
        }
        return "No products";
    }

    public String getMinProduct() {
        while (!minHeap.isEmpty()) {
            Map.Entry<String, Integer> top = minHeap.peek();
            if (mp.containsKey(top.getKey()) && mp.get(top.getKey()).equals(top.getValue())) {
                return top.getKey();
            }
            minHeap.poll(); // outdated, remove
        }
        return "No products";
    }

    public static void main(String[] args) {
        Wishlist w = new Wishlist();
        w.addProduct("a");
        w.addProduct("a");
        w.addProduct("a");

        w.addProduct("b");
        w.addProduct("b");

        System.out.println(w.getMaxProduct()); // a
        System.out.println(w.getMinProduct()); // b

        w.delList("a");
        w.delList("a");

        System.out.println(w.getMaxProduct()); // b
        System.out.println(w.getMinProduct()); // a

        w.delList("a");
        w.delList("a");

        System.out.println(w.getMaxProduct()); // b
        System.out.println(w.getMinProduct()); // b
    }
}
