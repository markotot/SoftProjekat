package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceBMTile extends Tile{
	
	public GrassFenceBMTile(int id){
		super(Assets.grassFenceBM, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
