package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LobbyClient implements Runnable {
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private InetAddress ip;
	private int port;
	
	public LobbyClient(int port) {
		this.port = port;
		
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		System.out.println("Client running");
		String host = "127.0.0.1";
		try {
	       byte message[] = new byte[256];
	       ip = InetAddress.getByName(host);
	       System.out.println("Checking time at host " + ip + "...please wait...");
	       DatagramPacket packet = new DatagramPacket(message, message.length, ip, port);
	       DatagramSocket socket = new DatagramSocket();

	       socket.send(packet);
	       packet = new DatagramPacket(message, message.length);

	       socket.receive(packet);

	       String time = new String(packet.getData());
	       System.out.println("The time at " + host + " is: " + time);
	       socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(byte[] data) {
		packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
