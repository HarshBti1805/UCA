#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Block {
  int start;            // Start address of the block
  int size;             // Size of the block
  int isOccupied;       // 1 if occupied, 0 if free
  char programName[20]; // Name of program (if occupied)
  struct Block *next;   // Pointer to the next block
} Block;

Block *head = NULL; // Head of the linked list

// Initialize memory with given size
void initializeMemory(int size) {
  head = (Block *)malloc(sizeof(Block));
  head->start = 0;
  head->size = size;
  head->isOccupied = 0;
  strcpy(head->programName, "");
  head->next = NULL;
}

// Display memory blocks
void displayMemory() {
  Block *temp = head;
  printf("\nMemory Layout:\n");
  printf("Start\tSize\tStatus\tProgram\n");
  while (temp != NULL) {
    printf("%d\t%d\t%s\t%s\n", temp->start, temp->size,
           temp->isOccupied ? "Occupied" : "Free",
           temp->isOccupied ? temp->programName : "-");
    temp = temp->next;
  }
}

// Allocate memory for a program
void allocateProgram(char *name, int size) {
  Block *temp = head;
  while (temp != NULL) {
    if (!temp->isOccupied && temp->size >= size) {
      if (temp->size > size) {
        // Split the block
        Block *newBlock = (Block *)malloc(sizeof(Block));
        newBlock->start = temp->start + size;
        newBlock->size = temp->size - size;
        newBlock->isOccupied = 0;
        strcpy(newBlock->programName, "");
        newBlock->next = temp->next;
        temp->next = newBlock;
      }
      temp->size = size;
      temp->isOccupied = 1;
      strcpy(temp->programName, name);
      printf("Program '%s' allocated %d units.\n", name, size);
      return;
    }
    temp = temp->next;
  }
  printf("Not enough memory to allocate program '%s'.\n", name);
}

// Deallocate a program
void deallocateProgram(char *name) {
  Block *temp = head;
  while (temp != NULL) {
    if (temp->isOccupied && strcmp(temp->programName, name) == 0) {
      temp->isOccupied = 0;
      strcpy(temp->programName, "");
      printf("Program '%s' has finished execution and memory is freed.\n",
             name);
      return;
    }
    temp = temp->next;
  }
  printf("Program '%s' not found in memory.\n", name);
}

// Defragment memory by merging adjacent free blocks
void defragmentMemory() {
  Block *temp = head;
  while (temp != NULL && temp->next != NULL) {
    if (!temp->isOccupied && !temp->next->isOccupied) {
      temp->size += temp->next->size;
      Block *toDelete = temp->next;
      temp->next = temp->next->next;
      free(toDelete);
    } else {
      temp = temp->next;
    }
  }
  printf("Memory defragmentation complete.\n");
}

int main() {
  int memorySize;
  printf("Enter total memory size: ");
  scanf("%d", &memorySize);
  initializeMemory(memorySize);

  int choice;
  char name[20];
  int size;

  while (1) {
    printf("\n1. Display Memory\n2. Allocate Program\n3. Deallocate "
           "Program\n4. Defragment Memory\n5. Exit\nEnter choice: ");
    scanf("%d", &choice);

    switch (choice) {
    case 1:
      displayMemory();
      break;
    case 2:
      printf("Enter program name: ");
      scanf("%s", name);
      printf("Enter program size: ");
      scanf("%d", &size);
      allocateProgram(name, size);
      break;
    case 3:
      printf("Enter program name to deallocate: ");
      scanf("%s", name);
      deallocateProgram(name);
      break;
    case 4:
      defragmentMemory();
      break;
    case 5:
      printf("Exiting.\n");
      return 0;
    default:
      printf("Invalid choice.\n");
    }
  }

  return 0;
}
