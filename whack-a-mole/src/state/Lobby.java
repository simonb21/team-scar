package state;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import chat.Server;
import config.GameConfig;
import main.GameState;
import main.Player;
import udp.game.GameServer;
import udp.game.LobbyThread;

public class Lobby extends BasicGameState {
	private GameServer server;
	private Server chatServer;
	private Player player;
	private String username;
	private String address;
	private String serverAddr;
	private String hostname;
	private String phase;
	private int port;
	
	private boolean connected;
	
	private Image lobby_background;
	private Image lobby_sidebar;
	private Image lobby_sidebar_host;
	private Image lobby_sidebar7;
	
	private String info;
	private String playerlist;
	
	public Lobby() {
		this.phase = "NEW";
		this.info = "";
		this.playerlist = "";
		this.hostname = "";
		this.serverAddr = "";
		this.port = 0;
		this.connected = false;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		lobby_background = new Image("res/lobby_bg.png");
		lobby_sidebar = new Image("res/lobby_sidebar6.png");
		lobby_sidebar7 = new Image("res/lobby_sidebar16.png");
		lobby_sidebar_host = new Image("res/lobby_sidebar_host.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		lobby_background.draw(0,0);

		lobby_sidebar7.draw(5, 10);
		if(server != null) {
			lobby_sidebar_host.draw(650,-10);
			g.drawString(info, 280, 160);
			g.drawString(serverAddr, 740, 255);
			g.drawString(String.valueOf(port), 740, 305);
		} else {
			lobby_sidebar.draw(650,-10);
			g.drawString(info, 330, 160);
		}
		
		
		g.drawString(playerlist, 165, 235);
		g.drawString(hostname, 165, 160);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(phase.startsWith("START")) {
			GameState game = parseGame();
			
			if(server != null) {
				server.countDown();
				chatServer.start();
			}

			((Play) sbg.getState(GameConfig.PLAY)).setGame(game);
			((Play) sbg.getState(GameConfig.PLAY)).setServerName(serverAddr);
			((Play) sbg.getState(GameConfig.PLAY)).setPlayer(player);
			((Play) sbg.getState(GameConfig.PLAY)).addChatClient();

			((Play) sbg.getState(GameConfig.PLAY)).start(serverAddr, address, port);

			sbg.enterState(GameConfig.PLAY);
		}

		if(server == null) info = "Connected to Host";
		else info = "Waiting for Players " + server.getPlayerCount() + "/" + server.maxPlayers;
	}
	
	public int getID() {
		return GameConfig.LOBBY;
	}
	
	public void update(String message) {
		phase = message.trim();
        if(message.startsWith("CONNECTED")) {
        	String[] data = phase.split("_");
        	hostname = data[1];
        	connected = true;
        }

        if(message.startsWith("WAITING")) {
        	String[] data = phase.split("_");
        	serverAddr = data[1];
        	if(!connected) {
        		try {
            		System.out.println("Sending host");
        			send("JOIN;" + username);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        	
        	playerlist = "";
        	for(String player: data[3].split(";")) {
        		String[] info = player.split(",");

        		if(!info[0].equals(hostname)) {
	        		playerlist += info[0];
	        		playerlist += "\n";
        		}
        	}
        }
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
	
	public void setParams(String s, String address, int p) {
		this.username = s;
		this.address = address;
		this.port = p;

		LobbyThread lt = new LobbyThread(this, address, p);
		lt.start();
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
