package tilegame.entities.staticEntities;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

public class Fire extends StaticEntity{

	private Animation idleAnimation;
	private Animation currentAnimation;
	
	public Fire(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		idleAnimation = new Animation(Assets.fire, 150);
		currentAnimation = idleAnimation;
		// TODO Auto-generated constructor stub
	}
	
	public void tick(){
		idleAnimation.tick();
	}
	
	public void render(Graphics g){
		currentAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), 
				width, height);	
	}

}
