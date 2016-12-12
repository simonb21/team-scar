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
import udp.game.GameServer;

public class Prompt extends BasicGameState {
	
	private TextField nameField;
	private TextField portField1;
	private TextField portField2;
	private TextField maxField;
	
	private String username;
	private String type;
	private int port;
	private int max;
	
	private String mouse;
	private Image hbg;
	private Image cbg;

	public Prompt() {
		this.type  = "host";
		this.mouse = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nameField  = new TextField(gc, gc.getDefaultFont(), 360, 285, 300, 30);
		maxField   = new TextField(gc, gc.getDefaultFont(), 360, 370, 180, 30);
		portField1 = new TextField(gc, gc.getDefaultFont(), 550, 370, 110, 30);
		portField2 = new TextField(gc, gc.getDefaultFont(), 360, 370, 300, 30);
		
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
		
		if(type.equals("host")) {
			maxField.setLocation(360, 370);
			portField1.setLocation(550, 370);
			maxField.render(gc, g);
			portField1.render(gc, g);
		} else {
			maxField.setLocation(360, 570);
			portField1.setLocation(550, 570);
			portField2.render(gc, g);
		}
		
		g.drawString(mouse, 10, 580);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		username = nameField.getText();
		
		if((xpos>335 && xpos<445) && (ypos>125 && ypos<190)) {
			if(input.isMousePressed(0)) {
				clear();
				sbg.enterState(GameConfig.MENU);
			}
		}
		
		if((xpos>585 && xpos<695) && (ypos>125 && ypos<190)) {
			if(input.isMousePressed(0) && isValid()) { 
				if(type.equals("host")) {
					port = Integer.parseInt(portField1.getText());
					max  = Integer.parseInt(maxField.getText());
					
					GameServer server = new GameServer(username, max, GameConfig.ADDRESS, port);
					server.start();
					
					((Lobby) sbg.getState(GameConfig.LOBBY)).setServer(server);
					((Lobby) sbg.getState(GameConfig.LOBBY)).setParams(
							username,
							GameConfig.ADDRESS,
							port
					);
					sbg.enterState(GameConfig.LOBBY);
				} else {
					port = Integer.parseInt(portField2.getText());
					
					((Lobby) sbg.getState(GameConfig.LOBBY)).setParams(
							username,
							GameConfig.ADDRESS,
							port
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
		if(username.trim().equals(""))
			return false;
		
		if(type.equals("host")) {
			if(maxField.getText().trim().equals(""))
				return false;
			
			if(portField1.getText().trim().equals(""))
				return false;
		} else {
			if(portField2.getText().trim().equals(""))
				return false;
		}

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
