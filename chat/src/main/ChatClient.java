package src.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import src.config.Config;

public class ChatClient {
    // private Socket socket;
    //
    // public ChatClient() {
    //     socket = new Socket(Config.SERVER_NAME, Config.PORT);
    // }

    public static void main(String[] args) throws IOException{
        Socket socket = new Socket(Config.SERVER_NAME, Config.PORT);

        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);

        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);

        System.out.println("The client started. To quit it type 'Ok'.");
        String response;
        while(true) {
            System.out.print("Say: ");
            out.writeUTF(br.readLine());

            System.out.println(in.readUTF());
        }
    }

}
