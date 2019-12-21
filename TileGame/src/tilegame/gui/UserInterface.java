package tilegame.gui;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Assets;

public class UserInterface {
	
	public static final int DEFAULT_UI_HEIGHT = 75;
	private Handler handler;
	
	private int maxHealth;
	private int currentHealth;
	
	private int currentExp;
	private int nextLevelExp;
	private int level;
	
	private int screenHeight;
	private int screenWidth;
	
	
	public UserInterface(Handler handler){
		this.handler = handler;
		maxHealth = Player.DEFAULT_HEALTH;
		currentHealth = Player.DEFAULT_HEALTH;
		
		nextLevelExp = 200;
		currentExp = 0;
		level = 1;
		
		screenHeight = handler.getGame().getDisplay().getFrame().getHeight();
		screenWidth = handler.getGame().getDisplay().getFrame().getWidth();
	}
	
	public void tick(){
		
		maxHealth = handler.getPlayer().getMaxHealth();
		currentHealth = handler.getPlayer().getHealth();
		
		nextLevelExp = handler.getPlayer().getNextLevelExp();
		currentExp = Player.experience;
		level = handler.getPlayer().getLevel();
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.black);
		g.fillRect( 0 , screenHeight - DEFAULT_UI_HEIGHT, screenWidth, screenHeight);
		
		renderPlayerHealth(g);
		renderPlayerExp(g);
	}
	private void renderPlayerExp(Graphics g){
		float percentage = (float)currentExp/(float)nextLevelExp;
		g.setColor(Color.white);
		g.fillRect(screenWidth / 3 + 100, screenHeight - DEFAULT_UI_HEIGHT, screenWidth / 3, screenHeight);
		g.setColor(Color.yellow);
		g.fillRect(screenWidth / 3 + 100, screenHeight - DEFAULT_UI_HEIGHT, (int) (screenWidth / 3 * percentage), screenHeight);
		String level = "Level " + this.level;
		g.setFont(Assets.largeFont);
		g.drawString(level,screenWidth * 2/ 3 + 110, screenHeight - DEFAULT_UI_HEIGHT + 30);
	}
	
	private void renderPlayerHealth(Graphics g){
		float percentage = (float)currentHealth/(float)maxHealth;
		g.setColor(Color.white);
		g.fillRect(0, screenHeight - DEFAULT_UI_HEIGHT, screenWidth / 3, screenHeight);
		g.setColor(Color.red);
		g.fillRect(0, screenHeight - DEFAULT_UI_HEIGHT,(int) (screenWidth / 3 * percentage), screenHeight);
		String health = currentHealth + "/" + maxHealth;
		g.setFont(Assets.largeFont);
		g.drawString(health,screenWidth / 3 + 10, screenHeight - DEFAULT_UI_HEIGHT + 30);
	}
}
