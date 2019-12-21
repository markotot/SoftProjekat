package tilegame.menu.buttons;

import java.awt.image.BufferedImage;

import tilegame.Handler;

public class ExitButton extends Button{
	public ExitButton(Handler handler, BufferedImage image, int x, int y, int width, int height) {
		super(handler, image, x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public ExitButton(Handler handler, BufferedImage image, BufferedImage imageHover, int x, int y, int width, int height) {
		super(handler, image, imageHover, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void action(){
		handler.exitGame();
	}
}
