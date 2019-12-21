package tilegame.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import tilegame.Handler;

public abstract class Entity {
	
	protected Handler handler;
	protected float x,y;
	protected final float originX, originY;
	protected int width, height;
	protected Rectangle bounds;
	
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		originX = x;
		originY = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
	}

	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public Rectangle getAbsoluteBounds(){
		
		return new Rectangle((int)(getX() + getBounds().getX()), (int)(getY() + getBounds().getY()), 
				(int)(getBounds().getWidth()), (int)(getBounds().getHeight()));
	}
	
	public Point getMapIndex(){
		
		int i = handler.getWorld().getMapIndex(x, y).x;
		int j = handler.getWorld().getMapIndex(x, y).y;
		
		return new Point(i,j);
	}

	
	// GETTERS && SETTERS
	
	public float getX() {
		return x;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
