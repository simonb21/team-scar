package main;

import java.security.SecureRandom;

import config.GameConfig;

public class HitBox extends Thread{
	
	private static int count;
	public final int x;
	public final int y;
	public final int id;
	private boolean running;
	private int type;
	private int up;
	
	public HitBox(int x, int y) {
		this.x = x;
		this.y = y;
		this.up = 0;
		this.type = 0;
		this.running = false;
		
		this.id = count++;
	}
	
	public int getUp() {
		return up;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isWhacked(int xpos, int ypos) {
		if((xpos>x && xpos<x+GameConfig.MOLEWIDTH) && (ypos>y-GameConfig.MOLEHEIGHT && ypos<y)) {
			if(up == 1) {
				up = 0;
				return true;	
			}
		}
		return false;
	}
	
	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized void setRunning(boolean b) {
		running = b;
	}

	public void setUp(int v) {
		up = v;
	}
	
	public void setType(int t) {
		type = t;
	}
	
	public void toggle() {
		up = (up+1)%2;
	}
	
	public String toString() {
		String temp = "";
		temp += Integer.toString(id) + ",";
		temp += Integer.toString(up) + ",";
		temp += Integer.toString(type);
		
		return temp;
	}
	
	public void run() {
		while(true) {
			if(isRunning()){
				SecureRandom rand = new SecureRandom();
				int x = rand.nextInt(20);
				if(x == 0) {
					type = GameConfig.M_GOLD;
				} else if(x<12) {
					type = GameConfig.M_BLACK;
				}
				
				up = 1;
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				up = 0;
				type = 0;
				running = false;
			}
		}
	}
	
}
