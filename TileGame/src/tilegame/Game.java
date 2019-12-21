package tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import tilegame.display.Display;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.states.GameState;
import tilegame.states.MenuState;
import tilegame.states.State;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	private State gameState;
	private State menuState;
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private long lastPause;
	private boolean paused;
	//Camera
	private GameCamera gameCamera;
	//Handler
	private Handler handler;

	public Game(String title, int width, int height){
		
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		lastPause = System.nanoTime();
		paused = false;
		
	}
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Display.canvas.addMouseListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
	}
		
	private void tick(){
		
		checkPause();
		
		keyManager.tick();
		mouseManager.tick();
		 
		
		if (State.getState() != null && !paused){
			State.getState().tick();
		}
	
	}
	
	private void checkPause(){
		
		long currTime = System.nanoTime();
		
		if ( KeyManager.p && currTime - lastPause > 1000000000){
			paused = !paused;
			lastPause = currTime;
			
		}
	}
	
	private void render(){
		bs = Display.canvas.getBufferStrategy();
		if (bs == null){
			Display.canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		//Clear Screen
		
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		if (State.getState() != null){
			State.getState().render(g);
		}
		
		//End Drawing!
		bs.show();
		g.dispose();
		
	}
	
	public void run(){
		
		init();
		//FPS setup
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		@SuppressWarnings("unused")
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
		
		while(running){
			
			now = System.nanoTime();
			delta += (now-lastTime) / timePerTick;
			timer += now-lastTime;
			lastTime = now;
			
			if (delta >= 1){
				tick();
				render();
				ticks++;
				delta = 0;
				//delta--;
			}
			
		}
		
		stop();
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public State getGameState(){
		return gameState;
	}
	
	public State getMenuState(){
		return menuState;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public synchronized void start(){
		if (running){
			return;
		} else {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public synchronized void stop(){
		if (!running){
			return;
		} else {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
}
