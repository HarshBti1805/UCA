public class InsertPosition{ 
    public static int searchInsertPosition(int[] arr, int target){
        int n = arr.length;
        int low = 0, high = n - 1;

        while(low <= high){ 
            int mid = low + (high - low) / 2;
            if(arr[mid] == target) return mid;
            if(arr[mid] < target) {
                low = mid + 1;
            }
            else high = mid - 1;
        }
        return low;

    }
    public static void main(String[] args){
        int[] arr = {1,3,5,6};
        System.out.println(searchInsertPosition(arr, 5));
        System.out.println(searchInsertPosition(arr, 2));
        System.out.println(searchInsertPosition(arr, 7));
        System.out.println(searchInsertPosition(arr, 0));
    }

}

