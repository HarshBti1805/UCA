import java.net.*;

public class UDPEchoServer {
    public static void main(String[] args) {
        int port = 6000;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Echo Server started on port " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String received = new String(request.getData(), 0, request.getLength());
                System.out.println("Received: " + received);

                // Echo back
                DatagramPacket response = new DatagramPacket(
                        request.getData(),
                        request.getLength(),
                        request.getAddress(),
                        request.getPort()
                );
                socket.send(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
