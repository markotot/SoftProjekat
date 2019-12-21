package tilegame.entities.creatures.bosses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import tilegame.Handler;
import tilegame.display.Display;
import tilegame.entities.creatures.Creature;
import tilegame.entities.staticEntities.Fire;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;

public class SkeletonBoss extends Creature {
	public enum PHASE {first, second, third};
	
	private PHASE phase;
	
	private Animation firstPhaseAnimation;
	private Animation secondPhaseAnimation;
	private Animation thirdPhaseAnimation;
	
	private Animation currentAnimation;
	
	private long lastNuke;
	private long lastNukeDmg;
	private Rectangle nuke;
	private Boolean drawNuke;
	private ArrayList<Rectangle> nukes = new ArrayList<Rectangle>();
	
	public SkeletonBoss(Handler handler, float x, float y){
		super(handler, x, y, (int) (Creature.DEFAULT_CREATURE_WIDTH * 1.5), (int) (Creature.DEFAULT_CREATURE_HEIGHT * 1.5));
		
		health = DEFAULT_HEALTH * 2;
		maxHealth = DEFAULT_HEALTH * 2;
		phase = PHASE.first;
		firstPhaseAnimation = new Animation(Assets.skeletonBossFirst, 500);
		secondPhaseAnimation = new Animation(Assets.skeletonBossSecond, 500);
		thirdPhaseAnimation = new Animation(Assets.skeletonBossThird, 500);
		
		currentAnimation = firstPhaseAnimation;
		lastNukeDmg = System.nanoTime();
		lastNuke = System.nanoTime();
		nuke = new Rectangle(0, 0, 0, 0);
		drawNuke = false;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		selectPhase();
		selectAnimation();
		tickAnimation();
		tickCollisionWithFire();
		chooseAction();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		renderAnimation(g);
		renderHealth(g);
		renderNukes(g);
	}
	
	private void renderNukes(Graphics g){
		g.setColor(Color.red);
		/*for (Rectangle nuke : nukes) {
			g.fillRect(nuke.x - (int)handler.getGameCamera().getxOffset(),nuke.y - (int)handler.getGameCamera().getyOffset(),nuke.width,nuke.height);
		}
		
		
		if (drawNuke){
			System.out.println("CRTAM");
			drawNuke(g);
		}
		*/
		
	}
	/*
	private void drawNuke(Graphics g){
		g.setColor(Color.yellow);
		g.fillRect(nuke.x - (int)handler.getGameCamera().getxOffset(),nuke.y - (int)handler.getGameCamera().getyOffset(),nuke.width,nuke.height);
		drawNuke = false;
	}
	*/
	
	private void chooseAction(){
		switch (phase){
		case first: firstPhaseAction();
					break;
		case second: secondPhaseAction();
					 break;
		case third: thirdPhaseAction();
					break;
		default: System.out.println("ERROR: POGRESNA FAZA BOSSA");
				 break;
		
		
		}
	}
	
	private void firstPhaseAction(){
		handler.getGame().getDisplay();
		int width = Display.getCanvas().getWidth();
		int height = Display.getCanvas().getHeight();
		long currTime = System.nanoTime();
		long seconds = 1000000000;
		if ( currTime - lastNuke > seconds * 3){
			Random r = new Random();
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			System.out.println("X:" + x + " Y:" + y);
			
			lastNuke = currTime;
			activateLastNuke();
			nuke = new Rectangle(x - 20, y - 20, 40, 40);
			drawNuke = true;

		}
		
	}
	private void activateLastNuke(){
			handler.getWorld().getEntityManager().getStaticEntities().add(new Fire(handler, nuke.x, nuke.y, nuke.width, nuke.height));
			nukes.add(nuke);
	}
	
	private void secondPhaseAction(){
		
	}
	
	private void thirdPhaseAction(){
		
	}
	private void tickAnimation(){
		firstPhaseAnimation.tick();
		secondPhaseAnimation.tick();
		thirdPhaseAnimation.tick();
		currentAnimation.tick();
	}
	
	private void tickCollisionWithFire(){
		
		long currTime = System.nanoTime();
		
		
		if (currTime - lastNukeDmg > 1000000000){
			for (Rectangle nuke : nukes) {
				if (nuke.intersects(handler.getPlayer().getAbsoluteBounds())){
					handler.getPlayer().takeDamage(1);
					lastNukeDmg = currTime;
					break;
				}
			}
		}
		
	}
	
	public void dispose(){
		
		
		this.setX(-500);
		this.setY(-500);
		this.setSpeed(0);
	}
	
	private void renderAnimation(Graphics g){
		currentAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
					(int)(y - handler.getGameCamera().getyOffset()), 
					width, height);	
	}
	
	private void selectPhase(){
		if (health > maxHealth * 2 / 3){
			phase = PHASE.first;
		} else if (health > maxHealth / 3){
			phase = PHASE.second;
		} else {
			phase = PHASE.third;
		}
	}
	
	private void selectAnimation(){
		switch (phase) {
		case first:
			currentAnimation = firstPhaseAnimation;
			break;
		case second:
			currentAnimation = secondPhaseAnimation;
			break;
		case third:
			currentAnimation = thirdPhaseAnimation;
			break;
		default:
			break;
		}
	}
}
