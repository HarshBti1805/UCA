import java.util.*;
public class GraphCycle {
    Map<Integer, Set<Integer>> adj;

    GraphCycle() {
        adj = new HashMap<>();
    }

    void addEdge(int u, int v) {
        adj.putIfAbsent(u, new HashSet<>());
        adj.putIfAbsent(v, new HashSet<>());
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    boolean hasCycle() {
        Set<Integer> visited = new HashSet<>();
        for (int start : adj.keySet()) {
            if (!visited.contains(start)) {
                if (bfsCycle(start, visited)) return true;
            }What is kubelet and kube proxy What is kubelet and kube proxy
        }
        return false;
    }
    private boolean dfsCycle(int node, int parent, Set<Integer> visited) {
        visited.add(node);

        for (int neighbor : adj.getOrDefault(node, Collections.emptySet())) {
            if (!visited.contains(neighbor)) {
                if (dfsCycle(neighbor, node, visited)) return true;
            } else if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }


    private boolean bfsCycle(int start, Set<Integer> visited) {
        Queue<int[]> queue = new LinkedList<>(); // each element = [node, parent]
        queue.add(new int[]{start, -1});
        visited.add(start);

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0];
            int parent = curr[1];

            for (int neighbor : adj.getOrDefault(node, Collections.emptySet())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new int[]{neighbor, node});
                }
                else if (neighbor != parent) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GraphCycle g = new GraphCycle();
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 2); // introduces a cycle

        System.out.println("Cycle present? " + g.hasCycle()); // ✅ true
    }
}

