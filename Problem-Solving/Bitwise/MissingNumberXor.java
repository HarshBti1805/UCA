/**
 * Finds the missing number in an array containing all distinct numbers from 0 to n inclusive with one missing.
 * Key constraints: The array size is n, containing numbers from 0 to n with exactly one missing.
 * @param nums an array containing distinct integers from 0 to n with exactly one missing
 * @return the missing integer in the range [0, n]
 */
public class MissingNumberXor {

    public static int findMissingNumber(int[] nums) {
        // method stub

        int n = nums.length , xor = 0;

        for(int i = 0 ; i <= n ; i++) xor ^= i;
        for(int num : nums) xor ^= num;

        return xor;
    }

    public static void main(String[] args) {
        System.out.println("Expected 2: " + findMissingNumber(new int[]{3, 0, 1}));
        System.out.println("Expected 3: " + findMissingNumber(new int[]{0, 1, 2, 4}));
        System.out.println("Expected 7: " + findMissingNumber(new int[]{1, 0, 4, 3, 2, 6, 5}));
    }
}



