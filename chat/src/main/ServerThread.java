package src.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import src.config.Config;

/*******************************************************************************
*   Creates a thread that listens to a socket which corresponds to a client.   *
* The thread notifies the server if it receives a message then broadcast to    *
* each ServerThread which is forwarded to the client.                          *
*******************************************************************************/
public class ServerThread extends Thread {
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private boolean running;

    public ServerThread(Server server, Socket socket) {
        super();
        this.socket = socket;
        this.server = server;
        this.running = true;
    }

    @Override
    public void run() {
        while(running) {
            try {
                in  = new DataInputStream(socket.getInputStream());
                server.broadcast(this, in.readUTF());
            } catch (IOException ioe) {
                System.err.println("Error retrieving message.\n " + ioe);
                break;
            }
        }
    }

    /********************************************************************
    *   Receives messages from the server and forwards it to the client *
    *                                                                   *
    *   Parameters                                                      *
    *   client -> ServerThread that sent the message                    *
    *   msg    -> The message                                           *
    ********************************************************************/
    public void send(ServerThread sender, String msg) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);
        } catch (IOException ioe) {
            System.out.println("Error: Message \'" + msg + "\' not sent.\n" + ioe);
        }
    }

    public void close() throws IOException{
        running = false;
        if(in  != null) in.close();
        if(out != null) out.close();
        if(socket != null) socket.close();
    }

}
