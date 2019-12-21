package tilegame.menu.buttons;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.input.MouseManager;

public class Button {
	
	
	protected BufferedImage image, imageHover,currentImage;
	protected Rectangle collisionBox;
	protected Handler handler;
	
	public Button(Handler handler, BufferedImage image, int x, int y, int width, int height){
		
		this.handler = handler;
		this.image = image;
		this.imageHover = image;
		this.currentImage = image;
		collisionBox = new Rectangle(x,y,width,height);
	}
	

	public Button(Handler handler, BufferedImage image, BufferedImage imageHover, int x, int y, int width, int height){
		
		this.handler = handler;
		this.image = image;
		this.imageHover = imageHover;
		collisionBox = new Rectangle(x,y,width,height);
	}
	
	public void tick(){
		actionTick();
	}
	
	public void render(Graphics g){
		
		g.drawImage(currentImage, collisionBox.x, collisionBox.y, null);
		
	}
	
	public boolean isMouseOver(){
		Point mouseLocation = MouseManager.getMouseLocation();
		if (collisionBox.contains(mouseLocation)){	
			currentImage = imageHover;
			return true;
		} else {
			currentImage = image;
			return false;
		}
	}
	
	public void actionTick(){
		if (isMouseOver()){
			if (MouseManager.leftButton){
				action();
			}
		}
	}
	
	public void action(){
		
	}
}
