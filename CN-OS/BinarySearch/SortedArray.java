public class SortedArray {
    public static boolean searchInRotatedArray(int[] arr, int target){
        int n = arr.length;
        int low = 0, high = n - 1;

        while(low <= high){
            int mid = low + (high - low) / 2;

            if(arr[mid] == target) return true;
            else if(arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }  
        return false;
    }
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
        int target = 1;
        System.out.println(searchInRotatedArray(arr, 1));
        System.out.println(searchInRotatedArray(arr, 2));
        System.out.println(searchInRotatedArray(arr, 6));
    }

}
