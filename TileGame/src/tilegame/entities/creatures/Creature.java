package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.creatures.enemies.Skeleton.OKRENUT;
import tilegame.entities.weapons.MetalSword;
import tilegame.entities.weapons.Weapon;
import tilegame.particles.ParticleEmitter;
import tilegame.tile.Tile;

public abstract class Creature extends Entity {

	public static final int DEFAULT_HEALTH = 20;
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATURE_HEIGHT = 64;
	public static final int DEFAULT_CREATURE_SIGHT = 92;
	public static final int DEFAULT_CREATURE_DAMAGE = 1;
	public static final int DEFAULT_EXPERIENCE_VALUE = 100;
	public static int DEFAULT_SIGHT_MODIFIER = 0;
	
	protected ParticleEmitter emitter;
	
	protected int maxHealth;
	protected int health;
	protected float speed;
	protected float xMove, yMove;
	
	// HP stuff
	protected long healthLastTime;
	protected boolean showHealth;
	protected Weapon weapon;
	protected static boolean ableToAttack;
	protected boolean dead;
	// Player Collision
	protected long playerCollisionLastTime;
	
	// Movement
	protected OKRENUT strana;
	
	// METODE
	public Creature(Handler handler, float x, float y, int width, int height) {
		
		super(handler, x, y, width, height);
		emitter = new ParticleEmitter(0, Color.red, (int)(x + width/2 - handler.getGameCamera().getxOffset()),
				(int)(y + height/2 - handler.getGameCamera().getyOffset()), 5, 5, 40, 80, 0, 360, 1, 2);
		
		
		health = DEFAULT_HEALTH;
		maxHealth = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		healthLastTime = System.nanoTime();
		playerCollisionLastTime = System.nanoTime();
		showHealth = false;
		dead = false;
		
		// kreiranje oruzja
		weapon = new MetalSword(handler, x, y, width / 2, height / 2);
		ableToAttack = true;
	}
	
	public void move(){
		moveX();
		moveY();
	}
	
	public void moveX(){
		if (xMove > 0){ // Move left
			
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
				x += xMove;
			} else {
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
			}
			
		} else if (xMove < 0){ // Moving left
			
			int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
				x += xMove;
			} else {
				x = tx * Tile.TILE_WIDTH +Tile.TILE_WIDTH - bounds.x;
			}
			
		}
	}
	
	public void moveY(){
		if (yMove < 0){
			
			int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH,ty)){
				y += yMove;
			} else {
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
			}
			
		} else if (yMove > 0){
			
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH,ty)){
				y += yMove;
			} else {
				y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
			}
			
		
			
		}
	}
	
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	protected boolean collisionWithPlayer(){
		Player player = handler.getPlayer();
	
		if ( x < player.getX() + player.getBounds().getWidth() && x > player.getX() - player.getBounds().getWidth() &&
				y < player.getY() + player.getBounds().getHeight() && y > player.getY() - player.getBounds().getHeight()){
			collisionAction();
			return true;
		}
		return false;
	}
	
	protected boolean nearPlayer(){
		Player player = handler.getPlayer();
		int range = DEFAULT_CREATURE_SIGHT + DEFAULT_SIGHT_MODIFIER;
		
		if ( x < player.getX() + player.getBounds().getWidth() + range && x > player.getX() - player.getBounds().getWidth() - range &&
				y < player.getY() + player.getBounds().getHeight() + range && y > player.getY() - player.getBounds().getHeight() - range){
			return true;
		}
		return false;
	}
	
	protected void collisionAction(){
		
		long currentTime = System.nanoTime();
		long second = 1000000000;
		if (currentTime - playerCollisionLastTime > second){
			collisionDealDamage();
			playerCollisionLastTime = currentTime;
		}
	}
	
	public void collisionDealDamage(){
		handler.getPlayer().takeDamage(DEFAULT_CREATURE_DAMAGE);
		
	}
	
	public void takeDamage(int x){
		health -= x;
		emitter = getCreatureBloodEmitter();
		if (health <= 0){
			
			health = 0;
			if (this.getClass() != Player.class){
				Player.experience += DEFAULT_EXPERIENCE_VALUE;
				Player.killCount++;
			}
			dead = true;
		}
		
	}
	
	public ParticleEmitter getCreatureBloodEmitter(){ 	
		return new ParticleEmitter(10, Color.red, (int)(x + width/2 - handler.getGameCamera().getxOffset()),
				(int)(y + height/2 - handler.getGameCamera().getyOffset()), 5, 5, 40, 80, 0, 360, 1, 2);
	} 
	
	public void dispose(){
		this.setX(-500);
		this.setY(-500);
		this.setSpeed(0);
	}
	
	public void hideHealth(){
		long currentTime = System.nanoTime();
		long second = 1000000000;
		if (currentTime - healthLastTime > second * 5){
			showHealth = false;
			healthLastTime = currentTime;
		}
	}
	
	public void showHealth(){
		showHealth = true;
		healthLastTime = System.nanoTime();
	}
	
	public void renderHealth(Graphics g){
		float percentage = (float)health/(float)maxHealth;
		if (showHealth){
			g.setColor(Color.black);
			g.fillRect((int)(x - handler.getGameCamera().getxOffset()),
					(int)(y - 12 - handler.getGameCamera().getyOffset()), width, 10);
			
			g.setColor(Color.white);
			g.fillRect((int)(x - handler.getGameCamera().getxOffset() + 2),
					(int)(y - 10 - handler.getGameCamera().getyOffset()), width - 4, 6);
			
			g.setColor(Color.red);
			g.fillRect((int)(x - handler.getGameCamera().getxOffset() + 2),
					(int)(y - 10 - handler.getGameCamera().getyOffset()), (int) (width * percentage) - 4 , 6);
			
			
		}
	}
	
	protected void moveToLocation(Point p){
		float targetX = (float) p.getX();
		float targetY = (float) p.getY();
		/*
		if (collisionWithPlayer()){
			speed = 1;
		} else {
			speed = 1;
		}
		*/
		if (x  - targetX > 4 ){
			xMove = - speed;
			moveX();
			strana = OKRENUT.levo;
		} else if (x - targetX < 4) {
			xMove = speed;
			moveX();
			strana = OKRENUT.desno;
		}
		
		if (y - targetY > 4 ){
			yMove = - speed;
			moveY();
			if (Math.abs(y - targetY) > Math.abs(x - targetX)){
				strana = OKRENUT.gore;
			}

		} else if(y - targetY < 4){
			yMove = speed;
			moveY();
			if (Math.abs(y - targetY) > Math.abs(x - targetX)){
				strana = OKRENUT.dole;
			}
			
		}
	}
	
	

	//GETTERS && SETTERS
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isAbleToAttack() {
		return ableToAttack;
	}
	
}
