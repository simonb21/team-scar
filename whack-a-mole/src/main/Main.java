package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import state.Lobby;
import state.Menu;
import state.Play;
import state.Prompt;

public class Main extends StateBasedGame {
	
	public Main() {
		super(GameConfig.NAME);
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Menu());
		this.addState(new Prompt());
		this.addState(new Lobby());
		this.addState(new Play());
		this.enterState(GameConfig.MENU);
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		
		try {
			appgc = new AppGameContainer(new Main());
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
