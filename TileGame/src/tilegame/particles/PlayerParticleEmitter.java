package tilegame.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import tilegame.Handler;
import tilegame.utils.Utils;

public class PlayerParticleEmitter {

//Emitter
	private float x,y;
	private Color color;
	private ArrayList<PlayerParticle> particles;
	private Handler handler;
	//Calculations
	private int amount, generated;
	private int particleWidth, particleHeight;
	private int minSpeed, maxSpeed;
	private int minAngle, maxAngle;
	private int minLife, maxLife;
	
	public PlayerParticleEmitter(Handler handler, int amount, Color color, int x, int y,
					int particleWidth, int particleHeight,
					int minSpeed, int maxSpeed, int minAngle,
					int maxAngle, int minLife, int maxLife){
		this.handler = handler;
		this.amount = amount;
		this.color = color;
		this.x = x;
		this.y = y;
		this.particleWidth = particleWidth;
		this.particleHeight = particleHeight;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		this.minLife = minLife;
		this.maxLife = maxLife;
		particles = new ArrayList<PlayerParticle>();
	}
	
	public void tick(){
		if (amount == -1 || generated < amount){
			emit();
		}
		ArrayList<PlayerParticle> particleCopy =new ArrayList<PlayerParticle>();
		
		for (PlayerParticle p : particles) {
			if(!p.tick()){
				particleCopy.add(p);
				
			}
		}
		particles = particleCopy;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		for (PlayerParticle p : particles) {
			p.render(g);
		}
		g.setColor(Color.white);
	}
	
	private void emit(){
		float param = Utils.returnRandomFloat(minAngle, maxAngle);
		float angleR = (float) (param * (Math.PI / 180)) ;
		int speed = (int) Utils.returnRandomFloat(minSpeed, maxSpeed);
		float angleX = (float) (speed * Math.cos(angleR));
		float angleY = (float) (-speed * Math.sin(angleR));
		int particleMaxLife = (int) Utils.returnRandomFloat(minLife, maxLife);
		
		
		particles.add(new PlayerParticle(handler, x, y, particleWidth, particleHeight,
						angleX, angleY, particleMaxLife));
		generated++;
	}
}