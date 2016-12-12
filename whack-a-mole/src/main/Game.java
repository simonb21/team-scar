package main;

import java.security.SecureRandom;

public class Game implements Runnable {
	private GameState state;
	
	public Game(GameState state) {
		this.state = state;
	}
	
	@Override
	public void run() {
		SecureRandom rand = new SecureRandom();
		
		int next;
		while(true) {
			next = rand.nextInt(21);
			
			HitBox mole = state.getMoles().get(next);
			
			if(!mole.isAlive()) mole.start();
			if(!mole.isRunning()) mole.setRunning(true);
				
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
