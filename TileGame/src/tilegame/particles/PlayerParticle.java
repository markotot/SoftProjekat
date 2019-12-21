package tilegame.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import tilegame.Handler;
import tilegame.entities.creatures.Player;

public class PlayerParticle extends Particle {

	private Handler handler;
	private Player player;
	Rectangle lastTime;
	public PlayerParticle(Handler handler, float x, float y, int width, int height, float xv, float yv, float maxLife) {
		super(x, y, width, height, xv, yv, maxLife);
		this.handler = handler;
		player = handler.getPlayer();
		lastTime = new Rectangle((int)player.getX() + player.getWidth()/2, (int)player.getY() + player.getHeight()/2, 5, 5);
		
		
		this.handler = handler;
	}
	
	public boolean tick(){
		
		lastTime.x+= xv/50;
		lastTime.y+=yv/50;
		life += 0.1;
		if (life > maxLife){
			return true;
		}
		return false;
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.orange);
		g.fillRect((int)(lastTime.x - handler.getGameCamera().getxOffset() - 1), (int)(lastTime.y - handler.getGameCamera().getyOffset() - 1), lastTime.width + 1 , lastTime.height + 1);
		
		g.setColor(Color.yellow);
		g.fillRect((int)(lastTime.x - handler.getGameCamera().getxOffset()), (int)(lastTime.y - handler.getGameCamera().getyOffset()), lastTime.width, lastTime.height);

	}

}
