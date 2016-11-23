package udp.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class GameServerThread extends Thread {
	
	private GameServer server;
	private MulticastSocket socket;
	private String address;
	private int port;
	
	public GameServerThread(GameServer s, String addr, int p) {
		this.server = s;
		this.address = addr;
		this.port = p;
	}
	
	@Override
	public void run() {
		System.out.println("Server listening");
		try {
	        InetAddress addr = InetAddress.getByName(address);
		
			socket = new MulticastSocket(port+10);
			socket.joinGroup(addr);	
			
			while(true) {
		        byte[] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		    	socket.receive(packet);
		    	
		    	String message = new String(buffer, 0, buffer.length);
		    	server.receive(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
