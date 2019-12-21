package tilegame.pathfinding;

import java.util.ArrayList;

import tilegame.Handler;
import tilegame.entities.Entity;
import tilegame.entities.creatures.Creature;
import tilegame.entities.staticEntities.StaticEntity;
import tilegame.worlds.World;

public class AreaMap {
	
	public static TileType[][] lavirint;
	public static TileType[][] playerLavirint;
	public static TileType[][] clearLavirint;
	public static int[][] raskrsnica;
	public static int[][] heartRaskrsnica;
	
	public static int startX = 8, startY = 8;
	public static int width;
	public static int height;
	
	public AreaMap(Handler handler, Creature c){
		
		fillLavirint(handler,c);
		fillRaskrsnica(handler);
		
	}
	
	public static void fillLavirint(Handler handler, Creature c){
		World world = handler.getWorld();
		
		width = world.getWidth();
		height = world.getHeight();
		
		startX = World.getMapIndex(c.getX() + c.getWidth() / 2, c.getY() + c.getHeight() / 2).x;
		startY = World.getMapIndex(c.getX() + c.getWidth() / 2, c.getY() + c.getHeight() / 2).y;
		
		//finishX = world.getMapIndex(player.getX(), player.getY()).x;
		//finishY = world.getMapIndex(player.getX(), player.getY()).y;
					
		lavirint = new TileType[world.getWidth()][world.getHeight()];
		
		for (int i = 0; i < world.getWidth(); i++){
			for (int j = 0; j < world.getHeight(); j++){
				
				if (world.getTile(j, i).isSolid()){
					
					lavirint[j][i] = TileType.SOLID;
					
				} else {
					
					lavirint[j][i] = TileType.PASSABLE;
					
				}
			
			}
		}
		
		clearLavirint = new TileType[world.getWidth()][world.getHeight()];
		
		for (int i = 0; i < world.getWidth(); i++){
			for (int j = 0; j < world.getHeight(); j++){
				clearLavirint[j][i] = lavirint[j][i];
			}
		}
		
		for (Entity e: handler.getWorld().getEntityManager().getCreatures()){
			int x = World.getMapIndex(e.getX(), e.getY()).x;
			int y = World.getMapIndex(e.getX(), e.getY()).y;
			lavirint[x][y] = TileType.ENEMY;
		}
		
		for (StaticEntity se : handler.getWorld().getEntityManager().getStaticEntities()) {
			int x = World.getMapIndex(se.getX(), se.getY()).x;
			int y = World.getMapIndex(se.getX(), se.getY()).y;
			lavirint[x][y] = TileType.HEART; 
		}
	}
	
