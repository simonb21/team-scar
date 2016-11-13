package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class LobbyServer implements Runnable {
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private int port;
	
	public LobbyServer(int port) {
		this.port = port;
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		System.out.println("Server running");
		while(true) {
			try {
				DatagramSocket socket =   new DatagramSocket(port);
		        System.out.println("Waiting for time requests...");
	
		        byte buffer[] = new byte[256];
		        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	
		        socket.receive(packet);
			    System.out.print("Request received...sending time...");
		        
		        String date = new Date().toString();
		        buffer = date.getBytes();
		        InetAddress address = packet.getAddress();
	
		        int port = packet.getPort();
		        packet = new DatagramPacket(buffer, buffer.length, address, port);
		        socket.send(packet);
		        socket.close();
			    System.out.println("done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ip, int port) {
		packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
