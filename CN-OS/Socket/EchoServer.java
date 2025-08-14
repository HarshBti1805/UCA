import java.io.*;
import java.net.*;

public class EchoServer {

    public void closeConnection(Socket client) {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println("We received this from Client: " + line); // Server console
                out.println("Echo: " + line); // Send back to client
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(client); // Close connection for server and client
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        EchoServer serverInstance = new EchoServer();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (true) {
                // Listening for the connection
                Socket client = server.accept();
                System.out.println("Client address is: " + client.getRemoteSocketAddress());

                // Handle client in a separate thread
                Thread t = new Thread(() -> serverInstance.handleClient(client));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
