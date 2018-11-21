package com.demetrio.base64.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.demetrio.base64.gui.B64Frame;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException, IOException
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
			
		}
	}
	
}
