package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceTRTile extends Tile{
	
	public GrassFenceTRTile(int id){
		super(Assets.grassFenceTR, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
