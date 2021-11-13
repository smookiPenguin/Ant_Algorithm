package View;

import java.awt.Window;
import java.awt.event.*;


public class FrameClose extends WindowAdapter {
	private Window w;
	
	public FrameClose(Window w) {
		this.w = w;
	}

	public void windowClosing(WindowEvent arg0) {

		w.dispose();
	}

}
