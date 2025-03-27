import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server...");
            new ServerListener(serverInput).start();

            String message;
            while ((message = consoleInput.readLine()) != null) {
                serverOutput.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServerListener extends Thread {
        private BufferedReader serverInput;

        public ServerListener(BufferedReader serverInput) {
            this.serverInput = serverInput;
        }

        public void run() {
            try {
                String message;
                while ((message = serverInput.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}