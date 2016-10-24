package src.main;

import java.net.Socket;
import java.io.IOException;
import src.config.Config;

public class ChatClient {

    public static void main(String[] args) throws IOException{
        Socket socket = new Socket(Config.SERVER_NAME, Config.PORT);
        
    }

}
