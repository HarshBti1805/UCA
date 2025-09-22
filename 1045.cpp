// // PAINTING WITH TWO COLORS 
// #include <iostream>
// using namespace std;
// using ll = long long int;
// int main() {
//     ios_base::sync_with_stdio(false);
//     cin.tie(NULL);
//     ll t; cin >> t;
//     while (t--) {
//         ll n, a, b;
//         cin >> n >> a >> b;
//         if ((n % 2) != (b % 2)) {
//             cout << "NO\n";
//             continue;
//         }
//         if ((n % 2) == (a % 2)) {
//             cout << "YES\n";
//             continue;
//         }
//         if (a + 1 <= b) cout << "YES" << endl;
//         else cout << "NO" << endl;
//     }
//     return 0;
// }


// ADD 0 OR K 
#include <bits/stdc++.h>
using namespace std;
using ll = long long int;
ll power(ll base, ll exp, ll mod) {
    ll res = 1;
    base %= mod;
    while (exp > 0) {
        if (exp % 2 == 1) res = (__int128)res * base % mod;
        base = (__int128)base * base % mod;
        exp /= 2;
    }
    return res;
}

ll modInverse(ll n, ll mod) {
    return power(n, mod - 2, mod);
}

void solve() {
    ll n, k;
    cin >> n >> k;
    vector<ll> a(n);
    for (int i = 0; i < n; ++i) cin >> a[i];
    ll p = -1;
    vector<int> small_primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
    for (int prime : small_primes) {
        if (k % prime != 0) {
            p = prime;
            break;
        }
    }
    ll k_inv = modInverse(k, p);
    for (int i = 0; i < n; ++i) {
        ll rem = a[i] % p;
        ll c_i = ((p - rem) * k_inv) % p;
        ll final_val = a[i] + c_i * k;
        cout << final_val << (i == n - 1 ? "" : " ");
    }
    cout << endl;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    ll t; cin >> t;
    while (t--) {
        solve();
    }
    return 0;
}


// using namespace std;