package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceTLTile extends Tile{
	
	public GrassFenceTLTile(int id){
		super(Assets.grassFenceTL, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
