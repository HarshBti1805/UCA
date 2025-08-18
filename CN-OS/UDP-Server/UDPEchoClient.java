import java.net.*;
import java.io.*;

public class UDPEchoClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            InetAddress address = InetAddress.getByName(host);
            System.out.println("Connected to UDP server. Type messages:");

            while (true) {
                String input = userInput.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) break;

                byte[] buffer = input.getBytes();
                DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(request);

                byte[] responseBuffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(response);

                String echoed = new String(response.getData(), 0, response.getLength());
                System.out.println("Echo: " + echoed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
