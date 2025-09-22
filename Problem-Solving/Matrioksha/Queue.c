// IMPLEMENT A QUEUE IN C 

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define INITIAL_CAPACITY 4
typedef struct {
    int *items;
    int front;
    int rear;
    int size;
    int capacity;
} Queue;

void initQueue(Queue *q) {
    q->capacity = INITIAL_CAPACITY;
    q->items = (int*)malloc(q->capacity * sizeof(int));
    q->front = 0;
    q->rear = -1;
    q->size = 0;
}

bool isEmpty(Queue *q) {
    return q->size == 0;
}

void resize(Queue *q) {
    int newCapacity = q->capacity * 2;
    int *newItems = (int*)malloc(newCapacity * sizeof(int));

    // Copy old elements in proper order
    for (int i = 0; i < q->size; i++) {
        newItems[i] = q->items[(q->front + i) % q->capacity];
    }

    free(q->items);
    q->items = newItems;
    q->front = 0;
    q->rear = q->size - 1;
    q->capacity = newCapacity;

    printf("Queue resized to capacity: %d\n", q->capacity);
}

void shrink(Queue *q) {
    if (q->capacity <= INITIAL_CAPACITY) return;

    int newCapacity = q->capacity / 2;
    if (q->size > newCapacity) return; // Only shrink when size is small enough

    int *newItems = (int*)malloc(newCapacity * sizeof(int));

    for (int i = 0; i < q->size; i++) {
        newItems[i] = q->items[(q->front + i) % q->capacity];
    }

    free(q->items);
    q->items = newItems;
    q->front = 0;
    q->rear = q->size - 1;
    q->capacity = newCapacity;

    printf("Queue shrunk to capacity: %d\n", q->capacity);
}

void enqueue(Queue *q, int val) {
    if (q->size == q->capacity) resize(q);

    q->rear = (q->rear + 1) % q->capacity;
    q->items[q->rear] = val;
    q->size++;
}
int dequeue(Queue *q) {
    if (isEmpty(q)) {
        printf("Queue Underflow\n");
        return -1;
    }
    int val = q->items[q->front];
    q->front = (q->front + 1) % q->capacity;
    q->size--;
    shrink(q);
    return val;
}

int front(Queue *q) {
    if (isEmpty(q)) {
        printf("Queue is empty\n");
        return -1;
    }
    return q->items[q->front];
}

void display(Queue *q) {
    if (isEmpty(q)) {
        printf("Queue is empty\n");
        return;
    }
    printf("Queue elements: ");
    for (int i = 0; i < q->size; i++) {
        printf("%d ", q->items[(q->front + i) % q->capacity]);
    }
    printf("\n");
}
void freeQueue(Queue *q) {
    free(q->items);
}
int main() {
    Queue q;
    initQueue(&q);

    for (int i = 1; i <= 6; i++) enqueue(&q, i);
    display(&q);

    printf("Dequeued: %d\n", dequeue(&q));
    printf("Dequeued: %d\n", dequeue(&q));
    display(&q);

    printf("Front element: %d\n", front(&q));

    freeQueue(&q);
    return 0;
}
