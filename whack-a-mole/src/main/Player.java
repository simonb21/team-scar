package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Player {
	
	private static int count;
	public final String name;
	public final int id;
	private int score;
	private int xpos;
	private int ypos;
	
	private Sound whack;
	
	public Player(String name) {
		this.name  = name;
		this.score = 0;
		
		this.id = count++;
	}
	
	public Player(int id, String name) {
		this.id	   = id;
		this.name  = name;
		this.score = 0;
	}

	public int getX() {
		return xpos;
	}

	public int getY() {
		return ypos;
	}
	
	public synchronized int getScore() {
		return score;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}
	
	public void setCoords(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}
	
	public synchronized void addScore(int points) {
		score += points;
	}
	
	public synchronized void subScore(int points) {
		score -= points;
	}
	
	public synchronized String toString() {
		String temp = "";
		temp += this.name	+ ",";
		temp += this.id		+ ",";
		temp += this.score	+ ",";
		temp += this.xpos	+ ",";
		temp += this.ypos;
		
		return temp;
	}
	
}
