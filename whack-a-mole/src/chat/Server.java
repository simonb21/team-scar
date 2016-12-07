package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import config.Config;

/*******************************************************************************
*   Creates a thread that accepts clients and stores their sockets in order to *
* maintain their connection with the server.                                   *
*******************************************************************************/
public class Server implements Runnable {
    private ArrayList<ServerThread> clients;
    private DataInputStream in;
    private ServerSocket server;
    private Socket socket;
    private Thread thread;
    private boolean running;

    public Server() {
        try {
            clients = new ArrayList<ServerThread>();
            server  = new ServerSocket(Config.PORT);
            server.setSoTimeout(0);
        } catch (IOException ioe) {
            System.out.println("Server failed to start.\n" + ioe);
            stop();
        }
    }

    @Override
    public void run() {
        String msg = "info: Server is running on port "
                   + server.getLocalPort() + " .. ";

        System.out.println(msg);

        while(running) {
            try {
                addClient(server.accept());
            } catch (IOException ioe) {
                System.out.println("Failed to accept client.\n" + ioe);
                stop();
            }
        }
    }

    /********************************************************************
    *       Sends a message to every client.          					*
    *                                                                   *
    *   Parameters:                                                     *
    *   client -> ServerThread that sent the message                    *
    *   msg    -> The message                                           *
    ********************************************************************/
    public synchronized void broadcast(ServerThread client, String msg) {
        if (msg.contains(": /q")) {
            client.send(client, msg);
            removeClient(client);
        } else {
            for (ServerThread c: clients) {
            	c.send(client, msg);
            }
        }
    }

    /********************************************************************
    *       Removes a client that requested a disconnections.           *
    *                                                                   *
    *   Parameters:                                                     *
    *   client -> ServerThread that sent the request                    *
    ********************************************************************/
    public synchronized void removeClient(ServerThread client) {
        System.out.println("Disconnecting client...");
        clients.remove(client);

        try {
            client.close();
        } catch (IOException ioe) {
            System.out.println("Error closing thread. \n" + ioe);
        }
    }

    /********************************************************************
    *       Creates a ServerThread that will serve as the client        *
    *   connection to the server.                                       *
    *                                                                   *
    *   Parameters:                                                     *
    *   socket -> Will serve as a connection between client and server  *
    ********************************************************************/
    public void addClient(Socket socket) {
        ServerThread client = new ServerThread(this, socket);
        System.out.println("Client accepted: " + socket);
        clients.add(client);
        client.start();
    }

    public void start() {
        running = true;
        thread  = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            if(in     != null) in.close();
            if(socket != null) socket.close();
        } catch(IOException ioe) {
            System.out.println("Error closing server.\n" + ioe);
        }
    }

//    public static void main(String[] args) throws IOException {
//        Server server = new Server();
//        server.start();
//    }

}
