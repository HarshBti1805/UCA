public class XorSum {
    public static int sumOfXorSubarrays(int[] arr) {
        int n = arr.length;
        int xorSum = 0;

        for (int i = 0; i < n; i++) {
            int count = (i + 1) * (n - i);
            if (count % 2 != 0) xorSum ^= arr[i];
        }

        return xorSum;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        System.out.println(sumOfXorSubarrays(arr));
    }
}

