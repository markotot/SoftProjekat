package tilegame.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import tilegame.display.Display;

public class MouseManager implements MouseListener {

	private boolean[] buttons;
	public static boolean noButton,leftButton, middleButton, rightButton;
	
	public MouseManager() {
		buttons = new boolean[4];
	}
	
	public void tick(){
		
		noButton = buttons[0];
		leftButton = buttons[1];
		middleButton = buttons[2];
		rightButton = buttons[3];
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		buttons[arg0.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		buttons[arg0.getButton()] = false;
	}
	
	public static Point getMouseLocation(){
		Point screenLocation = Display.getScreenLocation();
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		Point relativeLocation = new Point(mouseLocation.x - screenLocation.x, mouseLocation.y - screenLocation.y);
		return relativeLocation;
	}

}
