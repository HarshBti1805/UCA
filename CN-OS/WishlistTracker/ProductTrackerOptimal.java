import java.utilI.*;

public class ProductTrackerOptimal {
    private final Map<String, Integer> productCount;
    private final TreeMap<Integer, Set<String>> countMap;

    public ProductTrackerOptimal () {
        productCount = new HashMap<>();
        countMap = new TreeMap<>();
    }

    // Add product to wishlist (increment count)
    public void wishlist(String productName) {
        int oldCount = productCount.getOrDefault(productName, 0);
        int newCount = oldCount + 1;
        productCount.put(productName, newCount);

        // Remove from old count bucket
        if (oldCount > 0) {
            removeFromCountMap(oldCount, productName);
        }

        // Add to new count bucket
        countMap.computeIfAbsent(newCount, k -> new HashSet<>()).add(productName);
    }

    // Remove product from wishlist (decrement count)
    public void delist(String productName) {
        if (!productCount.containsKey(productName)) return;

        int oldCount = productCount.get(productName);
        removeFromCountMap(oldCount, productName);

        if (oldCount == 1) {
            productCount.remove(productName);
        } else {
            int newCount = oldCount - 1;
            productCount.put(productName, newCount);
            countMap.computeIfAbsent(newCount, k -> new HashSet<>()).add(productName);
        }
    }

    // Get product with maximum wishlist count
    public String getMaxProduct() {
        if (countMap.isEmpty()) return null;
        Map.Entry<Integer, Set<String>> entry = countMap.lastEntry();
        return entry.getValue().iterator().next(); // return any product
    }

    // Get product with minimum wishlist count
    public String getMinProduct() {
        if (countMap.isEmpty()) return null;
        Map.Entry<Integer, Set<String>> entry = countMap.firstEntry();
        return entry.getValue().iterator().next(); // return any product
    }

    // Helper: remove product from countMap
    private void removeFromCountMap(int count, String productName) {
        Set<String> products = countMap.get(count);
        if (products != null) {
            products.remove(productName);
            if (products.isEmpty()) {
                countMap.remove(count);
            }
        }
    }

    // Debug: print all products
    public void printAll() {
        System.out.println(productCount);
    }

    // Demo
    public static void main(String[] args) {
        ProductTracker tracker = new ProductTracker();

        tracker.wishlist("Apple");
        tracker.wishlist("Banana");
        tracker.wishlist("Banana");
        tracker.wishlist("Mango");
        tracker.wishlist("Mango");
        tracker.wishlist("Mango");

        tracker.printAll(); // {Apple=1, Banana=2, Mango=3}

        System.out.println("Max wished product: " + tracker.getMaxProduct()); // Mango
        System.out.println("Min wished product: " + tracker.getMinProduct()); // Apple

        tracker.delist("Mango");
        tracker.delist("Apple");
        tracker.printAll(); // {Banana=2, Mango=2}

        System.out.println("Max wished product: " + tracker.getMaxProduct()); // Banana or Mango
        System.out.println("Min wished product: " + tracker.getMinProduct()); // Banana or Mango
    }
}
