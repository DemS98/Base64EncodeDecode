package com.demetrio.base64.main;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.demetrio.base64.gui.B64Frame;

/** Class that contains {@link #main(String[])} method for starting the application
 * 	and a static initialization block for setting the system UI default look.
 * 	@see #main(String[])
 * 	@version 1.0
 * 	@author Alessandro Chiariello (Demetrio) */
public class Main 
{
	/** It constructs the {@link com.demetrio.base64.gui.B64Frame B64Frame} with
	 *  a size of 800x600, sets its close operation to EXIT, sets its location at the
	 *  center of the screen and makes it visible.
	 *  @param args - not used
	 *  @version 1.0
	 *  @author Alessandro Chiariello (Demetrio)
	 *  @see com.demetrio.base64.gui.B64Frame B64Frame */
	public static void main(String[] args)
	{
		B64Frame test = new B64Frame(800,600);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setLocationRelativeTo(null);
		test.setVisible(true);
	}
	
	static
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
