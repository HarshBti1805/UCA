/**
 * Finds the maximum XOR of any two elements in the array.
 * Key constraints: The array contains at least two non-negative integers.
 * @param nums an array of non-negative integers
 * @return the maximum XOR value of any two distinct elements in the array
 */
import java.util.*;
public class MaximumXorInArray {

    public static int findMaximumXor(int[] nums) {
        // method stub
        // int n = nums.length;
        // int maxi = Integer.MIN_VALUE;
        // for(int i = 0 ; i < n ; i++){
        //     for(int j = i + 1 ; j < n ; j++){
        //        int xor = nums[i] ^ nums[j];
        //        maxi = Math.max(maxi, xor);
        //    }
        // }

        int maxi = 0 , mask = 0;

        for(int bit = 31; bit >= 0 ; bit--){
            mask |= (1 << bit);

            HashSet<Integer> prefixes = new HashSet<>();
            for(int num : nums) prefixes.add(num & mask);


            int candidate = maxi | (1 << bit);

            for(int prefix : prefixes) {
                if(prefixes.contains(candidate ^ prefix)) {
                    maxi = candidate;
                    break;
                }
            }

        }
        return maxi;
    }

    public static void main(String[] args) {
        System.out.println("Expected 126: " + findMaximumXor(new int[]{26, 100, 25, 13, 4, 14}));
        System.out.println("Expected 7: " + findMaximumXor(new int[]{1, 2, 3, 4, 5, 6, 7}));
        System.out.println("Expected 0: " + findMaximumXor(new int[]{0, 0, 0}));
    }
}



