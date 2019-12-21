package tilegame.entities.weapons;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Animation;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;

public abstract class Weapon extends Entity {
	
	public enum STRANA_NAPADA {gore, dole, levo, desno};
	
	public static final int DEFAULT_ATTACK_RANGE = 50;
	public static final int DEFAULT_ATTACK_SPEED = 1;
	public static final int DEFAULT_DAMAGE = 2;
	
	protected Animation currentAnimation;
	protected Animation idleLeft, idleRight, idleUp, idleDown;
	protected Animation attackLeft, attackRight, attackUp, attackDown;
	
	protected boolean attackStarted;
	protected boolean currentlyAttacking;
	protected boolean dealtDamage;
	protected long lastAttackTime;
	protected STRANA_NAPADA stranaNapada;
	
	protected float attackSpeed;
	protected float attackRange;
	protected int damage;
	
	protected Rectangle currentBounds;
	
	public Weapon(Handler handler, float x, float y, int width, int height){
		super(handler, x, y, width, height);
		damage = 2;
		
		attackStarted = false;
		lastAttackTime = System.nanoTime();
		stranaNapada = STRANA_NAPADA.dole;
		dealtDamage = true;
		
		attackRange = DEFAULT_ATTACK_RANGE;
		attackSpeed = DEFAULT_ATTACK_SPEED;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 30;
		bounds.height = 60;
		
		currentlyAttacking = false;
		currentAnimation = idleDown;
		currentBounds = new Rectangle(bounds);
	}
	
	public void setWeaponPosition(){
		Player player = handler.getPlayer();
		x = player.getX() + player.getWidth() / 2 - width / 2;
		y = player.getY() + player.getHeight() / 2 - height / 2;
		setWeaponBounds();
	}
	
	public void setWeaponBounds(){
		
		String lastKey = handler.getPlayer().getLastKey();
		if (attackStarted){
			setStranaNapada();
		}
		
		if (currentlyAttacking){
			if (stranaNapada == STRANA_NAPADA.dole){
				
				bounds.x = (int)(x - bounds.width / 4);
				bounds.y = (int)(y - bounds.height / 4 + DEFAULT_ATTACK_RANGE);
				setCurrentBounds();
			} else if (stranaNapada == STRANA_NAPADA.gore) {
				bounds.x = (int)(x - bounds.width / 4);
				bounds.y = (int)(y - bounds.height / 4 - DEFAULT_ATTACK_RANGE);
				setCurrentBounds();
			} else if (stranaNapada == STRANA_NAPADA.levo){
				bounds.x = (int)(x  - bounds.width / 4- DEFAULT_ATTACK_RANGE);
				bounds.y = (int)y - bounds.height / 4;
				setCurrentBounds();
			} else if (stranaNapada == STRANA_NAPADA.desno){
				bounds.x = (int)(x - bounds.width/4  + DEFAULT_ATTACK_RANGE);
				bounds.y = (int)y - bounds.height / 4;
				setCurrentBounds();
			} else {
				bounds.x = (int)x  - bounds.width / 4;
				bounds.y = (int)y - bounds.height / 4;
			}
		} else {
			if (lastKey.equals("down")){
				bounds.x = (int)(x - bounds.width / 4);
				bounds.y = (int)(y - bounds.height / 4 + DEFAULT_ATTACK_RANGE);
				setCurrentBounds();
				
			} else if (lastKey.equals("up")) {
				bounds.x = (int)(x - bounds.width / 4);
				bounds.y = (int)(y - bounds.height / 4 - DEFAULT_ATTACK_RANGE);
				setCurrentBounds();
				
			} else if (lastKey.equals("left")){
				bounds.x = (int)(x  - bounds.width / 4- DEFAULT_ATTACK_RANGE);
				bounds.y = (int)y - bounds.height / 4;
				setCurrentBounds();
				
			} else if (lastKey.equals("right")){
				bounds.x = (int)(x - bounds.width/4  + DEFAULT_ATTACK_RANGE);
				bounds.y = (int)y - bounds.height / 4;
				setCurrentBounds();
				
			} else {
				bounds.x = (int)x;
				bounds.y = (int)y;
			} 
			
			
		}

		
		return;
	}	

	public boolean collisionWithEntity(Entity entity){
		return currentBounds.intersects(entity.getAbsoluteBounds());
	}
	
	public void tick(){
		startAttack();
		setWeaponPosition();
		tickAnimation();
	}
	
