package tilegame.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class Display {

	private static JFrame frame;
	public static Canvas canvas;
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack(); // to see all of the canvas

	}
	
	public static Canvas getCanvas(){
		return canvas;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public static Point getScreenLocation(){
		return getCanvas().getLocationOnScreen();
	}
	
	
	
}

