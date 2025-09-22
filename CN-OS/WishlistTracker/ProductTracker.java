import java.util.HashMap;
import java.util.Map;

public class ProductTracker {
    private final Map<String, Integer> productCount;

    public ProductTracker() {
        productCount = new HashMap<>();
    }

    // Increment wishlist count for a product
    public void wishlist(String productName) {
        productCount.put(productName, productCount.getOrDefault(productName, 0) + 1);
    }

    // Decrement wishlist count (remove if it reaches 0)
    public void delist(String productName) {
        if (productCount.containsKey(productName)) {
            int newCount = productCount.get(productName) - 1;
            if (newCount <= 0) {
                productCount.remove(productName);
            } else {
                productCount.put(productName, newCount);
            }
        }
    }

    // Return product with maximum wishlist count
    public String getMaxProduct() {
        if (productCount.isEmpty()) return null;

        String maxProduct = null;
        int maxCount = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : productCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxProduct = entry.getKey();
            }
        }
        return maxProduct;
    }

    // Return product with minimum wishlist count
    public String getMinProduct() {
        if (productCount.isEmpty()) return null;

        String minProduct = null;
        int minCount = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : productCount.entrySet()) {
            if (entry.getValue() < minCount) {
                minCount = entry.getValue();
                minProduct = entry.getKey();
            }
        }
        return minProduct;
    }

    // For debugging
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
