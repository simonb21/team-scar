package state;

import java.io.IOException;
import java.util.Iterator;

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
import main.HitBox;
import main.Player;
import udp.game.PlayerThread;

public class Play extends BasicGameState {
	
	private GameState game;
	private Player player;
	
	private Image bg;
	private Image gbg;
	private Image mole;
	private TextField tf;
	private String mouse;
	private PlayerThread inst;

	public Play() {
		this.mouse  = "No Input Yet.";
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		tf = new TextField(gc, gc.getDefaultFont(), 5, 420, 402, 175);
		tf.setText("Integrate chat here");
		
		bg   = new Image("res/play_bg.png");
		gbg  = new Image("res/game_bg.png");
		mole = new Image("res/mole.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(gbg, 256, 0);
		g.drawString(mouse, 10, 580);
		
		for(HitBox b: game.getMoles()) {
			if(b.getUp() == 1) {
				g.drawImage(mole, b.x, GameConfig.HEIGHT-b.y);
			}
		}
		
		int i=0;
		for(Iterator<Integer> ite=game.getPlayers().keySet().iterator();ite.hasNext();) {
			int key = ite.next();
			Player p = game.getPlayers().get(key);
			
			g.drawString(p.name, 30, 80+(20*i));
			g.drawString(Integer.toString(p.getScore()), 130, 80+(20*i));
			i += 1;
			
			if(p.id != player.id) {
				//System.out.println("hey "+p.name + " "+ p.getX() + " " + p.getY());
				g.drawString(p.name, p.getX(), GameConfig.HEIGHT-p.getY());
			}
		}
		
		// Show HitBoxes
//		for(HitBox b: game.getMoles()) {
//			g.drawRect(
//					b.x, 
//					GameConfig.HEIGHT-b.y, 
//					GameConfig.MOLEWIDTH, 
//					GameConfig.MOLEHEIGHT
//			);
//			g.drawString(
//					Integer.toString(b.getUp()),
//					b.x,
//					GameConfig.HEIGHT-b.y
//			);
//		}
		
		g.drawRect(412, 420, 607, 175);
		tf.render(gc, g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if(input.isMousePressed(0)) {
			for(HitBox b: game.getMoles()) {
				if(b.isWhacked(xpos, ypos)) {
					player.addScore(100);
					try {
						inst.send("ACTION_"+game.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		player.setCoords(xpos, ypos);
		try {
			inst.send("PLAYER_"+player.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return GameConfig.PLAY;
	}

	public void setGame(GameState g) {
		this.game = g;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void start(String address, int port) {
		inst = new PlayerThread(game, address, port);
		inst.start();
	}
	
}
