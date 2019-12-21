package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassSignRightTile extends Tile {

	public GrassSignRightTile(int id){
		super(Assets.grassSignRight, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
