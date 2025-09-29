import java.util.Arrays;

/**
 * Given an array where every element appears exactly twice except for two unique elements,
 * find those two unique elements.
 * Key constraints: The array contains exactly two unique elements that appear only once,
 * and all other elements appear exactly twice.
 * @param nums an array of integers
 * @return an integer array of length 2 containing the two non-repeating elements
 */
public class TwoNonRepeatingElements {

    public static int[] findTwoUnique(int[] nums) {
        // method stub
        int xor = 0;
        for(int i : nums) xor ^= i;

        int diff = xor & -xor;
        int a = 0 , b = 0;

        for(int i : nums) {
            if((i & diff) == 0) a ^= i;
            else b ^= i;
        }

        return new int[]{b, a};
    }

    public static void main(String[] args) {
        int[] result1 = findTwoUnique(new int[]{1, 2, 3, 2, 1, 4});
        System.out.println("Expected [3, 4]: " + Arrays.toString(result1));
        int[] result2 = findTwoUnique(new int[]{2, 2, 3, 5});
        System.out.println("Expected [3, 5]: " + Arrays.toString(result2));
        int[] result3 = findTwoUnique(new int[]{0, 0, -1, -1, 9, 7});
        System.out.println("Expected [7, 9]: " + Arrays.toString(result3));
    }
}

