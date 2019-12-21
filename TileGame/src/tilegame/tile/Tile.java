package tilegame.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	enum TileType{START, FINISH, PASSABLE, SOLID, SOLID_PLAYER,SOLID_ENEMY}
	//STATIC STUFF HERE
	public static Tile[] tiles = new Tile[256];
	
	//NEW TILES
	public static Tile grassEmptyTile = new GrassEmptyTile(0);
	public static Tile grassGrass1Tile = new GrassGrass1Tile(1);
	public static Tile grassGrass2Tile = new GrassGrass2Tile(2);
	public static Tile grassGrass3Tile = new GrassGrass3Tile(3);
	public static Tile grassFlower1Tile = new GrassFlower1Tile(4);
	public static Tile grassFlower2Tile = new GrassFlower2Tile(5);
	public static Tile grassFlower3Tile = new GrassFlower3Tile(6);
	public static Tile grassFlower4Tile = new GrassFlower4Tile(7);
	public static Tile grassFlower5Tile = new GrassFlower5Tile(8);
	public static Tile grassStone1Tile = new GrassStone1Tile(9);
	public static Tile grassStone2Tile = new GrassStone2Tile(10);
	public static Tile grassStone3Tile = new GrassStone3Tile(11);
	public static Tile grassStone4Tile = new GrassStone4Tile(12);
	public static Tile grassSignRightTile = new GrassSignRightTile(13);
	public static Tile grassSignLeftTile = new GrassSignLeftTile(14);
	public static Tile grassSignDoubleTile = new GrassSignDoubleTile(15);
	public static Tile grassFenceBLTile = new GrassFenceBLTile(16);
	public static Tile grassFenceBMTile = new GrassFenceBMTile(17);
	public static Tile grassFenceBRTile = new GrassFenceBRTile(18);
	public static Tile grassFenceMLTile = new GrassFenceMLTile(19);
	public static Tile grassFenceMRTile = new GrassFenceMRTile(20);
	public static Tile grassFenceTLTile = new GrassFenceTLTile(21);
	public static Tile grassFenceTMTile = new GrassFenceTMTile(22);
	public static Tile grassFenceTRTile = new GrassFenceTRTile(23);
	public static Tile grassFenceLeftEndTile = new GrassFenceLeftEndTile(24);
	// FALI grassFenceRightEndTile
	public static Tile grassBushTile = new GrassBushTile(25);
	
	
	//CLASS
	public static final int TILE_WIDTH = 64,
							TILE_HEIGHT = 64;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);	
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public int getId(){
		return id;
	}
}
