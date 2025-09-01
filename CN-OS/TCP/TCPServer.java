import java.util.*;
import java.io.*;
import java.net.*;
public class TCPServer {
    public static void main(String[] args){

        int port = 5000; // server will listen on this port 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            Socket socket = serverSocket.accept(); 
            System.out.println("Client connected");

            // Input & Output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            // Read message from client 
            String message = in.readLine();
            System.out.println("Client Says : " + message);

            out.println("Hello Client I recieved your message");
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}
