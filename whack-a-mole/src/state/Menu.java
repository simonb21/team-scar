package state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import main.LobbyClient;
import main.LobbyServer;

public class Menu extends BasicGameState {
	
	private String mouse;

	public Menu() {
		this.mouse = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("MAIN MENU", 50, 50);
		g.drawString("Play", 60, 75);
		g.drawString("Create Lobby", 60, 105);
		g.drawString("Join Lobby", 60, 135);
		g.drawString("Exit", 60, 165);
		g.drawString(mouse, 10, 580);

		g.drawRect(55, 70, 50, 25);
		g.drawRect(55, 100, 118, 25);
		g.drawRect(55, 130, 100, 25);
		g.drawRect(55, 160, 50, 25);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if((xpos>55 && xpos<105) && (ypos>503 && ypos<528)) {
			if(input.isMouseButtonDown(0)) {
				sbg.addState(new Play());
				sbg.enterState(GameConfig.PLAY);
			}
		}
		
		if((xpos>55 && xpos<173) && (ypos>473 && ypos<498)) {
			if(input.isMouseButtonDown(0)) {
				sbg.addState(new Lobby("host"));
				sbg.enterState(GameConfig.LOBBY);
				LobbyServer server = new LobbyServer(2121);
			}
		}
		
		if((xpos>55 && xpos<155) && (ypos>443 && ypos<468)) {
			if(input.isMouseButtonDown(0)) {
				sbg.addState(new Lobby("client"));
				sbg.enterState(GameConfig.LOBBY);
				LobbyClient client = new LobbyClient(2121);
			}
		}
		
		if((xpos>55 && xpos<105) && (ypos>413 && ypos<438)) {
			if(input.isMouseButtonDown(0)) {
				System.exit(0);
			}
		}
	}
	
	public int getID() {
		return GameConfig.MENU;
	}
	
}
