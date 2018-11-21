package com.demetrio.base64.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import com.demetrio.base64.base64.Base64;
import com.demetrio.base64.base64.Base64DecodeException;

class PlainListener extends KeyAdapter 
{
	public PlainListener(JTextArea output,boolean plain)
	{
		this.output = output;
		this.plain = plain;
	}
	
	public void keyReleased(KeyEvent e)
	{
		JTextArea input = (JTextArea) e.getSource();
		try
		{
			if (plain)
				output.setText(Base64.base64encode(input.getText()));
			else
				output.setText(Base64.base64decode(input.getText()));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}
		  catch(Base64DecodeException e2) {
			  e2.printStackTrace();
			  output.setText(null);
		  }
	}
	
	private JTextArea output;
	private boolean plain;
}
