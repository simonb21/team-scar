package chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import config.Config;
import config.GameConfig;
import state.Play;

/*******************************************************************************
*   Creates a thread that listens for input from the user. The input is then   *
* sent to the server via the socket.                                           *
*******************************************************************************/
public class Client {
    private Play state;
    private String name;
    private Socket socket;
    private ClientThread client;
    private DataOutputStream out;
    private boolean connected;

    public Client(String serverAddr, String name, Play state) {
        this.name  = name;
        this.state = state;

        try {
            socket = new Socket(serverAddr, Config.PORT);
            System.out.println("Connected: " + socket);
        } catch (UnknownHostException uhe) {
            System.out.println("Host not found.\n" + uhe);
        } catch (IOException ioe) {
            System.out.println("Client Connection failed.\n");
            ioe.printStackTrace();
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
        } else {
        	state.receive(msg);
        }
    }
    
    public void send(String msg) {
    	if(connected) {
	    	try {
	            out.writeUTF(name + ": " + msg);
	        } catch (IOException ioe) {
	            System.out.println("Disconnected from the server.");
	            stop();
	        }
    	}
    }

    public void start() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Failed to start OutputStream. \n" + ioe);
        }
        connected = true;
        client    = new ClientThread(this, socket);
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

//    public static void main(String[] args) throws IOException{
//        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("Enter name: ");
//
//        Client c = new Client(br.readLine());
//        c.start();
//    }

}
