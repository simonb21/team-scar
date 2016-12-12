package udp.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import state.Lobby;

public class LobbyThread extends Thread {
	
	private Lobby lobby;
	private String address;
	private int port;
	
	public LobbyThread(Lobby lobby, String address, int port) {
		this.lobby = lobby;
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void run() {
		System.out.println("Client running");
		try {
			InetAddress addr = InetAddress.getByName(address);
	       
	        MulticastSocket socket = new MulticastSocket(port);
	        socket.joinGroup(addr);
	        
	        while(true) {
		        byte[] buffer  = new byte[256];
	        	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		    	socket.receive(packet);
		       
		    	String data = new String(buffer, 0, buffer.length);
		        lobby.update(data);
	       }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
