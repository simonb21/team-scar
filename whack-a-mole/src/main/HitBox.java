package main;

import config.GameConfig;

public class HitBox extends Thread{
	
	private static int count;
	public final int x;
	public final int y;
	public final int id;
	private boolean running;
	private int up;
	
	public HitBox(int x, int y) {
		this.x = x;
		this.y = y;
		this.up = 0;
		this.running = false;
		
		this.id = count++;
	}
	
	public int getUp() {
		return up;
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
	
	public void toggle() {
		up = (up+1)%2;
	}
	
	public String toString() {
		String temp = "";
		temp += Integer.toString(id) + ",";
		temp += Integer.toString(up);
		
		return temp;
	}
	
	public void run() {
		while(true) {
			if(isRunning()){
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
