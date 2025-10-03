#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void swap(void *a, void *b, size_t size) {
  char temp[size];
  memcpy(temp, a, size);
  memcpy(a, b, size);
  memcpy(b, temp, size);
}

int partition(void *arr, int low, int high, size_t size,
              int (*cmp)(const void *, const void *)) {
  char *array = (char *)arr;
  void *pivot = array + high * size;
  int i = low - 1;

  for (int j = low; j < high; j++) {
    // Using function pointer cmp to compare elements
    if (cmp(array + j * size, pivot) <= 0) {
      i++;
      swap(array + i * size, array + j * size, size);
    }
  }
  swap(array + (i + 1) * size, array + high * size, size);
  return i + 1;
}
void quickSort(void *arr, int low, int high, size_t size,
               int (*cmp)(const void *, const void *)) {
  if (low < high) {
    int pivot = partition(arr, low, high, size, cmp);
    quickSort(arr, low, pivot - 1, size, cmp);
    quickSort(arr, pivot + 1, high, size, cmp);
  }
}

// comparing the integer elements
int compareInt(const void *a, const void *b) {
  int x = *(int *)a;
  int y = *(int *)b;
  return x - y;
}

// comparing the floating elements
int compareFloat(const void *a, const void *b) {
  float x = *(float *)a;
  float y = *(float *)b;
  if (x < y)
    return -1;
  if (x > y)
    return 1;
  return 0;
}

// comparing the string elements using char**
int compareString(const void *a, const void *b) {
  char *s1 = *(char **)a;
  char *s2 = *(char **)b;
  return strcmp(s1, s2);
}

int main() {

  // quickSort on integer arrays
  int arr[] = {8, 7, 6, 5, 4, 3, 2, 1};
  int n = sizeof(arr) / sizeof(arr[0]);

  int (*cmpPtr)(const void *, const void *);
  cmpPtr = compareInt;

  quickSort(arr, 0, n - 1, sizeof(int), cmpPtr);
  for (int i = 0; i < n; i++)
    printf("%d ", arr[i]);
  printf("\n");

  // quickSort on floating numbers
  float farr[] = {12.4, 21.4, 15.55, 15.24, 10.2};
  int m = sizeof(farr) / sizeof(farr[0]);
  void *memcpy(void *dest, const void *src, size_t n);
  cmpPtr = compareFloat;

  quickSort(farr, 0, m - 1, sizeof(float), cmpPtr);
  for (int i = 0; i < m; i++)
    printf("%.2f ", farr[i]);
  printf("\n");

  // quickSort on string array
  char *stringArr[] = {"banana", "apple", "orange", "grape"};
  char *strArr[] = {"bac", "cba", "abc", "bca"};
  int size = sizeof(stringArr) / sizeof(stringArr[0]);
  int s = sizeof(strArr) / sizeof(strArr[0]);

  cmpPtr = compareString;

  quickSort(stringArr, 0, size - 1, sizeof(char *), cmpPtr);
  quickSort(strArr, 0, s - 1, sizeof(char *), cmpPtr);

  for (int i = 0; i < size; i++)
    printf("%s\n", stringArr[i]);

  printf("\n");

  for (int i = 0; i < s; i++)
    printf("%s\n", strArr[i]);

  return 0;
}
