package main;

import java.io.Serializable;

public class Player implements Serializable {
	
	private String name;
	private int score;
	private int id;
	
	public Player(int id) {
		this.name = "Random Joe";
		this.id   = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int points) {
		score += points;
	}
	
}
