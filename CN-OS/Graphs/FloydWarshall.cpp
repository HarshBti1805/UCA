#include <bits/stdc++.h>
using namespace std;
const int INF = 1e9;

void floydWarshall(int V, vector<vector<int>> &dist) {
  for (int k = 0; k < V; k++) {
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {

        if (dist[i][j] != INF && dist[k][j] != INF) {
          dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
        }
      }
    }
  }

  for (int i = 0; i < V; i++) {
    for (int j = 0; j < V; j++) {
      if (dist[i][j] == INF)
        cout << "INF" << "\t";
      else
        cout << dist[i][j] << "\t";
    }
    cout << endl;
  }
}
int main() {
  int V = 4;
  vector<vector<int>> graph = {
      {0, 5, INF, 10}, {INF, 0, 3, INF}, {INF, INF, 0, 1}, {INF, INF, INF, 0}};

  floydWarshall(V, graph);

  return 0;
}
