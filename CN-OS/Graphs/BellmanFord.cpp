// The intuition is that longest simple path can have at most V-1 edges,
// so after V-1 rounds all shortest paths must be relaxed / finalized
//
// Run the initial relaxation for V-1 edges
// Run another relaxation and if the distance still decreases it means that
// negative cycle exists.
//
// TC : O(V * E)
// SC : O(V) (extra --> distance array)

#include <bits/stdc++.h>
using namespace std;
int main() {
  int V, E;
  cin >> V >> E;

  vector<vector<int>> edges(E, vector<int>(3, 0));
  for (int i = 0; i < E; i++) {
    cin >> edges[i][0] >> edges[i][1] >> edges[i][2];
  }
  int src;
  cin >> src;

  vector<int> dist(V, 1e9);
  dist[src] = 0;

  // Relax all edges V-1 times
  for (int i = 0; i < V - 1; i++) {
    for (auto it : edges) {
      int u = it[0];
      int v = it[1];
      int wt = it[2];

      if (dist[u] != 1e9 && dist[u] + wt < dist[v]) {
        dist[v] = dist[u] + wt;
      }
    }
  }

  // Relax for negatives edges
  for (auto it : edges) {
    int u = it[0];
    int v = it[1];
    int wt = it[2];

    if (dist[u] != 1e9 && dist[u] + wt < dist[v]) {
      // since it if there is still a smaller weight that exist that means that
      // the initial relaxation couldnt complete due to existing negative cycle
      // or negative weight
      return 0;
    }
  }

  for (int i = 0; i < V; i++) {
    cout << dist[i] << " ";
  }
  return 0;
}
