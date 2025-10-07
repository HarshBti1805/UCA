public class BST<Key extends Comparable<Key>, Value> {
    private class Node {
        Key key;
        Value val;
        Node left, right;
        int size; // number of nodes in this subtree

        Node(Key key, Value value, int size) {
            this.key = key;
            this.val = value;
            this.size = size;
        }
    }

    private Node root;

    // ---------- Size ----------
    private int size(Node x) {
        return x == null ? 0 : x.size;
    }

    public int size() {
        return size(root);
    }

    // ---------- Put ----------
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, value);
        else if (cmp > 0)
            x.right = put(x.right, key, value);
        else
            x.val = value;

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    // ---------- Get ----------
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

    // ---------- Min / Max ----------
    public Key min() {
        if (root == null) return null;
        return min(root).key;
    }

    private Node min(Node x) {
        return (x.left == null) ? x : min(x.left);
    }

    public Key max() {
        if (root == null) return null;
        return max(root).key;
    }

    private Node max(Node x) {
        return (x.right == null) ? x : max(x.right);
    }

    // ---------- Floor / Ceil ----------
    public Key floor(Key key) {
        Node x = floor(root, key);
        return (x == null) ? null : x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        return (t != null) ? t : x;
    }

    public Key ceil(Key key) {
        Node x = ceil(root, key);
        return (x == null) ? null : x.key;
    }

    private Node ceil(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceil(x.right, key);
        Node t = ceil(x.left, key);
        return (t != null) ? t : x;
    }

    // ---------- Rank ----------
    // Returns the number of keys smaller than the given key
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(key, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        else
            return size(x.left);
    }

    // ---------- Traversal ----------
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

    // ---------- Test ----------
    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<>();
        bst.put(10, "Ten");
        bst.put(5, "Five");
        bst.put(20, "Twenty");
        bst.put(15, "Fifteen");
        bst.put(25, "Twenty-Five");

        bst.inorder(); // Expected: 5 10 15 20 25

        // Core assertions
        assert bst.get(15).equals("Fifteen") : "Failed: get(15)";
        assert bst.min() == 5 : "Failed: min()";
        assert bst.max() == 25 : "Failed: max()";
        assert bst.floor(17) == 15 : "Failed: floor(17)";
        assert bst.ceil(17) == 20 : "Failed: ceil(17)";

        // Rank assertions
        // Sorted keys: [5, 10, 15, 20, 25]
        assert bst.rank(5) == 0 : "Failed: rank(5)";
        assert bst.rank(10) == 1 : "Failed: rank(10)";
        assert bst.rank(15) == 2 : "Failed: rank(15)";
        assert bst.rank(22) == 4 : "Failed: rank(22)";
        assert bst.rank(30) == 5 : "Failed: rank(30)";

        System.out.println("All tests passed");
    }
}

