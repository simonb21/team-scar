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

public class Play extends BasicGameState {
	
	private Image bg;
	private Image gbg;
	private TextField tf;
	private String mouse;

	public Play() {
		this.mouse = "No Input Yet.";
		try {
			bg  = new Image("res/play_bg.png");
			gbg = new Image("res/game_bg.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(gbg, 256, 0);
		g.drawString(mouse, 10, 580);
		
		// Row 1
		g.drawRect(365, 20, 130, 95);
		g.drawRect(550, 70, 130, 95);
		g.drawRect(775, 50, 130, 95);
		
		// Row 2
		g.drawRect(350, 150, 130, 95);
		g.drawRect(595, 180, 130, 95);
		g.drawRect(850, 155, 130, 95);
		
		// Row 3
		g.drawRect(460, 280, 130, 95);
		g.drawRect(800, 260, 130, 95);
		
		g.drawRect(412, 420, 607, 175);
		tf = new TextField(gc, gc.getDefaultFont(), 5, 420, 402, 175);
		tf.setText("Integrate chat here");
		tf.render(gc, g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		// Row 1
		if((xpos>365 && xpos<495) && (ypos>480 && ypos<580)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 1 clicked!");
			}
		}
		
		if((xpos>550 && xpos<680) && (ypos>430 && ypos<530)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 2 clicked!");
			}
		}
		
		if((xpos>775 && xpos<905) && (ypos>450 && ypos<550)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 3 clicked!");
			}
		}
		
		// Row 2
		if((xpos>350 && xpos<480) && (ypos>350 && ypos<450)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 4 clicked!");
			}
		}
		
		if((xpos>595 && xpos<725) && (ypos>320 && ypos<420)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 5 clicked!");
			}
		}
		
		if((xpos>850 && xpos<980) && (ypos>345 && ypos<445)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 6 clicked!");
			}
		}
		
		//Row 3
		if((xpos>460 && xpos<590) && (ypos>220 && ypos<320)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 7 clicked!");
			}
		}
		
		if((xpos>800 && xpos<930) && (ypos>240 && ypos<340)) {
			if(input.isMousePressed(0)) {
				System.out.println("Box 8 clicked!");
			}
		}
	}
	
	public int getID() {
		return GameConfig.PLAY;
	}
	
}
