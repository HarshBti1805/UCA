import java.util.*;
public class Wishlist {
    private Map<String, Integer> mp;
    public Wishlist() {
        mp = new HashMap<>();
    }
    public void addProduct(String product) {
        mp.put(product, mp.getOrDefault(product, 0) + 1);
    }
    public void delList(String product) {
        if (!mp.containsKey(product)) return;
        int count = mp.get(product);
        count--;
        if (count <= 0) {
            mp.remove(product);
        } else {
            mp.put(product, count);
        }
    }
    public String getMaxProduct() {
        if (mp.isEmpty()) return "No products";
        String res = "";
        int maxi = 0;
        for (Map.Entry<String, Integer> entry : mp.entrySet()) {
            if (entry.getValue() > maxi) {
                maxi = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;
    }
    public String getMinProduct() {
        if (mp.isEmpty()) return "No products";
        String res = "";
        int mini = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : mp.entrySet()) {
            if (entry.getValue() < mini) {
                mini = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Wishlist w = new Wishlist();
        w.addProduct("a");
        w.addProduct("a");
        w.addProduct("a");

        w.addProduct("b");
        w.addProduct("b");

        System.out.println(w.getMaxProduct());
        System.out.println(w.getMinProduct());
        
        w.delList("a");
        w.delList("a");

        System.out.println(w.getMaxProduct());
        System.out.println(w.getMinProduct());

        w.delList("a");
        w.delList("a");

        System.out.println(w.getMaxProduct());
        System.out.println(w.getMinProduct());


    }
}
