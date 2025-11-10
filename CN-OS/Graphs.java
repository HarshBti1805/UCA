class Graph {
    int nodes;
    int[][] matrix; // n * n matrix -> easy to check if node exist or not matrix[i][j];
    List<int[]> edges;  // more efficient than matrix -> new int[] {a,b,c};
    Map<Integer,Set<Integer>> adjList; // best structure for storing a -> {b,c} | b -> {c}


}
