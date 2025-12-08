#include <bits/stdc++.h>
using namespace std;

const int INF = 1e9;

int main() {
  int V;
  cin >> V;

  vector<vector<int>> dist(V, vector<int>(V));

  // Input adjacency matrix
  for (int i = 0; i < V; i++) {
    for (int j = 0; j < V; j++) {
      cin >> dist[i][j];
      if (dist[i][j] == -1) // -1 means no edge
        dist[i][j] = INF;
    }
  }

  // Floyd-Warshall Algorithm
  for (int k = 0; k < V; k++) {
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        if (dist[i][k] < INF && dist[k][j] < INF)
          dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
      }
    }
  }

  // Detect Negative Cycle
  for (int i = 0; i < V; i++) {
    if (dist[i][i] < 0) {
      cout << "Negative cycle detected\n";
      return 0;
    }
  }

  // Print shortest distance matrix
  cout << "All Pairs Shortest Path Matrix:\n";
  for (int i = 0; i < V; i++) {
    for (int j = 0; j < V; j++) {
      if (dist[i][j] == INF)
        cout << "INF ";
      else
        cout << dist[i][j] << " ";
    }
    cout << endl;
  }

  return 0;
}
