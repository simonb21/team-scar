package state;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;

public class Menu extends BasicGameState {
	
	private String mouse;
	private Image choices;
	private Animation titleAnimation;

	public Menu() {
		this.mouse = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image[] menu = {new Image("res/whack-a-mole-3.png"), 
				new Image("res/whack-a-mole-4.png")
			};
		choices = new Image("res/mana-full-2.png");
		titleAnimation = new Animation(menu, 100);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		titleAnimation.draw(-20,30);
		choices.draw(360, 280, 300, 250);
		g.drawString(mouse, 10, 580);
		

		g.drawRect(340, 275, 340, 70);
		g.drawRect(340, 360, 340, 70);
		g.drawRect(340, 440, 340, 70);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if((xpos>340 && xpos<680) && (ypos>250 && ypos<320)) { //Create lobby
			if(input.isMousePressed(0)) {
				((Prompt) sbg.getState(GameConfig.PROMPT)).setType("host");
				sbg.enterState(GameConfig.PROMPT);
			}
		}
		
		if((xpos>340 && xpos<680) && (ypos>170 && ypos<240)) { //Join lobby
			if(input.isMousePressed(0)) {
				((Prompt) sbg.getState(GameConfig.PROMPT)).setType("client");
				sbg.enterState(GameConfig.PROMPT);
			}
		}
		
		if((xpos>340 && xpos<680) && (ypos>90 && ypos<160)) { //Exit
			if(input.isMousePressed(0)) {
				System.out.println("sad");
				System.exit(0);
			}
		}
	}
	
	public int getID() {
		return GameConfig.MENU;
	}
	
}