	private void tickAnimation(){
		
		setCurrentAnimation();
		
		idleDown.tick();
		idleUp.tick();
		idleLeft.tick();
		idleRight.tick();
		//attackLeft.tick();
		//attackRight.tick();
		//attackUp.tick();
		attackDown.tick();
		
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		// render weapon
/*		g.setColor(Color.blue);
		g.fillRect((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()),
				width, height);
		
		// render weapon collision box
		g.setColor(Color.green);
		g.fillRect((int)(getCurrentBounds().x - handler.getGameCamera().getxOffset()), (int)(getCurrentBounds().y - handler.getGameCamera().getyOffset()),
				getCurrentBounds().width, getCurrentBounds().height);*/
		// Za soft iskljuceno
		/*currentAnimation.render(g,(int)(getCurrentBounds().x - handler.getGameCamera().getxOffset()), (int)(getCurrentBounds().y - handler.getGameCamera().getyOffset()),
				getCurrentBounds().width, getCurrentBounds().height);
		*/
				
	}

	public void startAttack(){

		long currentTime = System.nanoTime();
		if (currentTime - lastAttackTime > 1000000000 * attackSpeed){
			if ((MouseManager.leftButton || KeyManager.space) && handler.getPlayer().isAbleToAttack()){
				
				lastAttackTime = currentTime;
				dealtDamage = false;
				attackStarted = true;
				currentlyAttacking = true;
				handler.getPlayer().setSpeed(2f);
			} else {
				handler.getPlayer().setSpeed(3f);
				currentlyAttacking = false;
			}
		}
	}
	
	public void setStranaNapada(){
		Point mousePoint = MouseManager.getMouseLocation();
		Player player = handler.getPlayer();
		Point playerPoint = new Point( (int) (player.getX() - handler.getGameCamera().getxOffset() + player.getWidth()/2) , (int) (player.getY() - handler.getGameCamera().getyOffset()+ player.getHeight()/2));
		attackStarted = false;
		
		if (playerPoint.y <= mousePoint.y){
			stranaNapada = STRANA_NAPADA.dole;
			
		} else {
			stranaNapada = STRANA_NAPADA.gore;
		}
		
		if (playerPoint.x <= mousePoint.x){
			if (Math.abs(playerPoint.x - mousePoint.x) > Math.abs(playerPoint.y - mousePoint.y)){
				stranaNapada = STRANA_NAPADA.desno;
			}
		} else {
			if (Math.abs(playerPoint.x - mousePoint.x) > Math.abs(playerPoint.y - mousePoint.y)){
				stranaNapada = STRANA_NAPADA.levo;
			}
		}
	}
	
	public void setCurrentAnimation(){
		// radi samo za playerov weapon, za creature bi morao dodati animacije u Creature klasu
		String lastKey = handler.getPlayer().getLastKey();
		if (!currentlyAttacking){
			if (lastKey.equals("up")){
				currentAnimation = idleUp;
			} else if (lastKey.equals("down")){
				currentAnimation = idleDown;
			} else if (lastKey.equals("left")){
				currentAnimation = idleLeft;
			} else if (lastKey.equals("right")){
				currentAnimation = idleRight;
			} else {
				currentAnimation = idleDown;
			}
		} else {
			if (stranaNapada == STRANA_NAPADA.gore){
				currentAnimation = idleUp;
			} else if (stranaNapada == STRANA_NAPADA.dole){
				System.out.println("dole");
				currentAnimation = attackDown;
			} else if (stranaNapada == STRANA_NAPADA.levo){
				currentAnimation = idleLeft;
			} else if (stranaNapada == STRANA_NAPADA.desno){
				currentAnimation = idleRight;
			} else {
				currentAnimation = idleDown;
			}
		}
		
	}
	
	// GETTERS && SETTERS
	
	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}
	
	public void setCurrentBounds(){
		currentBounds = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	public void setCurrentBounds(int x, int y, int width, int height){
		currentBounds = new Rectangle(x, y, width, height);
	}

	public Rectangle getCurrentBounds() {
		return currentBounds;
	}

	public void setCurrentBounds(Rectangle currentBounds) {
		this.currentBounds = currentBounds;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isCurrentlyAttacking() {
		return currentlyAttacking;
	}

	public void setCurrentlyAttacking(boolean currentlyAttacking) {
		this.currentlyAttacking = currentlyAttacking;
	}

	public STRANA_NAPADA getStranaNapada() {
		return stranaNapada;
	}

	public void setStranaNapada(STRANA_NAPADA stranaNapada) {
		this.stranaNapada = stranaNapada;
	}

	public boolean isDealtDamage() {
		return dealtDamage;
	}

	public void setDealtDamage(boolean dealtDamage) {
		this.dealtDamage = dealtDamage;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
	
}

