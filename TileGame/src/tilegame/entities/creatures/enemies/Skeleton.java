package tilegame.entities.creatures.enemies;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import tilegame.Handler;
import tilegame.entities.creatures.Creature;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.pathfinding.AStar;
import tilegame.pathfinding.AreaMap;
import tilegame.pathfinding.State;
import tilegame.worlds.World;



public class Skeleton extends Creature {
	
	public enum OKRENUT {stoji, gore, dole, levo, desno};
	
	boolean hasQuest;
	boolean questFinished;
	
	// A* pathFinding
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Point> fullPath = new ArrayList<Point>();
	private long lastTimeAstar; 
	private boolean changedAstar;
	private int distanceToPlayer;
	
	//movement
	int patrolDown;
	long lastTime;
	//movement
	
	private Animation idleAnimation;
	private Animation waitingAnimation;
	private Animation currentAnimation;
	
	private Animation upAnimation, downAnimation, rightAnimation, leftAnimation;
	
	public Skeleton(Handler handler, float x, float y){
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		hasQuest = true;
		questFinished = false;
		patrolDown = 0;
		speed = 1.8f;
		lastTime = System.nanoTime();
		strana = OKRENUT.stoji;
		
		lastTimeAstar = System.nanoTime();
		changedAstar = false;
		distanceToPlayer = 99;
		
		bounds.x = 20;
		bounds.y = 20;
		bounds.width = 24;
		bounds.height = 30;
		
		idleAnimation = new Animation(Assets.questGiver1Idle, 500);
		waitingAnimation = new Animation(Assets.questGiver1Waiting, 500);
		upAnimation = new Animation(Assets.questGiver1Up, 500);
		downAnimation = new Animation(Assets.questGiver1Down, 500);
		leftAnimation = new Animation(Assets.questGiver1Left, 500);
		rightAnimation = new Animation(Assets.questGiver1Right, 500);
		currentAnimation = idleAnimation;
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		setCurrentAnimation();
		emitter.tick();
		tickAnimation();
		collisionWithPlayer();
		hideHealth();
		chooseAction();
		
		checkDead();
	}
	
	private void tickAnimation(){
		idleAnimation.tick();
		waitingAnimation.tick();
		upAnimation.tick();
		downAnimation.tick();
		leftAnimation.tick();
		rightAnimation.tick();
	}
	
	private void setCurrentAnimation(){
		switch (strana){
		case dole:{
			currentAnimation = downAnimation;
			break;
		}
		case gore:{
			currentAnimation = upAnimation;
			
			break;
		}
		case desno:{
			currentAnimation = rightAnimation;
			break;
		}
		case levo:{
			currentAnimation = leftAnimation;
			break;
		}
		case stoji:{
			currentAnimation = idleAnimation;
			break;
		}
		default:
			currentAnimation = upAnimation;
			break;
		}
	}

	private void renderAnimation(Graphics g){
		currentAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
					(int)(y - handler.getGameCamera().getyOffset()), 
					width, height);	
		
		//g.setColor(Color.green);
		//g.fillRect((int)(getX() + getBounds().x - handler.getGameCamera().getxOffset()), (int)(getY() + getBounds().y - handler.getGameCamera().getyOffset()),
		//		getBounds().width, getBounds().height);
	}

	@Override
	public void render(Graphics g) {
		
		renderAnimation(g);
		renderEmitter(g);
		renderHealth(g);
	}
	
	public void renderEmitter(Graphics g){
		emitter.render(g);
	}
	
	public void chooseAction(){
		/*
		if (nearPlayer()){
			DEFAULT_SIGHT_MODIFIER = 92;
			chasePlayer();
		} else {
			DEFAULT_SIGHT_MODIFIER = 0;
			patrolMove();
		}
		*/
		
		long currTime = System.nanoTime();
		//System.out.println("size: " + path.size());
		if (currTime - lastTimeAstar > 500000000){
			lastTimeAstar = currTime;
			aStar();
			changedAstar = true;
			//System.out.println("A*");
		}
		
		if (path.size() < 2){
			
			chasePlayer();
			
		} else if (path.size() > 0) {
			
			moveToLocation(path.get(0));
			if (getMapIndex(path.get(0)).equals(getMapIndex(getX(), getY()))){
				path.remove(0);
			}
		}
		
		distanceToPlayer = path.size();
		
	}
	

		

	private Point getMapIndex(float x, float y){
		return handler.getWorld().getMapIndex((int)x, (int)y);
	}
	private Point getMapIndex(Point p){
		return handler.getWorld().getMapIndex((int)p.getX(), (int)p.getY());
	}
	
	
	
	private void aStar(){
		
		AreaMap.fillLavirint(handler, this);
		
		Player player = handler.getPlayer();
		int finishX = World.getMapIndex(player.getX(), player.getY()).x;
		int finishY = World.getMapIndex(player.getX(), player.getY()).y;
		
		AStar as = new AStar(finishX,finishY);
		State pocetno = new State();
		pocetno.markI = World.getMapIndex(this.getX(), this.getY()).x;
		pocetno.markJ = World.getMapIndex(this.getX(), this.getY()).y;
		ArrayList<State> resenje = new ArrayList<State>();
		State solution = as.search(pocetno);
        if (solution != null)
        {
        	 resenje = solution.path();
        }
        
        path = AStar.parseState(resenje,true);
        fullPath = AStar.parseState(resenje, false);

        changedAstar = false;
	}
	

	
	public void chasePlayer(){
		
		Player player = handler.getPlayer();
		float targetX = player.getX();
		float targetY = player.getY();
		if (collisionWithPlayer()){
			speed = 0;
		} else {
			speed = 1.8f;
		}
		
		if (x - targetX > 4 ){
			xMove = -speed;
			moveX();
			strana = OKRENUT.levo;
		} else if (x - targetX < -4) {
			xMove = speed;
			moveX();
			strana = OKRENUT.desno;
		}
		
		if (y - targetY > 4 ){
			yMove = -speed;
			moveY();
			if (Math.abs(y - targetY) > Math.abs(x - targetX)){
				strana = OKRENUT.gore;
			}

		} else if(y - targetY < -4){
			yMove = speed;
			moveY();
			if (Math.abs(y - targetY) > Math.abs(x - targetX)){
				strana = OKRENUT.dole;
			}
			
		}
	}
		
	public void patrolMove(){
		
		changePatrol();
		
		if (patrolDown == 1){
			yMove = speed;
			moveY();
			strana = OKRENUT.dole;
		} else if (patrolDown == 0) {
			yMove = -speed;
			moveY();
			strana = OKRENUT.gore;
		} else {
			yMove = 0;
			moveY();
			strana = OKRENUT.stoji;
		}
	}
	
	public void changePatrol(){
		
		long currentTime = System.nanoTime();
		
		if (currentTime - lastTime > 1000000000){
			lastTime = currentTime;
			Random r = new Random();
			if (r.nextBoolean()){
				patrolDown = r.nextInt(2);
				
			} else {
				patrolDown = 2;
			}
			
		}

	}

	public void checkDead(){
		if (dead){
			handler.getWorld().getEntityManager().getCreatures().remove(this);
		}
	}

	public int getDistanceToPlayer() {
		return distanceToPlayer;
	}

	public void setDistanceToPlayer(int distanceToPlayer) {
		this.distanceToPlayer = distanceToPlayer;
	}

	public ArrayList<Point> getPath() {
		return path;
	}

	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}

	public ArrayList<Point> getFullPath() {
		return fullPath;
	}

	public void setFullPath(ArrayList<Point> fullPath) {
		this.fullPath = fullPath;
	}
}
