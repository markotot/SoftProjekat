package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassSignLeftTile extends Tile {

	public GrassSignLeftTile(int id){
		super(Assets.grassSignLeft, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
