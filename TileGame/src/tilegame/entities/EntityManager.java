package tilegame.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import tilegame.Handler;
import tilegame.entities.creatures.Creature;
import tilegame.entities.creatures.Player;
import tilegame.entities.staticEntities.StaticEntity;

public class EntityManager {

	private Handler handler;
	private Player player;
	private ArrayList<Creature> creatures;
	private ArrayList<StaticEntity> staticEntities;
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		creatures = new ArrayList<Creature>();
		staticEntities = new ArrayList<StaticEntity>();
	}
	
	public void tick(){
		
		for (int i = 0; i < staticEntities.size(); i++){
			StaticEntity se = staticEntities.get(i);
			se.tick();
		}
		
		for (int i = 0; i < creatures.size(); i++) {
			Entity c = creatures.get(i);
			c.tick();
		}
		

		player.tick();
	}
	
	public void render(Graphics g){
		for (StaticEntity se: staticEntities){
			se.render(g);
		}
		
		for (Creature c : creatures) {
			c.render(g);
		}
		

		player.render(g);
	}
	
	public void addCreature(Creature c){
		creatures.add(c);
	}
	
	public void addStaticEntity(StaticEntity se){
		staticEntities.add(se);
	}
	
	//GETTERS && SETTERS

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	public ArrayList<StaticEntity> getStaticEntities() {
		return staticEntities;
	}

	public void setStaticEntities(ArrayList<StaticEntity> staticEntities) {
		this.staticEntities = staticEntities;
	}
	
	public ArrayList<StaticEntity> getStaticEntitiesByClass(Class<?> klasa){
		
		ArrayList<StaticEntity> retVal = new ArrayList<StaticEntity>();
		for (StaticEntity staticEntity : this.staticEntities) {
			if(klasa.isInstance(staticEntity)){
				retVal.add(staticEntity);
			}
		}
		return retVal;
	}
}
