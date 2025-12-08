#include <climits>
#include <iostream>
#include <vector>

using namespace std;

struct Edge {
  int src;
  int dst;
  int weight;
}

void bellmanFord(int V, int src, vector<Edge>& edges){
  vector<int> dist(V, INT_MAX);
  dist[src] = 0;

  for (int i = 0; i < V - 1; i++) {
    for (const auto edge : edges) {
      int u = edge.src;
      int v = edge.dst;
      int w = edge.weight;

      if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {
        dist[v] = dist[u] + w;
      }
    }
  }

  for (const auto &edge : edges) {
    int u = edge.src;
    int v = edge.dst;
    int w = edge.weight;

    if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {
      return 0;
    }
  }

  for (int i = 0; i < V; i++) {
    if (dist[i] == INT_MAX)
      cout << -1 << " ";
    else
      cout << dist[i] << " ";
  }

  return;
}

int main() {
  int V, E;
  cin >> V >> E;
  return 0;
}
