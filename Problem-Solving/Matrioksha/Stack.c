// IMPLEMEMENT A STACK IN C 
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define INITIAL_CAPACITY 4
typedef struct {
    int *items;
    int top;
    int capacity;

} Stack; 
void initStack(Stack *st){
    st->capacity = INITIAL_CAPACITY;
    st->items = (int*)malloc(st->capacity * sizeof(int));
    st->top = -1;
}
void resize(Stack* st){
    st->capacity *= 2;
    st->items = (int*) realloc(st->items, st->capacity * sizeof(int));
    printf("Stack resized to capacity : %d\n", st->capacity);
}
bool isEmpty(Stack *st) {
    return st->top == -1;
}
void push(Stack *st, int val){
    if(st->top + 1 == st->capacity) resize(st);
    st->items[++st->top] = val;
}
int size(Stack *st){
    if(isEmpty(st)) return 0;
    return st->top + 1;
}
int peek(Stack *st){
    if(isEmpty(st)) {
        printf("Stack is empty\n");
        return -1;
    }
    return st->items[st->top];
}

int pop(Stack *st){
    if(isEmpty(st)){
        printf("Stack underflow\n");
        return -1;
    }
    int res = st->items[st->top--];
    // reclaiming memory
    if(st->top + 1 <= st->capacity / 2 && st->capacity > INITIAL_CAPACITY) {
        st->capacity = 3 * st->capacity / 2;
        st->items = (int*)realloc(st->items, st->capacity * sizeof(int));
    }
    return res;
}
void freeStack(Stack *st){
    free(st->items);
}
void display(Stack *st){
    if(isEmpty(st)) {
        printf("Stack is empty");
        return ;
    }
    for(int i = st->top ; i >= 0 ; i--) printf("%d ", st->items[i]);
    printf("\n");
}
int main(){
    Stack st;
    initStack(&st);

    int s = size(&st);

    for(int i = 1 ; i <= 5 ; i++) push(&st, i);
    display(&st);
    int ele = pop(&st);
    display(&st);

    freeStack(&st);
    return 0;
}
