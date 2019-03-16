package com.demetrio.base64.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.demetrio.base64.base64.Base64;
import com.demetrio.base64.base64.Base64DecodeException;

/** Listener class for decoding a file selected by a {@link javax.swing.JFileChooser JFileChooser}.
 * 	It has a default constructor (because it isn't specified) and implements {@link java.awt.event.ActionListener#actionPerformed(ActionEvent) 
 *  actionPerformed(ActionEvent)} from the {@link java.awt.event.ActionListener ActionListener} interface. 
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see java.awt.event.ActionListener ActionListener*/
class DecodeListener implements ActionListener {

	/** Implementation of {@link java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent)}
	 * 	from the {@link java.awt.event.ActionListener ActionListener} interface.
	 * 	This method opens a <i>file choosing</i> dialog that allows the user to choose a file, containing base64 text;
	 * 	than it opens a <i>file saving</i> dialog for saving the decoded text in a file (it can be an existing file or a new file
	 * 	specified in the dialog text field); finally, it decodes the base64 input file with the {@link com.demetrio.base64.base64.Base64#decode(RandomAccessFile)
	 * 	Base64.decode(RandomAccessFile)} method and save the result in the output file.
	 * 	If the input file doesn't contain base64 text, a message dialog appears indicating that.
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		String decFile=null;
		boolean select = false;
		JFileChooser c = new JFileChooser();
		int rval = c.showOpenDialog(SwingUtilities.getRoot((JButton) event.getSource()));
		if (rval==JFileChooser.APPROVE_OPTION)
		{
			decFile = c.getSelectedFile().getAbsolutePath();
			select = true;
		}
		if (rval==JFileChooser.CANCEL_OPTION)
		{
			//TODO nothing
		}
		
		if (select)
		{
			rval = c.showSaveDialog(SwingUtilities.getRoot((JButton) event.getSource()));
			if (rval==JFileChooser.APPROVE_OPTION)
			{
				String saveFile = c.getSelectedFile().getAbsolutePath();
				try 
				{
					Base64.decode(new RandomAccessFile(decFile,"r")).writeTo(new FileOutputStream(saveFile));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error reading file in class DecodeListener. Report this bug!!");
				}
				catch (Base64DecodeException e)
				{
					JOptionPane.showMessageDialog(c,"Error: this file isn't coded in base64!!");
				}
			}
			if (rval==JFileChooser.CANCEL_OPTION)
			{
				//TODO nothing
			}
		}
		
	}

}
