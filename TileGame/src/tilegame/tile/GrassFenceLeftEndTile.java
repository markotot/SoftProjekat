package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassFenceLeftEndTile extends Tile{
	
	public GrassFenceLeftEndTile(int id){
		super(Assets.grassFenceLeftEnd, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
