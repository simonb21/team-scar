package src.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import src.config.Config;

/*******************************************************************************
*   Creates a thread that listens for input from the user. The input is then   *
* sent to the server via the socket.                                           *
*******************************************************************************/
public class Client implements Runnable {
    private String name;
    private Socket socket;
    private Thread thread;
    private ClientThread client;
    private DataOutputStream out;
    private BufferedReader br;
    private boolean connected;

    public Client(String name) {
        this.name = name;

        try {
            socket = new Socket(Config.SERVER_NAME, Config.PORT);
            System.out.println("Connected: " + socket);
        } catch (UnknownHostException uhe) {
            System.out.println("Host not found.\n" + uhe);
        } catch (IOException ioe) {
            System.out.println("Client Connection failed.\n" + ioe);
        }
    }

    @Override
    public void run() {
        String msg;
        while(connected) {
            try {
                msg = br.readLine();
                out.writeUTF(name + ": " + msg);
            } catch (IOException ioe) {
                System.out.println("Disconnected from the server.");
                stop();
            }
        }
    }

    /********************************************************************
    *       Receives a message from the ClientThread                    *
    *                                                                   *
    *   Parameters:                                                     *
    *   msg -> The message that the ClientThread sent that was received *
    *          from the server.                                         *
    ********************************************************************/
    public void receive(String msg) {
        if (msg.contains(": /q")) {
            System.out.println("Goodbye. press ENTER to exit ..");
            stop();
        } else System.out.println(msg);
    }

    public void start() {
        try {
            br  = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Failed to start OutputStream. \n" + ioe);
        }
        connected = true;
        client    = new ClientThread(this, socket);
        thread    = new Thread(this);
        thread.start();
    }

    public void stop() {
        try {
            connected = false;
            if(out != null) out.close();
            if(socket != null) socket.close();
        } catch(IOException ioe) {
            System.out.println("Error closing client.\n" + ioe);
        }
        client.close();
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter name: ");

        Client c = new Client(br.readLine());
        c.start();
    }

}
