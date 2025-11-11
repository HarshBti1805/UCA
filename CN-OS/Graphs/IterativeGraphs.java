import java.util.*;
public class IterativeGraphs {
    Map<Integer, Set<Integer>> edgeMap;
    IterativeGraphs() {
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
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(Arrays.asList(startNode));

        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll();
            int last = path.get(path.size() - 1);

            boolean extended = false;

            for (int neighbor : edgeMap.getOrDefault(last, Collections.emptySet())) {
                if (!path.contains(neighbor)) {
                    extended = true;
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
            if (!extended) {
                StringBuilder sb = new StringBuilder();
                for (int node : path) sb.append(node);
                res.add(sb.toString());
            }
        }

        return res;
    }

    public static void main(String[] args) {
        IterativeGraphs g = new IterativeGraphs();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(2, 3);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(3, 4);
        System.out.println(g.getAllPaths(1));
    }
}

