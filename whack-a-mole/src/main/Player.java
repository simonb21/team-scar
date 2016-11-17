package main;

public class Player {
	
	private static int count;
	public final String name;
	public final int id;
	private int score;
	private int xpos;
	private int ypos;
	
	public Player(String name) {
		this.name  = name;
		this.score = 0;
		
		this.id = count++;
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setCoords(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}
	
	public void addScore(int points) {
		score += points;
	}
	
	public String toString() {
		String temp = "";
		temp += this.name + ",";
		temp += this.score + ",";
		temp += this.xpos + ",";
		temp += this.ypos;
		
		return temp;
	}
	
}
