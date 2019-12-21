package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassBushTile extends Tile{
	
	public GrassBushTile(int id){
		super(Assets.grassBush, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
