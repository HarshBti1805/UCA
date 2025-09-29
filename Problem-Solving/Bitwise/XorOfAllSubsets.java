/**
 * Given an array of integers, compute the XOR of XORs of all possible subsets of the array.
 * Key constraints: If the array has more than one element, the result is 0
 * (since each bit appears in an even number of subset XORs).
 * @param nums an array of integers
 * @return the XOR of the XOR of all subsets
 */
public class XorOfAllSubsets {

    public static int xorOfAllSubsets(int[] nums) {
        if(nums.length == 1) return nums[0];
        return 0; // for length >= 2
    }

    public static void main(String[] args) {
        System.out.println("Expected 5: " + xorOfAllSubsets(new int[]{5}));
        System.out.println("Expected 0: " + xorOfAllSubsets(new int[]{1, 2}));
        System.out.println("Expected 0: " + xorOfAllSubsets(new int[]{1, 2, 3}));
    }
}



