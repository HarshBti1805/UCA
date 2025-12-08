class Solution {
public:
  vector<bool> checkIfPrerequisite(int V, vector<vector<int>> &prerequisites,
                                   vector<vector<int>> &queries) {
    vector<vector<bool>> dist(V, vector<bool>(V, false));

    for (auto &it : prerequisites)
      dist[it[0]][it[1]] = true;

    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (dist[i][k] && dist[k][j])
            dist[i][j] = true;
        }
      }
    }
    vector<bool> res;
    for (vector<int> q : queries)
      res.push_back(dist[q[0]][q[1]]);

    return res;
  }
};
