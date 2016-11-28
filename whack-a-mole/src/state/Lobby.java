package state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import main.LobbyServer;

public class Lobby extends BasicGameState {

	private String type;
	private String mouse;
	
	public Lobby(String type) {
		this.type = type;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		if (this.type == "host") {
			Image lobby_background = new Image("res/create_lobby.png");
			lobby_background.draw(0,0);
		} else {
			Image lobby_background = new Image("res/join_lobby.png");
			lobby_background.draw(0,0);
		}

		
		g.drawString(mouse, 10, 580);
		g.drawString(type + " Lobby", 50, 50);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if((xpos>360 && xpos<415) && (ypos>150 && ypos<170)) { //Back  to menu
			if(input.isMouseButtonDown(0)) {
				//-note-
				sbg.enterState(1);
			}
		}
		
		if((xpos>615 && xpos<665) && (ypos>150 && ypos<170)) { //Create lobby
			if(input.isMouseButtonDown(0)) {
				sbg.addState(new WaitingLobby(this.type));
				sbg.enterState(4);
			}
		}
	
	}
	
	public int getID() {
		return GameConfig.LOBBY;
	}
	
}
