package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.creatures.bosses.SkeletonBoss;
import tilegame.entities.creatures.enemies.Skeleton;
import tilegame.entities.creatures.enemies.Skeleton.OKRENUT;
import tilegame.entities.staticEntities.Heart;
import tilegame.entities.staticEntities.StaticEntity;
import tilegame.entities.weapons.Weapon.STRANA_NAPADA;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.particles.PlayerParticleEmitter;
import tilegame.pathfinding.AStar;
import tilegame.pathfinding.AreaMap;
import tilegame.pathfinding.State;
import tilegame.pathfinding.TileType;
import tilegame.worlds.World;

public class Player extends Creature {

	private Animation upAnimation, downAnimation, rightAnimation, leftAnimation, 
					  	idleAnimationDown, idleAnimationUp, idleAnimationRight, idleAnimationLeft;
	private Animation currentAnimation;
	private PlayerParticleEmitter playerEmitter;
	private PlayerParticleEmitter heartCollectEmitter;
	private String lastKey;
	
	
	long lastAttack;
	
	// EXP AND LEVEL
	public static int experience;
	private int level;
	private int nextLevelExp;
	// TEMP QUESTS
	public static int killCount;
	// END TEMP QUESTS
	// AUTOMATIC MOVEMENT
	public static boolean automatic;
	long lastAutomatic;
	// END AUTOMATIC MOVEMENT
	ArrayList<Point> heartsCoords;
	public static boolean heartsChanged;
	public static int heartsCollected;
	public static boolean testirajBlizinu;
	public static int brojacPromena = 0;
	//
	
