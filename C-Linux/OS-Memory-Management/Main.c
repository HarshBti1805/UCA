#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TOTAL_MEMORY 1000

// Structure for memory block
typedef struct MemoryBlock {
  int start;
  int size;
  int process_id;
  char process_name[50];
  int is_allocated;
  struct MemoryBlock *next;
} MemoryBlock;

// Global head pointer
MemoryBlock *head = NULL;

// Function to initialize memory with one free block
void initialize_memory() {
  head = (MemoryBlock *)malloc(sizeof(MemoryBlock));
  head->start = 0;
  head->size = TOTAL_MEMORY;
  head->process_id = -1;
  strcpy(head->process_name, "FREE");
  head->is_allocated = 0;
  head->next = NULL;
  printf("Memory initialized: %d units available\n\n", TOTAL_MEMORY);
}

// Function to display current memory state
void display_memory() {
  MemoryBlock *temp = head;
  int free_space = 0, allocated_space = 0, fragments = 0;
  int prev_allocated = -1;

  printf("\n========== MEMORY STATE ==========\n");
  printf("%-10s %-10s %-10s %-20s\n", "Start", "Size", "Status", "Process");
  printf("---------------------------------------------------\n");

  while (temp != NULL) {
    printf("%-10d %-10d %-10s %-20s\n", temp->start, temp->size,
           temp->is_allocated ? "ALLOCATED" : "FREE", temp->process_name);

    if (temp->is_allocated) {
      allocated_space += temp->size;
      if (prev_allocated == 0)
        fragments++;
    } else {
      free_space += temp->size;
      if (prev_allocated == 1)
        fragments++;
    }
    prev_allocated = temp->is_allocated;
    temp = temp->next;
  }

  printf("===================================\n");
  printf("Total Memory: %d | Free: %d | Allocated: %d\n", TOTAL_MEMORY,
         free_space, allocated_space);
  printf("Fragmentation: %d fragment(s)\n", fragments > 0 ? fragments : 0);
  printf("===================================\n\n");
}

// Function to allocate memory using first-fit algorithm
int allocate_memory(int process_id, char *process_name, int size) {
  if (size <= 0 || size > TOTAL_MEMORY) {
    printf("ERROR: Invalid memory size requested!\n\n");
    return 0;
  }

  MemoryBlock *temp = head;

  // Find first free block that fits
  while (temp != NULL) {
    if (!temp->is_allocated && temp->size >= size) {
      // Allocate memory in this block
      if (temp->size > size) {
        // Split the block
        MemoryBlock *new_block = (MemoryBlock *)malloc(sizeof(MemoryBlock));
        new_block->start = temp->start + size;
        new_block->size = temp->size - size;
        new_block->process_id = -1;
        strcpy(new_block->process_name, "FREE");
        new_block->is_allocated = 0;
        new_block->next = temp->next;
        temp->next = new_block;
      }

      // Update current block
      temp->size = size;
      temp->process_id = process_id;
      strcpy(temp->process_name, process_name);
      temp->is_allocated = 1;

      printf("SUCCESS: Process '%s' (PID: %d) allocated %d units at position "
             "%d\n\n",
             process_name, process_id, size, temp->start);
      return 1;
    }
    temp = temp->next;
  }

  printf("FAILED: Not enough contiguous memory for process '%s' (PID: %d)\n",
         process_name, process_id);
  printf("Requested: %d units. Consider defragmentation.\n\n", size);
  return 0;
}

// Function to deallocate memory
int deallocate_memory(int process_id) {
  MemoryBlock *temp = head;

  while (temp != NULL) {
    if (temp->is_allocated && temp->process_id == process_id) {
      printf("Process '%s' (PID: %d) has completed execution.\n",
             temp->process_name, temp->process_id);
      printf("Freeing %d units of memory at position %d\n\n", temp->size,
             temp->start);

      temp->is_allocated = 0;
      temp->process_id = -1;
      strcpy(temp->process_name, "FREE");

      // Merge with next block if it's free
      if (temp->next != NULL && !temp->next->is_allocated) {
        MemoryBlock *next_block = temp->next;
        temp->size += next_block->size;
        temp->next = next_block->next;
        free(next_block);
      }

      return 1;
    }
    temp = temp->next;
  }

  printf("ERROR: Process with PID %d not found!\n\n", process_id);
  return 0;
}

