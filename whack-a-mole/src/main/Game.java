package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import state.Lobby;
import state.Menu;
import state.Play;

public class Game extends StateBasedGame {
	
	public Game() {
		super(GameConfig.NAME);
		
		this.addState(new Menu());
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(GameConfig.MENU);
		this.enterState(GameConfig.MENU);
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		
		try {
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(
				GameConfig.WIDTH,
				GameConfig.HEIGHT,
				false
			);
			appgc.start();
		} catch (SlickException se) {
			se.printStackTrace();
		}
	}

}
