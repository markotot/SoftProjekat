package tilegame.entities.creatures.npcs;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.creatures.Creature;
import tilegame.entities.creatures.Player;
import tilegame.entities.creatures.bosses.SkeletonBoss;
import tilegame.entities.weapons.GoldenSword;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.input.KeyManager;

public class QuestGiver extends Creature {
	
	boolean questStarted;
	boolean questFinished;
	boolean finished;
	
	private Animation idleAnimation;
	private Animation waitingAnimation;
	
	public QuestGiver(Handler handler, float x, float y){
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		questStarted = false;
		questFinished = false;
		finished = false;
		idleAnimation = new Animation(Assets.questGiver2Idle, 500);
		waitingAnimation = new Animation(Assets.questGiver2Waiting, 500);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
		idleAnimation.tick();
		waitingAnimation.tick();
		collisionWithPlayer();
		questTicker();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (questStarted){
			idleAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
					(int)(y - handler.getGameCamera().getyOffset()), 
					width, height);		
		} else {
			waitingAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
					(int)(y - handler.getGameCamera().getyOffset()), 
					width, height);	
		}
	}
	
	@Override
	public void collisionAction(){
		if (!questStarted && KeyManager.space){
			System.out.println("Kill a monester and i will give you my sword");
			questStarted = true;
		} else if (questFinished && KeyManager.space && !finished){
			finished = true;
			System.out.println("Thank you my friend, here you go, the promised reward");
			Player.experience+= 150;
			handler.getPlayer().setWeapon(new GoldenSword(handler, handler.getPlayer().getX(), handler.getPlayer().getY(), handler.getPlayer().getWidth() / 2, handler.getPlayer().getHeight() / 2));
			handler.getWorld().getEntityManager().addCreature(new SkeletonBoss(handler, 505, 300));
		}
		
		
	}
	
	private void questTicker(){
		if (Player.killCount >= 1){
			
			questFinished = true;
		}
	}
	
	
	
}
