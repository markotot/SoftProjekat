package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceBRTile extends Tile{
	
	public GrassFenceBRTile(int id){
		super(Assets.grassFenceBR, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
