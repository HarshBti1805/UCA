#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TABLE_SIZE 10  // INITIAL SIZE OF HASHAMP 
typedef struct Node {  // NODE DS FOR KEY - VALUE PAIR 
    char* key; 
    int value;
    struct Node* next;
} Node;
typedef struct HashMap { // INITIAL HASHAMAP STRUCTURE 
    Node* table[TABLE_SIZE];
} HashMap;

// Hash function (djb2 algorithm)
unsigned int hash(const char* key) {
    unsigned long hash = 5381;
    int c;
    while ((c = *key++))
        hash = ((hash << 5) + hash) + c; // hash * 33 + c
    return hash % TABLE_SIZE;
}

// Create a new node
Node* create_node(const char* key, int value) {
    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->key = strdup(key);
    newNode->value = value;
    newNode->next = NULL;
    return newNode;
}

// Initialize hashmap
HashMap* create_map() {
    HashMap* map = (HashMap*)malloc(sizeof(HashMap));
    for (int i = 0; i < TABLE_SIZE; i++) {
        map->table[i] = NULL;
    }
    return map;
}

// Insert key-value pair
void insert(HashMap* map, const char* key, int value) {
    unsigned int idx = hash(key);
    Node* head = map->table[idx];

    // Check if key already exists, update value
    for (Node* curr = head; curr != NULL; curr = curr->next) {
        if (strcmp(curr->key, key) == 0) {
            curr->value = value;
            return;
        }
    }

    // Insert new node at head
    Node* newNode = create_node(key, value);
    newNode->next = head;
    map->table[idx] = newNode;
}

// Search for a key
int* search(HashMap* map, const char* key) {
    unsigned int idx = hash(key);
    Node* curr = map->table[idx];

    while (curr) {
        if (strcmp(curr->key, key) == 0)
            return &curr->value;
        curr = curr->next;
    }
    return NULL; // key not found
}

// Delete a key
void delete(HashMap* map, const char* key) {
    unsigned int idx = hash(key);
    Node* curr = map->table[idx];
    Node* prev = NULL;

    while (curr) {
        if (strcmp(curr->key, key) == 0) {
            if (prev)
                prev->next = curr->next;
            else
                map->table[idx] = curr->next;

            free(curr->key);
            free(curr);
            return;
        }
        prev = curr;
        curr = curr->next;
    }
}

// Print hashmap
void print_map(HashMap* map) {
    for (int i = 0; i < TABLE_SIZE; i++) {
        printf("[%d]: ", i);
        Node* curr = map->table[i];
        while (curr) {
            printf("(%s, %d) -> ", curr->key, curr->value);
            curr = curr->next;
        }
        printf("NULL\n");
    }
}

// Free memory
void free_map(HashMap* map) {
    for (int i = 0; i < TABLE_SIZE; i++) {
        Node* curr = map->table[i];
        while (curr) {
            Node* temp = curr;
            curr = curr->next;
            free(temp->key);
            free(temp);
        }
    }
    free(map);
}

// Example usage
int main() {
    HashMap* map = create_map();

    insert(map, "apple", 10);
    insert(map, "banana", 20);
    insert(map, "orange", 30);
    insert(map, "grape", 40);

    print_map(map);

    int* val = search(map, "banana");
    if (val) printf("Found banana: %d\n", *val);
    else printf("banana not found\n");

    delete(map, "banana");
    print_map(map);

    free_map(map);
    return 0;
}

