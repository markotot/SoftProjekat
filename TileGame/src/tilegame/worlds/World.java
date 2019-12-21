package tilegame.worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import tilegame.Handler;
import tilegame.entities.EntityManager;
import tilegame.entities.creatures.Player;
import tilegame.entities.creatures.enemies.Skeleton;
import tilegame.entities.creatures.npcs.QuestGiver;
import tilegame.entities.staticEntities.Heart;
import tilegame.pathfinding.AreaMap;
import tilegame.pathfinding.TileType;
import tilegame.tile.Tile;
import tilegame.utils.Utils;

public class World {
	
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[] questGiverSpawnX,questGiverSpawnY, questGiverType;
	private int[][] tiles;
	
	private int numberOfHearts;
	private int[] heartPositionX, heartPositionY;
	
	// Entities
	private EntityManager entityManager;
	
	public World(Handler handler, String path){
		this.handler = handler;
		
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		
		loadWorld(path);
		
		fillEntities();
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	
	public void tick(){
		entityManager.tick();
	}
	
	public void render(Graphics g){
		
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1 );
		
		
		
		for (int y = yStart; y < yEnd; y++){
			for (int x = xStart; x < xEnd; x++){
				getTile(x,y).render(g, (int) ((x*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()) ,
										(int) ((y*Tile.TILE_WIDTH) - handler.getGameCamera().getyOffset()));
			}
		}
		
		// ZA AREAMAP
		if (AreaMap.raskrsnica != null){
			for (int y = yStart; y < yEnd; y++){
				for (int x = xStart; x < xEnd; x++){
					/*
					if ( AreaMap.raskrsnica[x][y] == 3 || AreaMap.raskrsnica[x][y] == 4){
						g.setColor(Color.RED);
						g.fillRect((int)((x*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()), (int)((y*Tile.TILE_WIDTH) - handler.getGameCamera().getyOffset()),
								Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
					}
					
					if ( AreaMap.raskrsnica[x][y] == 2){
						g.setColor(Color.PINK);
						g.fillRect((int)((x*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()), (int)((y*Tile.TILE_WIDTH) - handler.getGameCamera().getyOffset()),
								Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
					}
					if (AreaMap.raskrsnica[x][y] < 0){
						g.setColor(Color.BLUE);
						g.fillRect((int)((x*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()), (int)((y*Tile.TILE_WIDTH) - handler.getGameCamera().getyOffset()),
								Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
					}
					*/
					//if (AreaMap.lavirint[x][y] == TileType.HEART){
					//	g.setColor(Color.GREEN);
					//	g.fillRect((int)((x*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()), (int)((y*Tile.TILE_WIDTH) - handler.getGameCamera().getyOffset()),
					//			Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
					//}
					
				}
			}
		}
		
		
		
		
		//Entities
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y){
		
		if ( x < 0 || y < 0 || x>= width || y >= height){
			return Tile.grassEmptyTile;
		}
		
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null){
			return Tile.grassEmptyTile;
		}
		return t;
	}
	
	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		
		//load MAP SIZE
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		
		//load PLAYER spawn
		int spawnIndexX = Utils.parseInt(tokens[2]);
		int spawnIndexY = Utils.parseInt(tokens[3]);
		spawnX = World.getCoords(spawnIndexX, spawnIndexY).y;
		spawnY = World.getCoords(spawnIndexX, spawnIndexY).x;
		
		
		/*
		//load PLAYER spawn
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		*/
		//load QUEST GIVERS
		questGiverSpawnX = new int[Utils.parseInt(tokens[4])];
		questGiverSpawnY = new int[Utils.parseInt(tokens[4])];
		questGiverType = new int[Utils.parseInt(tokens[4])];
		int brojac = 0;
		for (int i = 0, y=5; i <questGiverSpawnX.length; i++) {
			
			int questGiverSpawnIndexX = Utils.parseInt(tokens[y++]);
			int questGiverSpawnIndexY = Utils.parseInt(tokens[y++]);
			int questGiverTypeIndex = Utils.parseInt(tokens[y++]);
			
			questGiverSpawnX[i] = World.getCoords(questGiverSpawnIndexX, questGiverSpawnIndexY).y;
			questGiverSpawnY[i] = World.getCoords(questGiverSpawnIndexX, questGiverSpawnIndexY).x;
			questGiverType[i] = questGiverTypeIndex;
			brojac = y;
			/*
			questGiverSpawnX[i] = Utils.parseInt(tokens[y++]);
			questGiverSpawnY[i] = Utils.parseInt(tokens[y++]);
			questGiverType[i] = Utils.parseInt(tokens[y++]);
			brojac = y;
			*/
		}
		
		//load hearts
		numberOfHearts = Utils.parseInt(tokens[brojac++]);
		heartPositionX = new int[numberOfHearts];
		heartPositionY = new int[numberOfHearts];
		
		for (int i = 0; i < numberOfHearts; i++){
			
			int heartPositionIndexX = Utils.parseInt(tokens[brojac++]);
			int heartPositionIndexY = Utils.parseInt(tokens[brojac++]);
			heartPositionX[i] = World.getCoords(heartPositionIndexX, heartPositionIndexY).y;
			heartPositionY[i] = World.getCoords(heartPositionIndexX, heartPositionIndexY).x;
		}
		
		//load TILES
		tiles = new int[width][height];
		for(int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + brojac]);
			}
		}
		
		
	}

	private void fillEntities(){
		
		for (int i = 0; i < questGiverSpawnX.length; i++){
			switch (questGiverType[i]){
			case 1:{
				
				entityManager.addCreature(new Skeleton(handler,questGiverSpawnX[i], questGiverSpawnY[i]));
				break;
			}	
			case 2:{
				entityManager.addCreature(new QuestGiver(handler, questGiverSpawnX[i], questGiverSpawnY[i]));
				break;
			}
			}
		}
		
		for (int i = 0; i <numberOfHearts; i++){
			entityManager.addStaticEntity(new Heart(handler, heartPositionX[i], heartPositionY[i], 32,32));
		}
	}
	
	public static Point getMapIndex(float x, float y){
		Point retVal = new Point();
		retVal.setLocation(Math.round(x / Tile.TILE_WIDTH),Math.round((y / Tile.TILE_HEIGHT)));
		return retVal;
	}
	
	public static Point getMapIndex(Point p){
		return getMapIndex((int)p.getX(), (int)p.getY());
	}
	
	public static Point getCoords(int x, int y){
		return new Point(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
	}
	//GETTERS && SETTERS
	
	public int[] getQuestGiverType() {
		return questGiverType;
	}

	public void setQuestGiverType(int[] questGiverType) {
		this.questGiverType = questGiverType;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	public int[][] getTiles() {
		return tiles;
	}

	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setQuestGiverSpawnX(int[] questGiverSpawnX) {
		this.questGiverSpawnX = questGiverSpawnX;
	}

	public void setQuestGiverSpawnY(int[] questGiverSpawnY) {
		this.questGiverSpawnY = questGiverSpawnY;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int[] getQuestGiverSpawnX(){
		return questGiverSpawnX;
	}
	
	public int[] getQuestGiverSpawnY(){
		return questGiverSpawnY;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