// Function to defragment memory
void defragment_memory() {
  printf("Starting defragmentation...\n");

  MemoryBlock *temp = head;
  int current_position = 0;
  MemoryBlock *new_head = NULL;
  MemoryBlock *new_tail = NULL;

  // First pass: collect all allocated blocks
  while (temp != NULL) {
    if (temp->is_allocated) {
      MemoryBlock *new_block = (MemoryBlock *)malloc(sizeof(MemoryBlock));
      new_block->start = current_position;
      new_block->size = temp->size;
      new_block->process_id = temp->process_id;
      strcpy(new_block->process_name, temp->process_name);
      new_block->is_allocated = 1;
      new_block->next = NULL;

      if (new_head == NULL) {
        new_head = new_block;
        new_tail = new_block;
      } else {
        new_tail->next = new_block;
        new_tail = new_block;
      }

      current_position += temp->size;
    }
    temp = temp->next;
  }

  // Add remaining free space as one block
  if (current_position < TOTAL_MEMORY) {
    MemoryBlock *free_block = (MemoryBlock *)malloc(sizeof(MemoryBlock));
    free_block->start = current_position;
    free_block->size = TOTAL_MEMORY - current_position;
    free_block->process_id = -1;
    strcpy(free_block->process_name, "FREE");
    free_block->is_allocated = 0;
    free_block->next = NULL;

    if (new_head == NULL) {
      new_head = free_block;
    } else {
      new_tail->next = free_block;
    }
  }

  // Free old memory structure
  temp = head;
  while (temp != NULL) {
    MemoryBlock *to_free = temp;
    temp = temp->next;
    free(to_free);
  }

  head = new_head;
  printf("Defragmentation complete! All free space consolidated.\n\n");
}

// Function to display menu
void display_menu() {
  printf("========== MEMORY MANAGEMENT SYSTEM ==========\n");
  printf("1. Allocate Memory to Process\n");
  printf("2. Deallocate Memory (Process Complete)\n");
  printf("3. Display Memory State\n");
  printf("4. Defragment Memory\n");
  printf("5. Exit\n");
  printf("==============================================\n");
  printf("Enter your choice: ");
}

int main() {
  int choice, process_id, size;
  char process_name[50];

  initialize_memory();

  while (1) {
    display_menu();
    scanf("%d", &choice);
    getchar(); // Consume newline

    switch (choice) {
    case 1:
      printf("\nEnter Process ID: ");
      scanf("%d", &process_id);
      getchar();
      printf("Enter Process Name: ");
      fgets(process_name, 50, stdin);
      process_name[strcspn(process_name, "\n")] = 0; // Remove newline
      printf("Enter Memory Size Required: ");
      scanf("%d", &size);
      getchar();
      printf("\n");
      allocate_memory(process_id, process_name, size);
      break;

    case 2:
      printf("\nEnter Process ID to deallocate: ");
      scanf("%d", &process_id);
      getchar();
      printf("\n");
      deallocate_memory(process_id);
      break;

    case 3:
      display_memory();
      break;

    case 4:
      printf("\n");
      defragment_memory();
      display_memory();
      break;

    case 5:
      printf("\nExiting Memory Management System...\n");

      // Free all memory
      MemoryBlock *temp = head;
      while (temp != NULL) {
        MemoryBlock *to_free = temp;
        temp = temp->next;
        free(to_free);
      }

      return 0;

    default:
      printf("\nInvalid choice! Please try again.\n\n");
    }
  }

  return 0;
}
