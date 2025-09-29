/**
 * Calculates the total Hamming distance between all pairs of integers in the array.
 * The Hamming distance between two integers is the number of bit positions in which they differ.
 * @param nums an array of non-negative integers
 * @return the total Hamming distance between all unique pairs in the array
 */
public class TotalHammingDistance {

    public static int totalHammingDistance(int[] nums) {
        int n = nums.length;
        int total = 0;

        for(int bit = 0 ; bit < 32 ; bit++){
            int countOnes = 0;
            for(int j : nums){
                if(((j >> bit) & 1) == 1) countOnes++;
            }
            int countZeroes = n - countOnes;
            total += (countOnes * countZeroes);
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("Expected 8: " + totalHammingDistance(new int[]{4, 14, 4, 14}));
        System.out.println("Expected 4: " + totalHammingDistance(new int[]{1, 2, 3}));
        System.out.println("Expected 0: " + totalHammingDistance(new int[]{0, 0, 0}));
    }
}


