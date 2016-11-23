package config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class GameConfig {
	
	// Application Properties 
	public static final String NAME = "Whack a Mole";
	public static final int WIDTH   = 1024;
	public static final int HEIGHT  = 600;
	
	// Game state identifiers
	public static final int MENU    = 1;
	public static final int LOBBY   = 2;
	public static final int PLAY    = 3;
	public static final int PROMPT  = 4;
	
	// Network
    public static final String SERVER_NAME;
    public static final int PORT    = 2100;
	
	// Constants
	public static final int HOLES   = 21;
	public static final int PLAYERS = 2;
	
	static {
        try {
            SERVER_NAME = InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            throw new Error("Failed to retrieve Server details.", e);
        }
    }
}
