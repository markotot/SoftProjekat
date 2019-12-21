package tilegame.entities.weapons;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

public class GoldenSword extends Weapon {
	
	public GoldenSword(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		damage = 6;
		
		idleLeft = new Animation(Assets.swordGoldLeftIdle, 500);
		idleRight = new Animation(Assets.swordGoldRightIdle, 500);
		idleUp = new Animation(Assets.swordGoldUpIdle, 500);
		idleDown = new Animation(Assets.swordGoldDownIdle, 500);
		
		attackDown = new Animation(Assets.swordGoldDownIdle, 100);
		attackUp = new Animation(Assets.swordGoldUpAttack, 100);
		attackLeft = new Animation(Assets.swordGoldLeftAttack, 100);
		attackRight = new Animation(Assets.swordGoldRightAttack, 100);
		
		currentAnimation = idleDown;
		
	}
}
