package state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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

public class WaitingLobby extends BasicGameState {

	private String type;
	private String mouse;
	
	public WaitingLobby(String type) {
		this.type = type;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image lobby_background = new Image("res/lobby_bg.png");
		lobby_background.draw(0,0);
		
		Image lobby_sidebar = new Image("res/lobby_sidebar6.png");
		lobby_sidebar.draw(650,-10);
		
		Image lobby_sidebar7 = new Image("res/lobby_sidebar16.png");
		lobby_sidebar7.draw(5, 10);
		g.drawString(mouse, 300, 580);
		
		Color colorAlpha = new Color(0f,0f,0f,1f);
		g.setColor(colorAlpha);		
		
		g.fillRect(30, 440, 580, 125);
		g.fillRect(675, 440, 290, 125);
		g.setColor(Color.white);
		g.drawRect(30, 440, 580, 125);
		g.drawLine(30, 535, 610, 535);
		g.drawRect(525, 540, 75, 20);
		g.drawRect(675, 440, 290, 125);
		g.drawRect(715, 455, 210, 40);
		g.drawRect(715, 510, 210, 40);
		g.drawString("Enter", 540, 540);
		g.drawString("Cancel", 795, 520);
		
		if (this.type == "host") {
			g.drawString("Start", 795, 470);
			lobby_sidebar = new Image("res/lobby_sidebar_host.png");
			lobby_sidebar.draw(650,-10);
		} else if (this.type == "client") {
			g.drawString("Ready", 795, 470);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
	
	}
	
	public int getID() {
		return GameConfig.WAITING;
	}
	
}
