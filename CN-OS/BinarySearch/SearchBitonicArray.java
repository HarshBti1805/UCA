public class SearchBitonicArray {
    public static int searchPeakIndex(int[] arr){
        int n = arr.length;
        int low = 0 , high = n - 1;
        
        while(low <= high) {
            int mid = low + (high - low) / 2;
            
            if(mid == 0) return arr[0] > arr[1] ? 0 : 1;
            if(mid == n - 1) return arr[n - 1] > arr[n - 2] ? n - 1: n - 2;

            if(arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) 
                return mid;
            else if(arr[mid] < arr[mid + 1]) low = mid + 1;
            else high = mid - 1;
        }

        return -1;
    }
    public static int normalBinarySearch(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) low = mid + 1;   
            else high = mid - 1;
        }
        return -1;
    }

    public static int reverseBinarySearch(int[] arr, int target, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) high = mid - 1; 
            else low = mid + 1;
        }
        return -1;
    }

    public static int searchBitonic(int[] arr, int target) {
        int n = arr.length;
        int splitIndex = searchPeakIndex(arr);
        if (splitIndex == -1) 
            return normalBinarySearch(arr, target, 0, n - 1);

        int res1 = normalBinarySearch(arr, target, 0, splitIndex);
        if (res1 != -1) return res1;

        return reverseBinarySearch(arr, target, splitIndex + 1, n - 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 12, 4, 2};
        System.out.println(searchBitonic(arr, 4));   
        System.out.println(searchBitonic(arr, 12));  
        System.out.println(searchBitonic(arr, 5));   
        System.out.println(searchBitonic(arr, 1));   
    }
}

