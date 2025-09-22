import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Scanner;

public class ChatWebSocketClient extends WebSocketClient {

    private final String nickname;

    public ChatWebSocketClient(URI serverURI, String nickname) {
        super(serverURI);
        this.nickname = nickname;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected.");
        // Set nickname right away
        send("/nick " + nickname);
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java ChatWebSocketClient <ws://host:port> <nickname>");
            System.out.println("Example: java ChatWebSocketClient ws://127.0.0.1:8080 Alice");
            return;
        }
        String uri = args[0];
        String nick = args[1];

        ChatWebSocketClient client = new ChatWebSocketClient(new URI(uri), nick);
        client.connectBlocking(); // wait until connected

        Scanner sc = new Scanner(System.in);
        System.out.println("Type messages. /quit to exit.");
        while (true) {
            String line = sc.nextLine();
            if (line == null) break;
            if (line.equalsIgnoreCase("/quit")) {
                client.close();
                break;
            }
            client.send(line);
        }
    }
}
