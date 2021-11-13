package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Model.Area;

/**
 * Create the frame with AreaPanel and ControlPanel
 * @author Damien
 *
 */
public class Frame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static int WIDTH = 900,
			HEIGHT = 500,
			AREA_WIDTH = 500,
			AREA_HEIGHT = 400;
	
	public Frame(Area area) {
		
		setTitle("Traveling Salesman Problem");
		setSize(WIDTH,HEIGHT);
		setLayout(new BorderLayout());
		
		AreaPanel ap = new AreaPanel(area);
		ControlPanel cp = new ControlPanel(area);
		
		add(ap, BorderLayout.CENTER);
		add(cp, BorderLayout.EAST);
		
		addWindowListener(new FrameClose(this));
		setVisible(true);	

	}

}
