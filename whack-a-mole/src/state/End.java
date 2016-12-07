package state;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import config.GameConfig;
import main.Player;

public class End extends BasicGameState {
	
	private Image win_bg;
	private Image lose_bg;
	private Player player;
	private boolean won;
	private TrueTypeFont display;

	public End() {
		won = true;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		win_bg = new Image("res/win_bg.png");
		lose_bg = new Image("res/lose_bg.png");
		
		display = new TrueTypeFont(new Font("Verdana", Font.BOLD, 52), false);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(won) {
			g.drawImage(win_bg, 0, 0);
			
			display.drawString(
					GameConfig.WIDTH/2 - (display.getWidth(player.name)/2),
					350,
					player.name,
					Color.yellow
			);
		} else {
			g.drawImage(lose_bg, 0, 0);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
	}
	
	public int getID() {
		return GameConfig.END;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public void setWon(boolean w) {
		won = w;
	}
	
}
