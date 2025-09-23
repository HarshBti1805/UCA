public class RotatedSorted {
    public static int searchInRotatedArray(int[] arr, int target){
        int n = arr.length;
        int low = 0, high = n - 1;

        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] == target) return mid;
            if(arr[low] <= arr[mid]){
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
    
    public static void main(String[] args){
        int[] arr = {4,5,6,7,0,1,2};
        System.out.println(searchInRotatedArray(arr, 4));
        System.out.println(searchInRotatedArray(arr, 8));
        System.out.println(searchInRotatedArray(arr, 7));
        System.out.println(searchInRotatedArray(arr, 2));
    }

}
