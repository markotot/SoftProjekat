package tilegame.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import tilegame.utils.Utils;

public class ParticleEmitter {

	//Emitter
	private float x,y;
	private Color color;
	private ArrayList<Particle> particles;
	
	//Calculations
	private int amount, generated;
	private int particleWidth, particleHeight;
	private int minSpeed, maxSpeed;
	private int minAngle, maxAngle;
	private int minLife, maxLife;
	
	public ParticleEmitter(int amount, Color color, int x, int y,
					int particleWidth, int particleHeight,
					int minSpeed, int maxSpeed, int minAngle,
					int maxAngle, int minLife, int maxLife){
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
		particles = new ArrayList<Particle>();
	}
	
	public void tick(){
		if (amount == -1 || generated < amount){
			emit();
		}
		ArrayList<Particle> particleCopy =new ArrayList<Particle>();
		
		for (Particle p : particles) {
			if(!p.tick()){
				particleCopy.add(p);
				// ne znam zasto
				//generated--;
			}
		}
		particles = particleCopy;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		for (Particle p : particles) {
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
		
		particles.add(new Particle(x, y, particleWidth, particleHeight,
						angleX, angleY, particleMaxLife));
		generated++;
	}
}
