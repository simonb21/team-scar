package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/*******************************************************************************
*   Creates a Thread that listens for messages from the server. It then sends  *
* the message to the client to be received.
*******************************************************************************/
public class ClientThread extends Thread {
    private Client client;
    private boolean running;
    private DataInputStream in;

    public ClientThread(Client client, Socket socket) {
        this.running = true;
        this.client  = client;

        try {
            this.in = new DataInputStream(socket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("Failed to start InputStream. \n" + ioe);
        }

        this.start();
    }

    public void run() {
        while(running) {
            try {
                client.receive(in.readUTF());
            } catch (IOException ioe) {
                System.out.println("Listening error.\n" + ioe);
                close();
            }
        }
    }

    public void close() {
        try {
            running = false;
            if(in != null) in.close();
        } catch(IOException ioe) {
            System.out.println("Error closing input stream.\n" + ioe);
        }
    }
}
