import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        int port = 5000; // you can choose any free port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Echo Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received: " + inputLine);
                    out.println("Echo: " + inputLine); // send back to client
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
