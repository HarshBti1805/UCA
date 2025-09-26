public class Base64 {
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] INV_ALPHABET = new int[128];
    static {
        for(int i = 0 ; i < INV_ALPHABET.length ; i++) INV_ALPHABET[i] = -1;
        for(int i = 0 ; i < ALPHABET.length; i++) INV_ALPHABET[ALPHABET[i]] = i;
    }

    public static String encode(byte[] data) {
        StringBuilder res = new StringBuilder((data.length + 2) / 3 * 4); // Ensure enough capacity is allocated
        int i = 0;
        // Since we need 3 bytes for every 4 characters :: Get the first 3 bytes in each iteration
        while (i < data.length) {
            int b0 = data[i++] & 0xFF;  // First byte
            int b1 = (i < data.length) ? data[i++] & 0xFF : 0; // Second Byte
            int b2 = (i < data.length) ? data[i++] & 0xFF : 0; // Third Byte

            int block = (b0 << 16) | (b1 << 8) | b2; // Combining the Three Bytes


            // >>> represents the unsigned right shift AND 03xF masks the last 6 bits.
            // Since we need 4 chars we do it 4 times taking 6 bits out of the total 24 bits everytime.
            // These 6 bits represents the index for the ALPHABET Array. So in the result append ALPHABET[index]
            res.append(ALPHABET[(block >>> 18) & 0x3F]);
            res.append(ALPHABET[(block >>> 12) & 0x3F]);

            res.append((i - 1) < data.length ? ALPHABET[(block >>> 6) & 0x3F] : '='); // Handle Padding
            res.append(i < data.length ? ALPHABET[block & 0x3F] : '='); // Handle Padding
        }

        // Make sure to  handle padding.
        // If 1 byte left -> pad with ==.
        // If only 2 bytes left -> pad with =.
        int mod = data.length % 3;
        if (mod == 1) {
            res.setCharAt(res.length() - 1, '=');
            res.setCharAt(res.length() - 2, '=');
        } else if (mod == 2) {
            res.setCharAt(res.length() - 1, '=');
        }
        return res.toString();
    }

    public static byte[] decode(String base64) {
        // Handling Padding. Base64 strings can end with = or == to make the length a multiple of 4.
        // == -> 2 padding bytes -> means only 1 real byte came from the last block
        // = -> 1 padding bytes -> means 2 real byes can from the last block
        int padding = 0;
        int len =  base64.length();
        if(base64.endsWith("==")) padding = 2;
        else if(base64.endsWith("=")) padding = 1;

        // Since we have encoded 6 bits for each character So total bits = len * 6;
        // Furhter divide by 8 -> gives number of oringinal bytes and then subtract padding.
        int bytesLen = (len * 6) / 8 - padding; // calculate length of decoded bytes
        byte[] decoded = new byte[bytesLen];

        int byteIndex = 0;
        int i = 0;
        while (i < len) {
            // Process input in Chunks of 4
            // Base64 encoding outputs 4 charactes for every 3 input bytes
            // c0..c3 | look up for their 6 bit values in INV_ALPHABET
            int c0 = base64.charAt(i) == '=' ? 0 : INV_ALPHABET[base64.charAt(i)];
            int c1 = base64.charAt(i + 1) == '=' ? 0 : INV_ALPHABET[base64.charAt(i + 1)];
            int c2 = base64.charAt(i + 2) == '=' ? 0 : INV_ALPHABET[base64.charAt(i + 2)];
            int c3 = base64.charAt(i + 3) == '=' ? 0 : INV_ALPHABET[base64.charAt(i + 3)];

            int block = (c0 << 18) | (c1 << 12) | (c2 << 6) | c3; // Recombine into 24 bit block

            // Extract the oringal bytes / characters from the block
            if (byteIndex < bytesLen) decoded[byteIndex++] = (byte) ((block >> 16) & 0xFF); // First Char
            if (byteIndex < bytesLen) decoded[byteIndex++] = (byte) ((block >> 8) & 0xFF); // Second Char
            if (byteIndex < bytesLen) decoded[byteIndex++] = (byte) (block & 0xFF); // Third Char

            i += 4; // move to the next 4 base64 chars
        }
        return decoded; // return the decoded array
    }
    public static void main(String[] args) {
        String text = "Hello World!!";
        String encoded = encode(text.getBytes()); // Creates an array of ASCII values for each character
        System.out.println("Encoded (manual): " + encoded);

        byte[] decodedBytes = decode(encoded);
        String decoded = new String(decodedBytes);
        System.out.println("Decoded (manual): " + decoded);
    }
}

