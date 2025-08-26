// // Given a graph, find the min distance from node 1 to N. You can select one edge and make its weight equal to the smallest integer greater than or equal to the square root of the weight.

// // ORIGINAL SOLUTION 
// #include <bits/stdc++.h>
// using namespace std;
// using ll = long long int;
// using pii = pair<ll,ll>;
// ll shortestDistanceWithTransformation(vector<vector<ll>>& edges, ll n){
//     vector<vector<pii>> adj(n + 1);
//     for(auto &edge : edges){
//         ll u = edge[0], v = edge[1], w = edge[2];
//         adj[u].push_back({v, w}); 
//         adj[v].push_back({u, w}); 
//     }

//     vector<vector<ll>> dist(n + 1, vector<ll>(2, LLONG_MAX));
//     priority_queue<pair<ll,pii>, vector<pair<ll,pii>>, greater<pair<ll,pii>>> pq;
//     dist[1][0] = 0;
//     pq.push({0, {1, 0}});

//     while(!pq.empty()){
//         ll currentDist = pq.top().first;
//         ll u = pq.top().second.first;
//         ll used = pq.top().second.second;
//         pq.pop(); 
//         if(currentDist > dist[u][used]) continue;

//         for(auto it : adj[u]){
//             ll v = it.first;
//             ll edgeWeight = it.second;  

//             // CASE 1: Use edge normally (without transformation)
//             ll newDist = currentDist + edgeWeight;
//             if(newDist < dist[v][used]){
//                 dist[v][used] = newDist;
//                 pq.push({newDist, {v, used}});
//             }
            
//             // CASE 2: Transform this edge (only if transformation not used yet)
//             if(used == 0){
//                 ll transformedWeight = (ll)ceil(sqrt((long double)edgeWeight));
//                 ll newDistTransformed = currentDist + transformedWeight;
//                 if(newDistTransformed < dist[v][1]){
//                     dist[v][1] = newDistTransformed;
//                     pq.push({newDistTransformed, {v, 1}});
//                 }
//             }
//         }
//     }
//     return min(dist[n][0], dist[n][1]);
// }
// int main(){
//     ios::sync_with_stdio(false);
//     cin.tie(nullptr);

//     ll n, m; 
//     cin >> n >> m;   
//     vector<vector<ll>> edges(m, vector<ll>(3));
    
//     for(ll i = 0; i < m; i++) cin >> edges[i][0] >> edges[i][1] >> edges[i][2];
//     cout << shortestDistanceWithTransformation(edges, n) << "\n";
//     return 0;
// }





// **********************************************************************************




// Given a graph, find the min distance from node 1 to N. You can select one edge and make its weight equal to the smallest integer greater than or equal to the square root of the weight.

// ORIGINAL SOLUTION 
#include <bits/stdc++.h>
using namespace std;
using ll = long long int;
using pii = pair<ll,ll>;
vector<ll> dijkstra(ll n, int src, vector<vector<pii>> adj){
    vector<ll> dist(n + 1, LLONG_MAX);
    dist[src] = 0;

    priority_queue<pii,vector<pii>,greater<pii>> pq;
    pq.push({0,src});

    while(!pq.empty()){
        ll wt = pq.top().first;
        ll u = pq.top().second;

        pq.pop();

        for(auto it : adj[u]){
            ll v = it.first;
            ll edgeWeight = it.second;

            if(dist[v] > edgeWeight + wt){
                dist[v] = edgeWeight + wt;
                pq.push({dist[v], v});
            }
        }
    }
    return dist;

}
ll shortestDistanceWithTransformation(vector<vector<ll>>& edges, ll n){
    vector<vector<pii>> adj(n + 1);
    for(auto &edge : edges){
        ll u = edge[0], v = edge[1], w = edge[2];
        adj[u].push_back({v, w}); 
        adj[v].push_back({u, w}); 
    }
    vector<ll> distFromOne = dijkstra(n, 1, adj);
    vector<ll> distFromEnd = dijkstra(n, n, adj);
    
    ll mini = LLONG_MAX;
    for(auto {u,v,wt} : edges){ 
        int newDistance = ceil(sqrt(wt));
        if(distFromOne[u] != LLONG_MAX && distFromEnd[v] != LLONG_MAX)  
            mini = min(mini, distFromOne[u] + newDistance + distFromEnd[v]);
            
    }
    



}
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    ll n, m; 
    cin >> n >> m;   
    vector<vector<ll>> edges(m, vector<ll>(3));
    
    for(ll i = 0; i < m; i++) cin >> edges[i][0] >> edges[i][1] >> edges[i][2];
    cout << shortestDistanceWithTransformation(edges, n) << "\n";
    return 0;
}