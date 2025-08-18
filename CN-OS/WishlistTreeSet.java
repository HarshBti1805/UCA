import java.util.*;

public class WishlistTreeSet {
    private final Map<String, Integer> mp;                     // product → count
    private final TreeMap<Integer, Set<String>> freq;          // count → products

    public WishlistTreeSet() {
        mp = new HashMap<>();
        freq = new TreeMap<>();
    }

    private void addToFreq(int count, String product) {
        freq.computeIfAbsent(count, k -> new HashSet<>()).add(product);
    }

    private void removeFromFreq(int count, String product) {
        Set<String> set = freq.get(count);
        if (set != null) {
            set.remove(product);
            if (set.isEmpty()) freq.remove(count);
        }
    }

    public void addProduct(String product) {
        int oldCount = mp.getOrDefault(product, 0);
        int newCount = oldCount + 1;
        mp.put(product, newCount);

        if (oldCount > 0) removeFromFreq(oldCount, product);
        addToFreq(newCount, product);
    }

    public void delList(String product) {
        if (!mp.containsKey(product)) return;

        int oldCount = mp.get(product);
        int newCount = oldCount - 1;

        removeFromFreq(oldCount, product);

        if (newCount == 0) {
            mp.remove(product);
        } else {
            mp.put(product, newCount);
            addToFreq(newCount, product);
        }
    }

    public String getMaxProduct() {
        if (freq.isEmpty()) return "No products";
        return freq.lastEntry().getValue().iterator().next();
    }

    public String getMinProduct() {
        if (freq.isEmpty()) return "No products";
        return freq.firstEntry().getValue().iterator().next();
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
