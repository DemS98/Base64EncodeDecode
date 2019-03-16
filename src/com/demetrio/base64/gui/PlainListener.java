package com.demetrio.base64.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

import com.demetrio.base64.base64.Base64;
import com.demetrio.base64.base64.Base64DecodeException;

/** Class for the main application interface: it realises the translation from plain text to base64 text
 * 	and vice versa. It's a subclass of {@link java.awt.event.KeyAdapter KeyAdapter} and this allows to
 * 	implement only the method of interest: {@link java.awt.event.KeyAdapter#keyReleased(KeyEvent) keyReleased(KeyEvent)}.
 * 	It contains also a {@link #PlainListener(JTextArea, boolean) constructor} with two arguments.
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see java.awt.event.KeyAdapter KeyAdapter
 * 	@see java.awt.event.KeyAdapter#keyReleased(KeyEvent) keyReleased(KeyEvent)*/
class PlainListener extends KeyAdapter 
{
	/** Constructor that set the output text area (in which the translated string will be inserted) and
	 * 	a boolean that, if set {@code true}, indicates that the input text is plain text, otherwise
	 * 	is base64 text.
	 * 	@param output - the text area in which the output will be inserted
	 * 	@param plain - the boolean indicating if the input is plain text or not
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0 */
	public PlainListener(JTextArea output,boolean plain)
	{
		this.output = output;
		this.plain = plain;
	}
	
	/** Overriding of {@link java.awt.event.KeyAdapter#keyReleased(KeyEvent) keyReleased(KeyEvent)} from the
	 * 	{@link java.awt.event.KeyAdapter KeyAdapter} class.
	 * 	This method takes a reference of the input text area and check the value of instance variable <b>plain</b>:
	 * 	<ul>
	 * 		<li>
	 * 			if {@code true}, it means that the input text is plain text so it is translated in base64 text.
	 *  	</li>
	 *  	<li>
	 *  		if {@code false}, it means that the input text is base64 text so it is translated in plain text.
	 *  	</li>
	 *  </ul>
	 *  Finally, the translated string is inserted in the output text area, which reference is taken in the
	 *  {@link #PlainListener(JTextArea, boolean) constructor}.
	 *  @author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.awt.event.KeyAdapter#keyReleased(KeyEvent) keyReleased(KeyEvent)*/
	@Override
	public void keyReleased(KeyEvent e)
	{
		JTextArea input = (JTextArea) e.getSource();
		try
		{
			if (plain)
				output.setText(Base64.encode(input.getText()));
			else
				output.setText(Base64.decode(input.getText()));
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
