package config;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameConfig {
	
	// Application Properties 
	public static final String NAME    = "Whack a Mole";
	public static final int WIDTH      = 1024;
	public static final int HEIGHT     = 600;
	
	// Game state identifiers
	public static final int MENU       = 1;
	public static final int LOBBY      = 2;
	public static final int PLAY       = 3;
	public static final int PROMPT     = 4;
	
	// Network
    public static final String SERVER_NAME;
    public static final String ADDRESS = "224.0.0.5";
    public static final int PORT       = 2100;
	
	// Constants
    public static final int MAX_TIME   = 90;
	public static final int HOLES      = 21;
	public static final int MOLEWIDTH  = 70;
	public static final int MOLEHEIGHT = 50;
			
	// Mole Types
	public static final int M_REG      = 0;
	public static final int M_GOLD     = 1;
	public static final int M_BLACK    = 2;
	
	static {
        try {
            SERVER_NAME = InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            throw new Error("Failed to retrieve Server details.", e);
        }
    }
}