	public static void fillRaskrsnica(Handler handler){
		
		
		World world = handler.getWorld();
		width = world.getWidth();
		height = world.getHeight();
		
		
		lavirint = new TileType[world.getWidth()][world.getHeight()];
		raskrsnica = new int[world.getWidth()][world.getHeight()];
		for (int i = 0; i < world.getWidth(); i++){
			for (int j = 0; j < world.getHeight(); j++){
				
				if (world.getTile(j, i).isSolid()){
					
					lavirint[j][i] = TileType.SOLID;
					
				} else {
					
					lavirint[j][i] = TileType.PASSABLE;
					
				}
			
			}
		}
		
		for (Entity e: handler.getWorld().getEntityManager().getCreatures()){
			int x = World.getMapIndex(e.getX(), e.getY()).x;
			int y = World.getMapIndex(e.getX(), e.getY()).y;
			lavirint[x][y] = TileType.ENEMY;
		}
		
		for (StaticEntity se : handler.getWorld().getEntityManager().getStaticEntities()) {
			int x = World.getMapIndex(se.getX(), se.getY()).x;
			int y = World.getMapIndex(se.getX(), se.getY()).y;
			lavirint[x][y] = TileType.HEART; 
		}
		

		// cini mi se da sam obrnuo i i j :) pazi sa debugom cela mapa rotirana za 90 stepeni
		for(int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				
				int susedi = 0;
				
				if ( i == 0){
					if ( j == 0){ // gore-levo
						
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}
						
					} else if ( j == height - 1){ // dole-levo
						
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						
					} else { // levo
						
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						
					}
					
				} else if ( i == width - 1){ 
					if ( j == 0){ // gore-desno
						
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}

					} else if ( j == height - 1){ // dole-desno
						
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						
					} else { // desno
						
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						
					}
					
				} else {
					
					if ( j == 0){ // gore
						
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						
					} else if ( j == height - 1){ // dole
						
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						
					} else { //sredina
						
						if (lavirint[i+1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i-1][j] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j-1] != TileType.SOLID){
							susedi++;
						}
						if (lavirint[i][j+1] != TileType.SOLID){
							susedi++;
						}
						
					}
					
				}
				
				if ( lavirint[i][j] != TileType.SOLID){
					raskrsnica[i][j] = susedi; 
				} else {
					raskrsnica[i][j] = 9;
				}

			}
		}
		/*
		for ( int i = 0; i < width; i++){
			for ( int j = 0; j < height; j++){
				System.out.print(raskrsnica[i][j] + " ");
			}
			System.out.println("");
		}
		for ( int i = 0; i < width; i++){
			for ( int j = 0; j < height; j++){
				System.out.print(lavirint[i][j] + " ");
			}
			System.out.println("");
		}
		
		for (StaticEntity staticEntity : handler.getWorld().getEntityManager().getStaticEntities()) {
			if (staticEntity instanceof Heart){
				System.out.println("Coords:" + staticEntity.getX() + " " + staticEntity.getY());
				System.out.println(world.getMapIndex(staticEntity.getX(), staticEntity.getY()));
			}
		}
		*/
		removeDeadEnds();
		
		heartRaskrsnica = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				heartRaskrsnica[i][j] = raskrsnica[i][j];
			}
		}
		//heartRaskrsnica = raskrsnica.clone();
		includeDeadEndsWithHearts();
		fixDeadEndsHearts();
		
		/*
		for ( int i = 0; i < width; i++){
			for ( int j = 0; j < height; j++){
				System.out.print(raskrsnica[j][i] + "\t");
			}
			System.out.println("");
		}
		
		System.out.println("\n");
		
		for ( int i = 0; i < width; i++){
			for ( int j = 0; j < height; j++){
				System.out.print(heartRaskrsnica[j][i] + "\t");
			}
			System.out.println("");
		}
		*/
	}
	
	private static void removeDeadEnds(){
		
		boolean sadrziSlepu = true;
		
		while (sadrziSlepu){
			sadrziSlepu = false;
			for (int i = 0; i < width; i++){
				for ( int j = 0; j < height; j++){
					if ( raskrsnica[i][j] == 1) {
						sadrziSlepu = true;
						raskrsnica[i][j]  = -50;
						
						if ( i == 0){
							if ( j == 0){ // gore-levo
								raskrsnica[i+1][j]--;
								raskrsnica[i][j+1]--;

							} else if ( j == height - 1){ // dole-levo
								raskrsnica[i+1][j]--;
								raskrsnica[i][j-1]--;
								
							} else { // levo
								raskrsnica[i+1][j]--;
								raskrsnica[i][j+1]--;
								raskrsnica[i+1][j-1]--;
								
							}
							
						} else if ( i == width - 1){ 
							if ( j == 0){ // gore-desno
								raskrsnica[i-1][j]--;
								raskrsnica[i][j+1]--;
								
							} else if ( j == height - 1){ // dole-desno
								raskrsnica[i-1][j]--;
								raskrsnica[i][j-1]--;
								
							} else { // desno
								
								raskrsnica[i-1][j]--;
								raskrsnica[i][j+1]--;
								raskrsnica[i-1][j-1]--;
								
							}
							
						} else {
							
							if ( j == 0){ // gore
								
								raskrsnica[i][j+1]--;
								raskrsnica[i-1][j]--;
								raskrsnica[i+1][j]--;
						
							} else if ( j == height - 1){ // dole
								
								raskrsnica[i+1][j]--;
								raskrsnica[i][j-1]--;
								raskrsnica[i-1][j]--;
								
							} else { //sredina
								
								raskrsnica[i+1][j]--;
								raskrsnica[i][j+1]--;
								raskrsnica[i-1][j]--;
								raskrsnica[i][j-1]--;
								
							}
							
						}
					}
				}
			}
			
			
		}
		
	}
	
	private static void includeDeadEndsWithHearts(){
		
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				if (lavirint[i][j] == TileType.HEART){

					AStar astar = new AStar(i,j);
					State pocetno = new State();
					for (int k = 0; k < width; k++){
						for (int l = 0; l < height; l++){
							if (raskrsnica[k][l] >= 2 && lavirint[k][l] == TileType.PASSABLE){
								pocetno.setStateCoords(k, l);
								break;
							}
						}
					}
					
					ArrayList<State> resenje = new ArrayList<State>();
					State solution = astar.search(pocetno);
					if (solution != null){
						resenje = solution.path();
					}
					
					for (State state : resenje) {
						if (raskrsnica[state.markI][state.markJ] < 2){
							heartRaskrsnica[state.markI][state.markJ] = 2;
						}
					}
					
				}
			}
		}
	}
	
	private static void fixDeadEndsHearts(){
		int[][] tempRaskrsnica = new int[width][height];
		for(int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				tempRaskrsnica[i][j] = heartRaskrsnica[i][j];
			}
		}
		
		
		for(int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				if (tempRaskrsnica[i][j] > 0 && raskrsnica[i][j] > 0){
					if ( i == 0){
						if ( j == 0){ // gore-levo
							
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] && tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						} else if ( j == height - 1){ // dole-levo
							
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						} else { // levo
							
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] && tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						}
						
					} else if ( i == width - 1){ 
						if ( j == 0){ // gore-desno
							
							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] &&tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
	
						} else if ( j == height - 1){ // dole-desno

							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						} else { // desno
							
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] && tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						}
						
					} else {
						
						if ( j == 0){ // gore
							
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] && tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						} else if ( j == height - 1){ // dole
							
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							
						} else { //sredina
							
							if (raskrsnica[i+1][j] != tempRaskrsnica[i+1][j] && tempRaskrsnica[i+1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i-1][j] != tempRaskrsnica[i-1][j] && tempRaskrsnica[i-1][j] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j-1] != tempRaskrsnica[i][j-1] && tempRaskrsnica[i][j-1] > 0){
								heartRaskrsnica[i][j]++;
							}
							if (raskrsnica[i][j+1] != tempRaskrsnica[i][j+1] && tempRaskrsnica[i][j+1] > 0){
								heartRaskrsnica[i][j]++;
							}
						}
						
					}
				}
			}
		}
		
	}
}
