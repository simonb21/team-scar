package src.main;

import java.lang.Thread;
import java.net.ServerSocket;
import java.io.IOException;
import src.config.Config;

public class ChatServer extends Thread {
    private ServerSocket serverSocket;

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(Config.PORT);
        serverSocket.setSoTimeout(0);
    }
    public void run() {
        boolean connected = true;
    }
    public static void main(String[] args) throws IOException {

    }

}
