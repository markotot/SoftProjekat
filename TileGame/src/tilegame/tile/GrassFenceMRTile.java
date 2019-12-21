package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceMRTile extends Tile{
	
	public GrassFenceMRTile(int id){
		super(Assets.grassFenceMR, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
