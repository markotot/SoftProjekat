package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceMLTile extends Tile{
	
	public GrassFenceMLTile(int id){
		super(Assets.grassFenceML, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
