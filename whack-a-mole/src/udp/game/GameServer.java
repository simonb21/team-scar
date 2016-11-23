package udp.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import config.GameConfig;
import main.Game;
import main.GameState;
import main.Player;

public class GameServer implements Runnable {

	private GameState state;
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
		
		this.state = new GameState();
		state.init();
		
		Thread listener = new GameServerThread(this, address, port);
		listener.start();
	}
	
	public void start() {
		Thread g = new Thread(new Game(state));
		Thread t = new Thread(this);
		
		g.start();
		t.start();
	}
	
	public void setGame(GameState g) {
		this.state = g;
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

				state.addPlayer(new Player(data[1]));
				playerCount += 1;
				
				if(playerCount == GameConfig.PLAYERS) {
					send("START_" + state.toString());
					phase = "START";
				} else send("CONNECTED");
			}
			break;
		case "START":
			// Update Player
			if(message.startsWith("PLAYER")) {
				String[] msg  = message.split("_");
				String[] data = msg[1].split(",");
				
				int key   = Integer.parseInt(data[1]);
				int score = Integer.parseInt(data[2]);
				int x     = Integer.parseInt(data[3]);
				int y     = Integer.parseInt(data[4]);
				
				Player p = state.getPlayers().get(key);
				p.setScore(score);
				p.setCoords(x, y);
			}
			
			// Update Board
			if(message.startsWith("ACTION")) {
				String[] msg  = message.split("_");
				for(String temp: msg[2].split(";")) {
					String[] box = temp.split(",");
					
					int index = Integer.parseInt(box[0]) % 21;
					int up	  = Integer.parseInt(box[1]);
					
					state.getMoles().get(index).setUp(up);
				}
			}
			
			send(state.toString());
			break;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Server running");
		try {
	        while(true) {
//	        	state.getMoles().get(i).toggle();
//	            i = (i+1)%21;
	            
	        	send(state.toString());
			    Thread.sleep(300);
	       }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
