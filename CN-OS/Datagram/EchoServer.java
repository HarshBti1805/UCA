import java.net.*;
import java.util.*;
class EchoServer {

	public static void main(String args[]) throws Exception {
		DatagramSocket d = new DatagramSocket(9090);
		byte[] recieved  = new byte[1024];
		DatagramPacket dp = new DatagramPacket(recieved, recieved.length); // Constructs a DatagramPacket for receiving packets of length length.

		d.receive(dp);
		InetAddress ipAddress = dp.getAddress();
		String message = new String(dp.getData());
		System.out.println(ipAddress + " " + message);

    }
}
