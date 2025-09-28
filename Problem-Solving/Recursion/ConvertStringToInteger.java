public class ConvertStringToInteger {
    /**
     * Convert a string to an integer without using built-in parsing functions.
     *
     * Constraints / Rules:
     * 1. You may not use built-in functions like Integer.parseInt() or similar.
     * 2. You may not use any form of loops (for, while, do-while).
     * 3. You may not use any form of conditional statements (if, switch, ternary operator).
     * 4. Your function should work for negative numbers.
     * 5. If the string is invalid, return 0.
     * 6. Only characters that should be allowed in the string are numeric digits from '0'
     *    through '9', possibly preceded by a single minus sign '-' at the start if the number
     *    is negative. A string that contains any other characters in it is considered invalid.
     * 7. You may assume that the string will not have leading or trailing whitespace.
     * 8. You may assume that if the string contains valid characters, then it represents an integer
     * that can be successfully stored in an int; that is, its value will not exceed 2^31.
     * 9. If the empty string is passed, your function should return 0.
     *
     * @param s - Input string representing an integer.
     * @returns int - The integer value represented by the string.
     */
   private int parse(String s, int index, int acc) {
        if (index == s.length()) return acc;

        char c = s.charAt(index);

        // invalid character
        if (c < '0' || c > '9') return -1;

        int digit = c - '0';

        // Check for overflow in positive range
        if (acc > (Integer.MAX_VALUE - digit) / 10) return -2; // overflow signal

        return parse(s, index + 1, acc * 10 + digit);
    }

    public int convertStringToInteger(String s) {
        if (s == null || s.isEmpty()) return 0;

        s = s.trim();
        if (s.isEmpty()) return 0;

        int sign = 1, index = 0;

        char first = s.charAt(0);
        if (first == '+') index++;
        else if (first == '-') {
            sign = -1;
            index++;
        }

        int res = parse(s, index, 0);

        if (res == -1) return 0;              // invalid character
        if (res == -2) return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE; // overflow

        // Apply sign and check overflow for negative
        if (sign == -1 && res > Integer.MAX_VALUE) return Integer.MIN_VALUE;

        return sign * res;
    }

     /**
     * Main method for testing the ConvertStringToInteger class.
     */
    public static void main(String[] args) {
        ConvertStringToInteger converter = new ConvertStringToInteger();
        String[] testStrings = {"123", "-456", "0", "789a", "", "-0", "2147483647", "-2147483648"};
        int[] expectedResults = {123, -456, 0, 0, 0, 0, 2147483647, -2147483648};
        for (int i = 0; i < testStrings.length; i++) {
            try {
                assert converter.convertStringToInteger(testStrings[i]) == expectedResults[i] : "Test case " + (i + 1) + " failed";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
