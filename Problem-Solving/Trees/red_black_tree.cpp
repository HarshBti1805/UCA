#include <iostream>
using namespace std;

enum Color { RED, BLACK };

struct Node {
  int data;
  Color color;
  Node *left, *right, *parent;

  Node(int data) : data(data) {
    parent = left = right = nullptr;
    color = RED;
  }
};

class RedBlackTree {
private:
  Node *root;

  void rotateLeft(Node *&x) {
    Node *y = x->right;
    x->right = y->left;
    if (y->left)
      y->left->parent = x;
    y->parent = x->parent;

    if (!x->parent)
      root = y;
    else if (x == x->parent->left)
      x->parent->left = y;
    else
      x->parent->right = y;

    y->left = x;
    x->parent = y;
  }

  void rotateRight(Node *&x) {
    Node *y = x->left;
    x->left = y->right;
    if (y->right)
      y->right->parent = x;
    y->parent = x->parent;

    if (!x->parent)
      root = y;
    else if (x == x->parent->right)
      x->parent->right = y;
    else
      x->parent->left = y;

    y->right = x;
    x->parent = y;
  }

  void fixInsert(Node *&x) {
    Node *parent = nullptr;
    Node *grandparent = nullptr;

    while (x != root && x->color == RED && x->parent->color == RED) {
      parent = x->parent;
      grandparent = parent->parent;

      // Parent is left child of grandparent
      if (parent == grandparent->left) {
        Node *uncle = grandparent->right;

        // Case 1: Uncle is red -> Recolor
        if (uncle && uncle->color == RED) {
          grandparent->color = RED;
          parent->color = BLACK;
          uncle->color = BLACK;
          x = grandparent;
        } else {
          // Case 2: x is right child -> Left rotation
          if (x == parent->right) {
            rotateLeft(parent);
            x = parent;
            parent = x->parent;
          }

          // Case 3: x is left child -> Right rotation
          rotateRight(grandparent);
          swap(parent->color, grandparent->color);
          x = parent;
        }
      }
      // Parent is right child of grandparent
      else {
        Node *uncle = grandparent->left;

        // Case 1: Uncle is red
        if (uncle && uncle->color == RED) {
          grandparent->color = RED;
          parent->color = BLACK;
          uncle->color = BLACK;
          x = grandparent;
        } else {
          // Case 2: x is left child
          if (x == parent->left) {
            rotateRight(parent);
            x = parent;
            parent = x->parent;
          }

          // Case 3: x is right child
          rotateLeft(grandparent);
          swap(parent->color, grandparent->color);
          x = parent;
        }
      }
    }
    root->color = BLACK;
  }

public:
  RedBlackTree() { root = nullptr; }

  void insert(int data) {
    Node *node = new Node(data);
    if (root == nullptr) {
      node->color = BLACK;
      root = node;
      return;
    }

    Node *parent = nullptr;
    Node *current = root;

    while (current) {
      parent = current;
      if (data < current->data)
        current = current->left;
      else if (data > current->data)
        current = current->right;
      else
        return; // No duplicates
    }

    node->parent = parent;
    if (data < parent->data)
      parent->left = node;
    else
      parent->right = node;

    fixInsert(node);
  }

  void inorder(Node *node) {
    if (node == nullptr)
      return;
    inorder(node->left);
    cout << node->data << (node->color == RED ? "(R) " : "(B) ");
    inorder(node->right);
  }

  void display() {
    inorder(root);
    cout << endl;
  }
};

int main() {
  RedBlackTree tree;
  tree.insert(10);
  tree.insert(20);
  tree.insert(30);
  tree.insert(15);
  tree.insert(25);
  tree.insert(5);

  cout << "Inorder traversal of Red-Black Tree:\n";
  tree.display();
}
