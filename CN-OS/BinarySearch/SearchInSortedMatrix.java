public class SearchInSortedMatrix {
    public static boolean binarySearch(int[] arr, int low, int high, int target){
        while(low <= high){
            int mid = low + (high - low) / 2;
            if(arr[mid] == target) return true;
            else if(arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }
    public static boolean searchInMatrix(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        for(int i = 0 ; i < n ; i++){
            if(binarySearch(matrix[i], 0, m - 1, target)) 
                return true;
        }
        return false;
    }
    public static boolean searchInMatrixOptimal(int[][] matrix, int target)  {
        int n = matrix.length, m = matrix[0].length;

        int low = 0, high = n * m - 1;
        while(low <= high){
            int mid = low + (high - low) / 2;

            int row = mid / m;
            int col = mid % m;

            if(matrix[row][col] == target) return true;
            else if(matrix[row][col] < target) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }
    public static void main(String[] args){
        int[][] matrix = {{1,3,5}, {7,9,11}, {13, 15, 17}};

        System.out.println(searchInMatrixOptimal(matrix, 9)); 
        System.out.println(searchInMatrixOptimal(matrix, 2));
        System.out.println(searchInMatrixOptimal(matrix, 17));
        System.out.println(searchInMatrixOptimal(matrix, 10));
    }

}
