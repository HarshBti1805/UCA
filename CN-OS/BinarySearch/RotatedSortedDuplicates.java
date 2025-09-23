public class RotatedSortedDuplicates {
    public static int searchInRotated(int[] arr, int target) {
        int n = arr.length;
        int low = 0, high = n - 1;

        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] == target) return mid;

            if(arr[low] == arr[mid] && arr[mid] == arr[high]) {
                low++;
                high--;
                continue;
            }
            if(arr[low] <= arr[mid]) {
                if(arr[low] <= target && target < arr[mid]) 
                    high = mid - 1;
                else low = mid + 1;
            }
            else {
                if(arr[mid] < target && target <= arr[high]) 
                    low = mid + 1;
                else high = mid - 1;
            }
        }
        return -1;

    }
    public static void main(String[] args) {
        int[] arr = {2,5,6,0,0,1,2};
        System.out.println(searchInRotated(arr, 0));
        System.out.println(searchInRotated(arr, 0));
        
        int[] arr2 = {2,2,2,3,4,2};
        System.out.println(searchInRotated(arr2, 3));
        System.out.println(searchInRotated(arr2, 5));
    }


}
