package tilegame.menu.buttons;

import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.states.State;

public class StartButton extends Button{

	
	public StartButton(Handler handler, BufferedImage image, int x, int y, int width, int height) {
		super(handler, image, x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public StartButton(Handler handler, BufferedImage image, BufferedImage hoverImage, int x, int y, int width, int height) {
		super(handler, image, hoverImage, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void action(){
		State.setState(handler.getGame().getGameState());
	}
	
}
