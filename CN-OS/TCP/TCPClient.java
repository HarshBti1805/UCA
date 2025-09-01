import java.io.*;
import java.net.*;
public class TCPClient {
    public static void main(String[] args){

        String hostname = "127.0.0.1"; // server IP     
        int port = 5000;

        try (Socket socket = new Socket(hostname, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello Server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Server says: " + response);


        }
        catch(UnknownHostException e) {
            System.out.println("Server not found : " + e.getMessage());
        }
        catch(IOException e) {
            System.out.println("I/O Error : " + e.getMessage());
        }   


    }


}
