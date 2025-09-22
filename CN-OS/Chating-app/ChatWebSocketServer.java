import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketServer extends WebSocketServer {

    // Map each connection to a username
    private final Map<WebSocket, String> users =
            Collections.synchronizedMap(new HashMap<>());

    public ChatWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // No username yet; client will send "/nick <name>" first
        conn.send("Welcome! Set your name with: /nick your_name");
        System.out.println("New connection from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String name = users.remove(conn);
        if (name != null) {
            broadcast("💨 " + name + " left the chat");
            System.out.println("Closed: " + name + " (" + reason + ")");
        } else {
            System.out.println("Closed: " + conn.getRemoteSocketAddress() + " (" + reason + ")");
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Simple command: /nick <name>
        if (message.startsWith("/nick ")) {
            String name = message.substring(6).trim();
            if (name.isEmpty()) {
                conn.send("Name cannot be empty. Try: /nick Alice");
                return;
            }
            // ensure unique-ish names
            synchronized (users) {
                if (users.values().stream().anyMatch(n -> n.equalsIgnoreCase(name))) {
                    conn.send("Name already taken. Choose another one.");
                    return;
                }
                users.put(conn, name);
            }
            conn.send("✅ Name set to: " + name);
            broadcastExcept(conn, "👋 " + name + " joined the chat");
            System.out.println("User set nick: " + name);
            return;
        }

        // Regular chat: must have a name first
        String name = users.get(conn);
        if (name == null) {
            conn.send("Set your name first: /nick your_name");
            return;
        }
        String payload = "💬 " + name + ": " + message;
        broadcast(payload);
        System.out.println(payload);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket chat server started on " + getAddress());
        setConnectionLostTimeout(30);
    }

    private void broadcastExcept(WebSocket exclude, String msg) {
        synchronized (connections()) {
            for (WebSocket c : connections()) {
                if (c != exclude) c.send(msg);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080; // change if you want
        ChatWebSocketServer server = new ChatWebSocketServer(port);
        server.start();
        System.out.println("Listening on ws://0.0.0.0:" + port + "  (Ctrl+C to stop)");
    }
}
