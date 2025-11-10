import java.util.*;

public class AllPathsInGraph {
    Map<Integer, Set<Integer>> edgeMap;

    AllPathsInGraph() {
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

        res.add(path.toString());

        for (int neighbour : edgeMap.getOrDefault(curr, Collections.emptySet())) {
            if (!vis[neighbour]) {
                dfs(neighbour, vis, path, res);
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        vis[curr] = false;
    }

    public static void main(String[] args) {
        AllPathsInGraph g = new AllPathsInGraph();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(2, 3);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(3, 4);

        System.out.println("All paths from node 1:");
        System.out.println(g.getAllPaths(1));
    }
}

