package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceBLTile extends Tile{
	
	public GrassFenceBLTile(int id){
		super(Assets.grassFenceBL, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
