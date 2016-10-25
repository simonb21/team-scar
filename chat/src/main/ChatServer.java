package src.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import src.config.Config;

public class ChatServer implements Runnable {
    private ServerSocket serverSocket;

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(Config.PORT);
        serverSocket.setSoTimeout(0);
    }

    //@Override
    public void run() {
        ArrayList<ChatClient> clients = new ArrayList<ChatClient>();
        boolean connected = true;

        try {
            String msg = "info: Server is running on port "
                        + serverSocket.getLocalPort() + " .. ";

            System.out.println(msg);

            Socket server = serverSocket.accept();
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            while(!server.isClosed()) {
                msg = in.readUTF();
                out.writeUTF("Server says: " + msg);
            }
        } catch (IOException e) {
            String err = "Accept failed on: " + serverSocket.getLocalPort();
            System.out.println(err);
        }
    }

    public static void main(String[] args) throws IOException {
        Thread server = new Thread(new ChatServer());
        server.start();
    }

}
