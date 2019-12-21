package tilegame.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.gfx.Assets;

public class Particle {

	//pazi ovde
	protected static BufferedImage texture = Assets.menuStart;
	protected float x, y;
	protected int width, height;
	protected float xv,yv;
	protected float life, maxLife;
	
	public Particle(float x, float y, int width, int height, float xv, float yv, float maxLife){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xv = xv;
		this.yv = yv;
		this.maxLife = maxLife;
		life = 0;

	}
	
	public void render(Graphics g){
		
		g.fillOval((int)x, (int)y, width + 1, height + 1);
		g.fillOval((int)x, (int)y, width, height);
		
	}
	
	public boolean tick(){
		x += xv/50;
		y += yv/50;
		life += 0.1;
		if (life > maxLife){
			return true;
		}
		return false;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
