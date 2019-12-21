package tilegame.entities.staticEntities;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.worlds.World;

public class Heart extends StaticEntity {
	private Animation idleAnimation;
	private Animation currentAnimation;
	
	public Heart(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		idleAnimation = new Animation(Assets.heart, 150);
		currentAnimation = idleAnimation;
		// TODO Auto-generated constructor stub
	}
	
	public void tick(){
		collisionWithPlayer();
		idleAnimation.tick();
	}
	
	public void render(Graphics g){
		currentAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), 
				width, height);	
	}
	
	public boolean collisionWithPlayer(){
		Player player = handler.getPlayer();
		if (player.getMapIndex().equals(World.getMapIndex(getX(), getY()))){
			collisionAction();
			return true;
		}
		if ( x < player.getX() + player.getBounds().getWidth() && x > player.getX() - player.getBounds().getWidth() &&
				y < player.getY() + player.getBounds().getHeight() && y > player.getY() - player.getBounds().getHeight()){
			collisionAction();
			return true;
		}
		return false;
	}
	
	private void collisionAction(){
		Player.heartsChanged = true;
		Player.changeGoal = true;
		Player.heartsCollected++;
		handler.getWorld().getEntityManager().getStaticEntities().remove(this);
		
	}
}
