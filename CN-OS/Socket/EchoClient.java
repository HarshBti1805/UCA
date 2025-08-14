import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        try (
            Socket socket = new Socket(serverAddress, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Connected to server. Type messages (type 'exit' to quit):");

            String userInput;
            while ((userInput = input.readLine()) != null) {
                if (userInput.equalsIgnoreCase("exit")) {
                    break; // leave loop
                }
                out.println(userInput); // send to server
                String response = in.readLine(); // receive from server
                System.out.println("Server replied: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Client closed.");
    }
}
