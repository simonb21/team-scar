package main;

import java.util.Random;

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
				Random rand = new Random();
				if(rand.nextInt(20) == 0) {
					if(rand.nextInt(2) == 0) {
						type = GameConfig.M_GOLD;
					} else {
						type = GameConfig.M_BLACK;
					}
				}
				
				up = 1;
				
				try {
					Thread.sleep(3500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				up = 0;
				running = false;
			}
		}
	}
	
}
