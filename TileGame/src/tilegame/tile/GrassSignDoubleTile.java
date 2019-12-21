package tilegame.tile;

import tilegame.gfx.Assets;

public class GrassSignDoubleTile extends Tile {

	public GrassSignDoubleTile(int id){
		super(Assets.grassSignDouble, id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}
}
