#include <bits/stdc++.h>
using namespace std;
const int INF = 1e9 + 7;
void wallsAndGates(vector<vector<int>>& arr){
    int n = arr.size(), m = arr[0].size();
    vector<vector<int>> visited(n, vector<int>(m,0));
    queue<pair<int,pair<int,int>>> q;
    for(int i = 0 ; i < n ; i++){
        for(int j = 0 ; j < m ; j++){
            if(arr[i][j] == 0) {
                q.push({0, {i, j}});
                visited[i][j] = 1;
            }
        }
    }
    cout << "Q Size : " <<  q.size() << endl;
    int delRow[] = {-1,0,1,0};
    int delCol[] = {0,1,0,-1};

    while(!q.empty()){
        auto p = q.front(); q.pop();
        int val = p.first;
        int row = p.second.first;
        int col = p.second.second; 
        for(int k = 0 ; k < 4 ; k++){
            int nrow = row + delRow[k];
            int ncol = col + delCol[k];

            if(nrow >= 0 && nrow < n && ncol >= 0 && ncol < m && arr[nrow][ncol] == INF && !visited[nrow][ncol]){
                q.push({ val + 1, {nrow, ncol}});
                visited[nrow][ncol] = 1;
                arr[nrow][ncol] = val + 1;
            }
        }
    }
}

int main(){
    vector<vector<int>> arr = {
            {INF, -1, 0, INF}, 
            {INF, INF, INF, -1}, 
            {INF, -1, INF, -1}, 
            {0 , -1, INF, INF}
        };
    wallsAndGates(arr);
    for(int i = 0 ; i < arr.size(); i++){
        for(int j = 0 ; j < arr[0].size(); j++) cout << arr[i][j] << " ";
        cout << endl;
    }

    return 0;

}
