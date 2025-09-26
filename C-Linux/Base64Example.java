import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class Base64Example {
    public static String encode(String plain) {
        return Base64.getEncoder().encodeToString(plain.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String base64) {
        byte[] decoded = Base64.getDecoder().decode(base64);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String original = "Hello, Harsh!";
        String encoded = encode(original);
        String decoded = decode(encoded);

        System.out.println("Original: " + original);
        System.out.println("Encoded : " + encoded);
        System.out.println("Decoded : " + decoded);
    }
}

