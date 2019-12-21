package tilegame.entities.weapons;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

public class MetalSword extends Weapon{

	public MetalSword(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		damage = 20;
		
		idleLeft = new Animation(Assets.swordLeftIdle, 500);
		idleRight = new Animation(Assets.swordRightIdle, 500);
		idleUp = new Animation(Assets.swordUpIdle, 500);
		idleDown = new Animation(Assets.swordDownIdle, 500);
		
		attackDown = new Animation(Assets.swordDownAttack, 500);
		attackUp = new Animation(Assets.swordUpAttack, 100);
		attackLeft = new Animation(Assets.swordLeftAttack, 100);
		attackRight = new Animation(Assets.swordRightAttack, 100);
		
		currentAnimation = idleDown;
		
		// TODO Auto-generated constructor stub
	}
}
