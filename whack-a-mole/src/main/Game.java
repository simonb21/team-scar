package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import state.Menu;
import state.Play;

public class Game extends StateBasedGame {
	
	public Game() {
		super(GameConfig.GAME_NAME);
		
		this.addState(new Menu());
		this.addState(new Play());
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(GameConfig.MENU_STATE).init(gc, this);
		this.getState(GameConfig.PLAY_STATE).init(gc, this);
		this.enterState(GameConfig.MENU_STATE);
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		
		try {
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(
				GameConfig.GAME_WIDTH,
				GameConfig.GAME_HEIGHT,
				false
			);
			appgc.start();
		} catch (SlickException se) {
			se.printStackTrace();
		}
	}

}
