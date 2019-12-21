package tilegame.states;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.creatures.Player;
import tilegame.gui.UserInterface;
import tilegame.pathfinding.AreaMap;
import tilegame.worlds.World;

public class GameState extends State{

	private World world;
	private UserInterface userInterface;
	
	public GameState(Handler handler){
		super(handler);
		
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
		AreaMap.fillRaskrsnica(handler);
		userInterface = new UserInterface(handler);
	}
	
	@Override
	public void tick() {
		
		world.tick();
		userInterface.tick();
		enableAttackModes();
	
	}
	
	public void enableAttackModes(){
		Player.enableAttackMode();
	}
	
	@Override
	public void render(Graphics g) {
		world.render(g);
		userInterface.render(g);
	}
	
	public Player getPlayer(){
		return world.getEntityManager().getPlayer();
	}

}
