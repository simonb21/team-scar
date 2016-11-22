package udp.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Iterator;

import config.GameConfig;
import main.GameState;
import main.Player;

public class GameServer implements Runnable {

	private GameState game;
	private String address;
	private String phase;
	private int playerCount;
	private int port;
	int i=0;
	
	public GameServer(String address, int port) {
		this.address = address;
		this.phase = "WAITING";
		this.port = port;
		this.playerCount = 0;
		
		this.game = new GameState();
		game.init();
		
		Thread listener = new GameServerThread(this, address, port);
		listener.start();
	}
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setGame(GameState g) {
		this.game = g;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public void send(String message) throws IOException {
        byte[] buffer  = new byte[256];
		InetAddress addr = InetAddress.getByName(address);
        DatagramSocket socket = new DatagramSocket();
        
        buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(
        		buffer,
        		buffer.length,
        		addr,
        		port
		);

        socket.send(packet);
        socket.close();
	}
	
	public void receive(String message) throws IOException {
		message = message.trim();
				
		switch(phase) {
		case "WAITING":
			if(message.startsWith("JOIN")) {
				String[] data = message.split(";");

				game.addPlayer(new Player(data[1]));
				playerCount += 1;
				
				if(playerCount == GameConfig.PLAYERS) {
					send("START_" + game.toString());
					phase = "START";
				} else send("CONNECTED");
			}
			break;
		case "START":
			String[] data = message.split(",");
			int key = Integer.parseInt(data[1]);
			int x   = Integer.parseInt(data[3]);
			int y   = Integer.parseInt(data[4]);
			
			for(Iterator<Integer> ite=game.getPlayers().keySet().iterator(); ite.hasNext();) {
				key = ite.next();
				Player p = game.getPlayers().get(key);
				
				p.setCoords(x, y);
			}
			break;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Server running");
		try {
	        while(true) {
	            game.getMoles().get(i).toggle();
	            i = (i+1)%21;
	            
	        	send(game.toString());
			    Thread.sleep(3000);
	       }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
