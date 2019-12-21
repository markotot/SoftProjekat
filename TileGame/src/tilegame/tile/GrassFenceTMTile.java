package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceTMTile extends Tile{
	
	public GrassFenceTMTile(int id){
		super(Assets.grassFenceTM, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
