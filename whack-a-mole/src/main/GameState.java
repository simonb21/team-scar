package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.GameConfig;

public class GameState {
	
	private int time;
	private boolean endgame;
	private HashMap<Integer, Player> players;
	private ArrayList<HitBox> moles;
	
	public GameState() {
		players = new HashMap<Integer, Player>();
		moles   = new ArrayList<HitBox>();
		time    = GameConfig.MAX_TIME;
		endgame = false;
	}
	
	public void init() {
		moles.add(new HitBox(280, 475));
		moles.add(new HitBox(295, 390));
		moles.add(new HitBox(280, 295));

		moles.add(new HitBox(410, 500));
		moles.add(new HitBox(400, 425));
		moles.add(new HitBox(390, 330));

		moles.add(new HitBox(530, 465));
		moles.add(new HitBox(495, 370));
		moles.add(new HitBox(480, 265));
		
		moles.add(new HitBox(620, 510));
		moles.add(new HitBox(600, 390));
		moles.add(new HitBox(590, 300));
		
		moles.add(new HitBox(690, 430));
		moles.add(new HitBox(700, 345));
		moles.add(new HitBox(750, 265));

		moles.add(new HitBox(760, 500));
		moles.add(new HitBox(800, 425));
		moles.add(new HitBox(805, 345));

		moles.add(new HitBox(885, 470));
		moles.add(new HitBox(920, 385));
		moles.add(new HitBox(910, 290));
	}
	
	public synchronized String toString() {
		String temp = "";
		temp += String.valueOf(time);
		temp += "_";
		for(Iterator<Integer> ite=players.keySet().iterator();ite.hasNext();) {
			int key = ite.next();
			temp += players.get(key).toString();
			temp += ";";
		}
		temp += "_";
		for(HitBox b: moles) {
			temp += b.toString();
			temp += ";";
		}
		
		return temp;
	}
	
	public void addPlayer(Player p) {
		players.put(p.id, p);
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	public void setEnd(boolean b) {
		endgame = b;
	}
	
	public void timeDown() {
		time -= 1;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public ArrayList<HitBox> getMoles() {
		return moles;
	}
	
	public int getTime() {
		return time;
	}
	
	public boolean getEnd() {
		return endgame;
	}
	
}
