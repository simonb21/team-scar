package state;

import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import chat.Client;
import config.GameConfig;
import main.GameState;
import main.HitBox;
import main.Player;
import udp.game.PlayerThread;

public class Play extends BasicGameState {

	private int time;
	private GameState game;
	private Player player;
	private Client chatClient;
	
	private Image bg;
	private Image gbg;
	private Image mole_r;
	private Image mole_g;
	private Image mole_b;
	private TextField in;
	private TextField out;
	private String mouse;
	private PlayerThread inst;

	public Play() {
		this.mouse = "No Input Yet.";
		this.time  = GameConfig.MAX_TIME;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		out = new TextField(gc, gc.getDefaultFont(), 5, 420, 402, 150);
		in  = new TextField(gc, gc.getDefaultFont(), 5, 575, 402, 20);
		
		out.setAcceptingInput(false);
		in.setMaxLength(20);
		
		bg   = new Image("res/play_bg.png");
		gbg  = new Image("res/game_bg.png");
		mole_r = new Image("res/mole.png");
		mole_g = new Image("res/mole_g.png");
		mole_b = new Image("res/mole_b.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(gbg, 256, 0);
		g.drawString(mouse, 10, 580);
		g.drawString(String.valueOf(time), 15, 400);
		
		for(HitBox b: game.getMoles()) {
			if(b.getUp() == 1) {
				switch(b.getType()) {
				case GameConfig.M_REG:
					g.drawImage(mole_r, b.x, GameConfig.HEIGHT-b.y);
					break;
				case GameConfig.M_GOLD:
					g.drawImage(mole_g, b.x, GameConfig.HEIGHT-b.y);
					break;
				case GameConfig.M_BLACK:
					g.drawImage(mole_b, b.x, GameConfig.HEIGHT-b.y);
					break;
				}
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
		
		out.render(gc, g);
		in.render(gc, g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse at x: " + xpos + " y: " + ypos;
		
		if(in.hasFocus() && input.isKeyPressed(Input.KEY_ENTER)) {
			chatClient.send(in.getText());
			in.setText("");
		}
		
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
	
	public void countDown() {
		final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	time--;
                if (time<0)
                    timer.cancel();
            }
        }, 0, 1000);
	}
	
	public void addChatClient() {
		this.chatClient = new Client(player.name, this);
		this.chatClient.start();
	}
	
	public void receive(String msg) {
		if(out.getText().equals("")) {
			out.setText(msg);
		} else {
			msg = out.getText() + "\n" + msg;
			
			String[] temp = msg.split("\n");
			if(temp.length == 8) {
				msg = "";
				
				for(int i=1; i<temp.length-1; i++) {
					msg += temp[i] + "\n";
				}
				msg += temp[temp.length-1];
			}
			
			out.setText(msg);
		}
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void start(String address, int port) {
		inst = new PlayerThread(game, address, port);
		inst.start();
	}
	
}
