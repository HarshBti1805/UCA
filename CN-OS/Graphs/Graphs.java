import java.util.*;

public class Graphs {
    Map<Integer, Set<Integer>> edgeMap;

    Graphs () {
        edgeMap = new HashMap<>();
    }

    void addEdge(int a, int b) {
        edgeMap.putIfAbsent(a, new HashSet<>());
        edgeMap.putIfAbsent(b, new HashSet<>());
        edgeMap.get(a).add(b);
        edgeMap.get(b).add(a);
    }

    List<String> getAllPaths(int startNode) {
        List<String> res = new ArrayList<>();

        // Find the max node to size visited array
        int maxNode = 0;
        for (int node : edgeMap.keySet()) {
            if (node > maxNode) maxNode = node;
        }
        boolean[] vis = new boolean[maxNode + 1];

        dfs(startNode, vis, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int curr, boolean[] vis, List<Integer> path, List<String> res) {
        vis[curr] = true;
        path.add(curr);

        // Check if there exists any unvisited neighbor
        boolean hasUnvisitedNeighbor = false;
        for (int neighbour : edgeMap.getOrDefault(curr, Collections.emptySet())) {
            if (!vis[neighbour]) {
                hasUnvisitedNeighbor = true;
                dfs(neighbour, vis, path, res);
            }
        }

        // If no unvisited neighbors, it's a leaf for this path -> record it
        if (!hasUnvisitedNeighbor) {
            // Convert to compact string like 1234 (without commas/spaces) if desired:
            StringBuilder sb = new StringBuilder();
            for (int node : path) sb.append(node);
            res.add(sb.toString());
            // If you prefer the path format "[1, 2, 3, 4]" instead, use:
            // res.add(path.toString());
        }

        // Backtrack
        path.remove(path.size() - 1);
        vis[curr] = false;
    }

    public static void main(String[] args) {
        Graphs g = new Graphs();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(2, 3);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(3, 4);

        System.out.println(g.getAllPaths(1)); // expected: [1234, 1236, 1237, 125]
    }
}

