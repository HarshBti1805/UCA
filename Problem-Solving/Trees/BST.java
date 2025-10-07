public class BST<Key extends Comparable<Key>, Value> {
    private class Node {
        Key key;
        Value val;
        Node left, right;

        Node(Key key, Value value) {
            this.key = key;
            this.val = value;
        }
    }

    private Node root;

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x.val;
        else if (cmp < 0)
            return get(x.left, key);
        else
            return get(x.right, key);
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, value);
        else if (cmp > 0)
            x.right = put(x.right, key, value);
        else
            x.val = value;
        return x;
    }

    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        System.out.print(x.key + " ");
        inorder(x.right);
    }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<>();

        // Insert elements
        bst.put(10, "Ten");
        bst.put(5, "Five");
        bst.put(20, "Twenty");
        bst.put(15, "Fifteen");

        // Print tree structure
        bst.inorder(); // Expected: 5 10 15 20

        // ✅ Assertions for correctness
        assert bst.get(10).equals("Ten") : "Failed: Key 10 should map to 'Ten'";
        assert bst.get(5).equals("Five") : "Failed: Key 5 should map to 'Five'";
        assert bst.get(20).equals("Twenty") : "Failed: Key 20 should map to 'Twenty'";
        assert bst.get(15).equals("Fifteen") : "Failed: Key 15 should map to 'Fifteen'";
        assert bst.get(99) == null : "Failed: Key 99 should not exist";

        // Update value
        bst.put(10, "TEN");
        assert bst.get(10).equals("TEN") : "Failed: Key 10 should now map to 'TEN'";

        // Simple output confirmation
        System.out.println("All tests passed");
    }
}

