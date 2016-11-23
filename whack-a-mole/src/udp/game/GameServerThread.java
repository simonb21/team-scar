package udp.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import config.GameConfig;

public class GameServerThread extends Thread {
	
	private GameServer server;
	
	public GameServerThread(GameServer s) {
		this.server = s;
	}
	
	@Override
	public void run() {
		System.out.println("Server listening");
		try {
	        DatagramSocket socket = new DatagramSocket(GameConfig.PORT);
	        
			while(true) {
		        byte[] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		    	socket.receive(packet);
		    	
		    	String message = new String(buffer, 0, buffer.length);
		    	server.receive(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
