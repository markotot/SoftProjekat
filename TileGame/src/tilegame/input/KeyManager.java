package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public static boolean up, down, left, right;
	
	public static boolean c,p;
	public static boolean space;
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick(){
		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		c = keys[KeyEvent.VK_C];
		p = keys[KeyEvent.VK_P];
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