	//A* PATHFINDING
	public static ArrayList<Point> path = new ArrayList<Point>();
	public static long lastTimetimeout;
	public static long lastTimeAstar;
	public static boolean changedAstar;
	public static boolean changeGoal;
	Point goal = new Point();
	//END A* PATHFINDING
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		playerEmitter = new PlayerParticleEmitter(handler,0, Color.red, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), 5, 5, 40, 80, 60, 120, 3, 5);
		heartCollectEmitter = new PlayerParticleEmitter(handler,0, Color.red, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), 5, 5, 40, 80, 60, 120, 3, 5);
		
		bounds.x = 20;
		bounds.y = 20;
		bounds.width = 24;
		bounds.height = 30;
		
		killCount = 0;
		lastKey = "down";
		lastAttack = System.nanoTime();
		
		experience = 0;
		level = 1;
		nextLevelExp = level * 200;
		
		automatic = true;
		heartsCoords = new ArrayList<Point>();
		heartsChanged = false;
		heartsCollected = 0;
		lastAutomatic = System.nanoTime();
		lastTimetimeout = System.nanoTime();
		lastTimeAstar = System.nanoTime();
		changeGoal = true;
		
		dead = false;
		strana = OKRENUT.dole;
		
		downAnimation = new Animation(Assets.playerDown, 100);
		upAnimation = new Animation(Assets.playerUp, 100);
		leftAnimation = new Animation(Assets.playerLeft, 100);
		rightAnimation = new Animation(Assets.playerRight, 100);
		idleAnimationDown = new Animation(Assets.playerIdleDown, 150);
		idleAnimationUp = new Animation(Assets.playerIdleUp, 150);
		idleAnimationLeft = new Animation(Assets.playerIdleLeft, 150);
		idleAnimationRight = new Animation(Assets.playerIdleRight, 150);
		currentAnimation = idleAnimationDown;
		
		
	}

	@Override
	public void tick() {
		
		if (automatic){
			this.speed = 2;
			chooseAction();
			
		} else {
			getInput();
			move();
		}
		
		checkAutomatic();
		//consoleOutput();
		tickAnimation();
		calculateLevel();
		collectHeartCheck();
		emitter.tick();
		playerEmitter.tick();
		heartCollectEmitter.tick();
		weapon.tick();
		attack();
		handler.getGameCamera().centerOnEntity(this);
	}
	
	private void checkAutomatic(){
		
		long currTime = System.nanoTime();
		
		if (KeyManager.c && currTime - lastAutomatic > 500000000){
			automatic = !automatic;
			lastAutomatic = currTime;
		}
	}
	
	private void chooseAction(){
		
		// napuni listu svih srca
		fillHearts();
		
		path = null;
		// odaberi najblize srce
		int pokusaj = 0;
		long currTime = System.nanoTime();
		while (path == null){
			currTime = System.nanoTime();
			System.out.println("Poceo");
			if (changeGoal){
				if (heartsCollected > 3 ){
					this.goal = findClosestHeartAStar(pokusaj++);
				} else {
					this.goal = findClosestHeartDecartes(pokusaj++);
				}
				changeGoal = false;
			}
			System.out.println("Zavrsio");

			if (currTime - lastTimeAstar > 5000000 && goal != null){
				System.out.println("Usao");
				setupPlayerLavirint();
				path = getRoute(this.goal, pokusaj);
				changedAstar = true;
			}
			
			if (goal == null){
				handler.getGame().setPaused(true);
				return;
			}
			
		}
		
		lastTimeAstar = currTime;
		parsePath();
		

	}
	
	private void parsePath(){
		
		if (path != null && path.size() < 2 && path.size() > 0){
			
			moveToLocation(path.get(path.size() - 1));
			
		} else if (path != null && path.size() > 0){
			
			moveToLocation(path.get(0));
			if (World.getMapIndex(path.get(0)).equals(World.getMapIndex(getX(),getY()))){
				path.remove(0);
			}
			
		}
	}
	
	private void fillHearts(){
		heartsCoords.clear();
		if (heartsCoords.isEmpty() || heartsChanged){
			ArrayList<StaticEntity> hearts = handler.getWorld().getEntityManager().getStaticEntitiesByClass(Heart.class);
			if (hearts.size() == 0){
				System.out.println("GOTOVO");
			}
			for (StaticEntity staticEntity : hearts) {
				heartsCoords.add(staticEntity.getMapIndex());
			}

		}
	}
	
	private Point findClosestHeartDecartes(int number){
		
		int myX = World.getMapIndex(this.x, this.y).x;
		int myY = World.getMapIndex(this.x, this.y).y;
		
		ArrayList<Double> distancesDecartes = new ArrayList<Double>();
		
		for (Point point : heartsCoords) {
			distancesDecartes.add(Math.sqrt(Math.pow(myX - point.x, 2) + Math.pow(myY - point.y,2)));
		}
		
		ArrayList<Double> sortedDistances = new ArrayList<Double>();
		for (Double double1 : distancesDecartes) {
			sortedDistances.add(double1);
		}
		
		for (int i = 0; i < sortedDistances.size(); i++){
			for ( int j = i; j < sortedDistances.size(); j++){
				if (sortedDistances.get(j) < sortedDistances.get(i)){
					double disI = sortedDistances.get(i);
					double disJ = sortedDistances.get(j);
					sortedDistances.remove(j);
					sortedDistances.add(j, disI);
					sortedDistances.remove(i);
					sortedDistances.add(i,disJ);
				}
			}
		}
		
		Point retVal;
		try {
			retVal = heartsCoords.get(distancesDecartes.indexOf(sortedDistances.get(number)));
		} catch (IndexOutOfBoundsException e){
			if (heartsCoords.isEmpty()){
				return null;
			} else {
				retVal = heartsCoords.get(0);
			}
		}
		return retVal;
		
	}
	
	
	private Point findClosestHeartAStar(int number){
		
		if (AreaMap.playerLavirint == null){
			setupPlayerLavirint();
		}
		
		if (AreaMap.clearLavirint == null){
			AreaMap.fillLavirint(handler, this);
		}
		int myX = World.getMapIndex(this.x, this.y).x;
		int myY = World.getMapIndex(this.x, this.y).y;
		
		ArrayList<ArrayList<State>> solutions = new ArrayList<ArrayList<State>>();
		
		for (Point point : heartsCoords) {
			
			AStar as = new AStar(point.x, point.y);
			State pocetno = new State(myX, myY, AreaMap.clearLavirint);
			State solution = as.search(pocetno);
			solutions.add(solution.path());
		}
		ArrayList<Integer> sortedDistances = new ArrayList<Integer>();
		for (ArrayList<State> solution : solutions) {
			sortedDistances.add(solution.size());
		}
		
		for (int i = 0; i < sortedDistances.size(); i++){
			for ( int j = i; j < sortedDistances.size(); j++){
				if (sortedDistances.get(j) < sortedDistances.get(i)){
					
					int disI = sortedDistances.get(i);
					int disJ = sortedDistances.get(j);
					
					sortedDistances.remove(j);
					sortedDistances.add(j, disI);
					sortedDistances.remove(i);
					sortedDistances.add(i,disJ);
					
					ArrayList<State> solutionI = solutions.get(i);
					ArrayList<State> solutionJ = solutions.get(j);
					
					solutions.remove(j);
					solutions.add(j,solutionI);
					solutions.remove(i);
					solutions.add(i,solutionJ);
					
					
				}
			}
		}
		
		Point retVal;
		try {
			System.out.println("Number:" + number + " Sorted distances.get(number):" + sortedDistances.get(number));
			State closestState = solutions.get(number).get(solutions.get(number).size() - 1);
			retVal = new Point(closestState.markI,closestState.markJ);
			
		} catch (Exception e){
			if (heartsCoords.isEmpty()){
				return null;
			} else {
				retVal = heartsCoords.get(0);
			}
		}
		return retVal;
	}
	
	
	private ArrayList<Point> getRoute(Point goal, int pokusaj){
		
		boolean canReachRaskrsnica = true;
		boolean changeRoute = false;
		AStar as = new AStar(goal.x, goal.y);
		
		int routeChangedCounter = 30;
		
		while (canReachRaskrsnica || changeRoute){
			canReachRaskrsnica = true;
			int myX = World.getMapIndex(this.x, this.y).x;
			int myY = World.getMapIndex(this.x, this.y).y;
			State pocetnoStanje = new State(myX, myY,AreaMap.playerLavirint);
			State solution = as.search(pocetnoStanje);
			changeRoute = false;
			
			if (solution == null){
				
				changeGoal = true;
				if (pokusaj >= heartsCoords.size()){
					pocetnoStanje = new State(myX,myY,AreaMap.clearLavirint);
					solution = as.search(pocetnoStanje);
					System.out.println("No way out - take hit");
					return AStar.parseState(solution.path(),true);
				} else {
					return null;
				}
				
			} else {
				
				boolean slepaUlica = true;
				Point poslednjaRaskrsnica = null;
				ArrayList<Point> coordinate = AStar.parseStateMapIndex(solution.path(),false);
				
				int brojacRaskrsnica = 0;
				int brojacPolja = 0;
				
				for (Point point : coordinate){
					
					canReachRaskrsnica = canReachBeforeCreature(point, brojacPolja);
					
					if (AreaMap.heartRaskrsnica[point.x][point.y] > 2){
						brojacRaskrsnica++;
						poslednjaRaskrsnica = new Point(point.x, point.y);
					}
					
					if (!canReachRaskrsnica && brojacRaskrsnica < 4 && brojacPolja < 6){
						
						AreaMap.playerLavirint[point.x][point.y] = TileType.CHANGED;
						changeRoute = true;
					}
					
					brojacPolja++;
				}
				
				
				if (changeRoute){
					System.out.println("Route changed");
					routeChangedCounter--;
					if (routeChangedCounter <= 0){
						changeGoal = true;
						System.out.println("Goal changed");
						return null;
					}
					//changeRoute = false;
					continue;
					
				}
				
				// ako imas put do sledeceg srca srca
				if (brojacRaskrsnica == 0 && !canReachNextHeart() && heartsCoords.size() > 1){
					System.out.println("Ne mogu da dodjem");				
				}
				
				if (AreaMap.raskrsnica[goal.x][goal.y] >= 2 || brojacRaskrsnica > 0){
					slepaUlica = false;
				}
				
				// ako je goal na slepoj ulici onda razmisli da li ces da udjes
				
				if (slepaUlica){
					boolean idi = true;
					ArrayList<Creature> creatures = handler.getWorld().getEntityManager().getCreatures();
					for (Creature creature : creatures) {
						int goalDistance = (coordinate.size() - coordinate.indexOf(poslednjaRaskrsnica) - 1) * 2;
						if (creature instanceof Skeleton){
							if (((Skeleton)creature).getDistanceToPlayer() / creature.getSpeed() < goalDistance / this.getSpeed()){
								if (heartsCoords.size() > 1)
									idi = false;
							}
						}
					}
	
					if (!idi){
						System.out.println("Dead end - can't return");
						System.out.println("Change Goal");
						changeGoal = true;
						return null;
					}
				}
				System.out.println("nista se ne desi");
				return AStar.parseState(solution.path(),true);
			}
		}
		return null;
	}
	private boolean canReachNextHeart(){
		
		
		int myX = World.getMapIndex(this.x, this.y).x;
		int myY = World.getMapIndex(this.x, this.y).y;
		int brojacPolja = 0;
		
		if (heartsCoords.size() > 1){
			
			boolean canReach = true;
			
			Point nextGoal = findClosestHeartDecartes(1);
			AStar nextAstar = new AStar(nextGoal.x, nextGoal.y);
			State nextPocetno = new State(myX, myY, AreaMap.playerLavirint);
			State nextSolution = nextAstar.search(nextPocetno);
			if (nextSolution == null){
				return false;
			}
			ArrayList<Point> nextCoordinate = AStar.parseStateMapIndex(nextSolution.path(),false);
			for (Point point : nextCoordinate){
				canReach = canReachBeforeCreature(point, brojacPolja);
				if (canReach == false){
					break;
					
				}
				if (AreaMap.heartRaskrsnica[point.x][point.y] > 2 && canReach == true){
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	private boolean canReachBeforeCreature(Point mapIndex, int playerDistance){
		
		Point coords = World.getCoords(mapIndex.x, mapIndex.y);
		for (Creature creature : handler.getWorld().getEntityManager().getCreatures()) {
			if (creature instanceof Skeleton){
				ArrayList<Point> creaturePath = ((Skeleton)creature).getFullPath();
				if (creaturePath.contains(coords)){
					int creatureDistanceToPoint = creaturePath.indexOf(coords) ;
					if (creatureDistanceToPoint / creature.speed  <= playerDistance  / handler.getPlayer().getSpeed()){
						System.out.println("Can't reach: " + mapIndex.x + " " + mapIndex.y);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void setupPlayerLavirint(){
		
		AreaMap.playerLavirint = new TileType[AreaMap.width][AreaMap.height];
		for (int i = 0; i < AreaMap.width; i++){
			for (int j = 0; j < AreaMap.height; j++){
				AreaMap.playerLavirint[i][j] = AreaMap.lavirint[i][j];
			}
		}
		for (Creature creature : handler.getWorld().getEntityManager().getCreatures()) {
			if (creature instanceof Skeleton){
				Point mapIndex = creature.getMapIndex();
				if (mapIndex.equals(World.getMapIndex(this.x, this.y))){
					
					double distanceY = Math.abs(creature.getY() - this.getY());
					double distanceX = Math.abs(creature.getX() - this.getX());
					if (distanceY > distanceX){
						if (creature.getY() + 5 > this.getY()){
							AreaMap.playerLavirint[mapIndex.x][mapIndex.y + 1] = TileType.ENEMY;
						} else if (creature.getY() - 5 < this.getY()){
							AreaMap.playerLavirint[mapIndex.x][mapIndex.y - 1] = TileType.ENEMY;
						}
					} else {
						if (creature.getX() + 5 > this.getX()){
							AreaMap.playerLavirint[mapIndex.x +1][mapIndex.y] = TileType.ENEMY;
						} else if (creature.getX() - 5 < this.getX()){
							AreaMap.playerLavirint[mapIndex.x - 1][mapIndex.y] = TileType.ENEMY;
						}
					}
				}
			}
		}
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		
		if (KeyManager.left){
			xMove -= speed;
			lastKey = "left";
		}
		if (KeyManager.right){
			xMove += speed;
			lastKey = "right";
		}
		
		if (KeyManager.up){
			lastKey = "up";
			yMove -= speed;
			
		}
		if (KeyManager.down){
			yMove += speed;
			lastKey = "down";
		}
		
		
	}

	public boolean inRangeOfCreature(){
		
		boolean hit = false;
		for (Creature c : handler.getWorld().getEntityManager().getCreatures()){
			if (c instanceof Creature && nearEnemy(c)){
				Creature cr = (Creature)c;
				cr.takeDamage(weapon.getDamage());
				cr.showHealth();
				hit = true;
			} else if (c instanceof SkeletonBoss && nearEnemy(c)){
				SkeletonBoss skB = (SkeletonBoss)c;
				skB.takeDamage(weapon.getDamage());
				skB.showHealth();
				hit = true;
			}
		}
		return hit;
	}
	
	private boolean nearEnemy(Entity entity){
		if (weapon.collisionWithEntity(entity)){
			return true;
		}
		return false;
	}
	
	public void attack(){
		long currentTime = System.nanoTime();		
		if (currentTime - lastAttack > 500000000 * weapon.getAttackSpeed()){
			
			if (KeyManager.space || MouseManager.leftButton  || !weapon.isDealtDamage() ){
				//System.out.println("Key = " + KeyManager.space + " Mouse = " + MouseManager.leftButton +" Dealt = " + !weapon.isDealtDamage());
				lastAttack = currentTime;
				inRangeOfCreature();
				weapon.getCurrentAnimation().reset();
				weapon.setDealtDamage(true);
			}	
		}
	}

	public void calculateLevel(){
		if (experience >= nextLevelExp){
			lvlUp();
			experience-= nextLevelExp;
			level++;
			nextLevelExp = 200 * level;
			playerEmitter = getLevelUpEmmiter();
		}
	}
	
	public void lvlUp(){
		maxHealth+= level;
		health = maxHealth;
		
	}
	
	public PlayerParticleEmitter getLevelUpEmmiter(){
		return new PlayerParticleEmitter(handler, 20, Color.yellow, (int)x,
				(int)y, 2, 2, 40, 80, 0, 360, 3, 5);
	}
	
	public void collectHeartCheck(){
		if (heartsChanged == true){
			
			heartCollectEmitter = getHeartCollectEmitter();
			heartsChanged = false;
		}
		
	}
	
	
	public PlayerParticleEmitter getHeartCollectEmitter(){
		return new PlayerParticleEmitter(handler, 20, Color.blue, (int)x,
				(int)y, 2, 2, 40, 80, 0, 360, 3, 5);
	}
	
	@Override
	public void render(Graphics g) {
		
		currentAnimation.render(g, (int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), 
				width, height);
		weapon.render(g);
		renderEmitters(g);
		// render collision box
		//g.setColor(Color.red);
		//g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
		//			(int) (y + bounds.y - handler.getGameCamera().getyOffset()), 
		//			bounds.width, bounds.height);
	
		
		
	}
	
	private void renderEmitters(Graphics g){
		heartCollectEmitter.render(g);
		playerEmitter.render(g);
		emitter.render(g);
	}
	
	private void tickAnimation(){
		setupCurrentAnimation();
		
		upAnimation.tick();
		downAnimation.tick();
		leftAnimation.tick();
		rightAnimation.tick();
		idleAnimationDown.tick();
		idleAnimationUp.tick();
		idleAnimationLeft.tick();
		idleAnimationRight.tick();
		
	}
	
	public void setupCurrentAnimation(){
		
		if (automatic){
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
				currentAnimation = idleAnimationDown;
				break;
			}
			default:
				currentAnimation = upAnimation;
				break;
			}
		} else {
			STRANA_NAPADA strana = weapon.getStranaNapada();
			if (weapon.isCurrentlyAttacking()){
				if (strana == STRANA_NAPADA.gore){
					currentAnimation = upAnimation;
					lastKey = "up";
					
				} else if (strana == STRANA_NAPADA.dole){
					currentAnimation = downAnimation;
					lastKey = "down";
					
				} else if (strana == STRANA_NAPADA.levo){
					currentAnimation = leftAnimation;
					lastKey = "left";
					
				} else if (strana == STRANA_NAPADA.desno){
					currentAnimation = rightAnimation;
					lastKey = "right";
					
				} else {
					currentAnimation = downAnimation;
				}
			}else {
				if (KeyManager.up){
					currentAnimation = upAnimation;
					lastKey = "up";
				} else if (KeyManager.down){
					currentAnimation = downAnimation;
					lastKey = "down";
				} else if (KeyManager.left){
					currentAnimation = leftAnimation;
					lastKey = "left";
				} else if (KeyManager.right){
					currentAnimation = rightAnimation;
					lastKey = "right";
				} else {
					switch(lastKey){
					case "up":{
						currentAnimation = idleAnimationUp;
						break;
					}
					case "down":{
						currentAnimation = idleAnimationDown;
						break;
					}
					case "right":{
						currentAnimation = idleAnimationRight;
						break;
					}
					case "left":{
						currentAnimation = idleAnimationLeft;
						break;
					}
					
				}
					
				}
			}
		}
		
	}

	public static void disableAttackMode(){
		ableToAttack = false;
	}
	
	public static void enableAttackMode(){
		ableToAttack = true;
	}
	// GETTERS && SETTERS
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public String getLastKey() {
		return lastKey;
	}

	public void setLastKey(String lastKey) {
		this.lastKey = lastKey;
	}

	public static int getExperience() {
		return experience;
	}

	public static void setExperience(int experience) {
		Player.experience = experience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNextLevelExp() {
		return nextLevelExp;
	}

	public void setNextLevelExp(int nextLevelExp) {
		this.nextLevelExp = nextLevelExp;
	}
	
}
