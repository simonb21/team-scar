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
import main.GameState;
import main.Player;
import udp.game.GameServer;

public class Prompt extends BasicGameState {
	
	private TextField nameField;
	private TextField addrField;
	private TextField portField;
	
	private String username;
	private String type;
	private String port;
	
	private String mouse;
	private Image hbg;
	private Image cbg;

	public Prompt() {
		this.type  = "host";
		this.mouse = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nameField = new TextField(gc, gc.getDefaultFont(), 360, 285, 300, 30);
		addrField = new TextField(gc, gc.getDefaultFont(), 360, 370, 200, 30);
		portField = new TextField(gc, gc.getDefaultFont(), 570, 370, 90, 30);
		
		nameField.setMaxLength(10);
		
		hbg = new Image("res/create_bg.png");
		cbg = new Image("res/join_bg.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(type.equals("host")) {
			g.drawImage(hbg, 0, 0);
		} else {
			g.drawImage(cbg, 0, 0);
		}
		
		nameField.render(gc, g);
		addrField.render(gc, g);
		portField.render(gc, g);
		
		g.drawString(mouse, 10, 580);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		username = nameField.getText();
		port = portField.getText();
		if((xpos>335 && xpos<445) && (ypos>125 && ypos<190)) {
			if(input.isMousePressed(0)) {
				clear();
				sbg.enterState(GameConfig.MENU);
			}
		}
		
		if((xpos>585 && xpos<695) && (ypos>125 && ypos<190)) {
			if(input.isMousePressed(0) && !username.trim().equals("")) { // TODO
				if(type.equals("host")) {
					GameServer server = new GameServer("224.0.0.3", 2121);
					
					((Lobby) sbg.getState(GameConfig.LOBBY)).setServer(server);
					((Lobby) sbg.getState(GameConfig.LOBBY)).setParams(
							username,
							"224.0.0.3",
							2121
					);
					sbg.enterState(GameConfig.LOBBY);
				} else {
					((Lobby) sbg.getState(GameConfig.LOBBY)).setParams(
							username,
							"224.0.0.3",
							2121
					);
					sbg.enterState(GameConfig.LOBBY);
				}
				clear();
			}
		}
	}
	
	public int getID() {
		return GameConfig.PROMPT;
	}
	
	public boolean isValid() {
		// TODO
		return true;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void clear() {
		nameField.setText("");
		username = "";
	}
	
}
