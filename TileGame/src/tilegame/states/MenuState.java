package tilegame.states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Assets;
import tilegame.menu.buttons.ExitButton;
import tilegame.menu.buttons.StartButton;

public class MenuState extends State {
	
	private BufferedImage background;
	
	private StartButton startButton;
	private ExitButton exitButton;
	
	public MenuState(Handler handler) {
		super(handler);
		background = Assets.menuBackground;
		startButton = new StartButton(handler, Assets.menuStart, Assets.menuStartHover, 255, 185, Assets.menuStart.getWidth(), Assets.menuStart.getHeight());
		exitButton = new ExitButton(handler, Assets.menuExit, Assets.menuExitHover, 275, 285, Assets.menuExit.getWidth(), Assets.menuExit.getHeight());
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		// disable attack modes
		disableAttackModes();
		startButton.tick();
		exitButton.tick();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		renderBackground(g);
		renderButtons(g);
		
	}
	
	private void renderBackground(Graphics g){

		g.drawImage(background, 0,0,handler.getWidth(), handler.getHeight(), null);
	}
	
	private void renderButtons(Graphics g){
		startButton.render(g);
		exitButton.render(g);
	}
	
	public void disableAttackModes(){
		Player.disableAttackMode();
	}
 
}
