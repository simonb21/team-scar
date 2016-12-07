package state;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import chat.Server;
import config.GameConfig;
import main.GameState;
import main.Player;
import udp.game.GameServer;

public class Lobby extends BasicGameState {
	
	private GameServer server;
	private Server chatServer;
	private Player player;
	private String username;
	private String address;
	private String serverAddr;
	private String phase;
	private int port;
	
	private String info;
	private String mouse;
	
	public Lobby() {
		this.phase = "NEW";
		this.mouse  = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		info = ":)";
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(info, 10, 50);
		
		g.drawString(phase, 10, 550);
		g.drawString(mouse, 10, 580);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		try {
        	String message;
        	
			InetAddress addr = InetAddress.getByName(address);
	        MulticastSocket socket = new MulticastSocket(port);
	        byte[] buffer  = new byte[256];
			
	        if(phase.startsWith("START")) {
				GameState game = parseGame();
				
				if(server != null) {
					server.start();
					server.countDown();
					chatServer.start();
				}

				((Play) sbg.getState(GameConfig.PLAY)).setGame(game);
				((Play) sbg.getState(GameConfig.PLAY)).setPlayer(player);
				((Play) sbg.getState(GameConfig.PLAY)).addChatClient();

				((Play) sbg.getState(GameConfig.PLAY)).start(address, port);

				sbg.enterState(GameConfig.PLAY);
	        } else if(phase.equals("NEW")) {
	        	send("JOIN;" + username);
	        } 
	        
	    	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	        socket.joinGroup(addr);
	    	socket.receive(packet);
	    	
	    	message = new String(buffer, 0, buffer.length);
	        socket.close();
	        
	        phase = message.trim();
	        if(message.startsWith("WAITING")) {
	        	String[] data = phase.split("_");
	        	serverAddr = data[1];
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(server == null) info = "Connected to Host";
		else info = "Waiting for Players " + server.getPlayerCount() + "/" + server.maxPlayers;
	}
	
	public int getID() {
		return GameConfig.LOBBY;
	}
	
	public void send(String message) throws IOException {
        byte[] buffer  = new byte[256];
		InetAddress addr = InetAddress.getByName(serverAddr);
        DatagramSocket socket = new DatagramSocket();
        
        buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(
        		buffer,
        		buffer.length,
        		addr,
        		GameConfig.PORT
		);
		socket.send(packet);
		socket.close();
//	    System.out.println("Socket sent msg " + game.toString());
	}
	
	public void setServer(GameServer s) {
		this.server = s;
		this.chatServer = new Server();
	}
	
	public void setParams(String s, String addr, int p) {
		this.username = s;
		this.address = addr;
		this.port = p;
	}
	
	public GameState parseGame() {
		GameState game = new GameState();
		game.init();
		
		String[] message = phase.split("_");
		for(String player: message[2].split(";")) {
			String[] data = player.split(",");
			
			int id = Integer.parseInt(data[1]);
			
			Player p = new Player(id, data[0]);
			
			if(data[0].equals(username)) this.player = p;
			game.addPlayer(p);
		}
		
		return game;
	}
	
}
