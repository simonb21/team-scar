package main;

public class HitBox {
	
	private static int count;
	public final int x;
	public final int y;
	public final int id;
	private int up;
	
	public HitBox(int x, int y) {
		this.x = x;
		this.y = y;
		this.up = 0;
		
		this.id = count++;
	}
	
	public int getUp() {
		return up;
	}
	
	public void toggle() {
		up = (up+1)%2;
	}

	public void setUp(int v) {
		up = v;
	}
	
	public String toString() {
		String temp = "";
		temp += Integer.toString(id) + ",";
		temp += Integer.toString(up);
		
		return temp;
	}
	
}
