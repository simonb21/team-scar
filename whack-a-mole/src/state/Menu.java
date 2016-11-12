package state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;

public class Menu extends BasicGameState {
	
	private String mouse;

	public Menu() {
		this.mouse = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("MAIN MENU", 50, 50);
		g.drawString("Create Lobby", 60, 75);
		g.drawString("Join Lobby", 60, 105);
		g.drawString("Exit", 60, 135);
		g.drawString(mouse, 10, 580);
		
		g.drawRect(55, 70, 118, 25);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if((xpos>55 && xpos<173) && (ypos>500 && ypos<530)) {
			if(input.isMouseButtonDown(0)) 
				sbg.enterState(GameConfig.PLAY_STATE);
		}
	}
	
	public int getID() {
		return GameConfig.MENU_STATE;
	}
	
}
