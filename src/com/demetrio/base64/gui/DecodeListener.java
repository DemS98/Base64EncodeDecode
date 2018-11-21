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

class DecodeListener implements ActionListener {

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
					Base64.base64decode(new RandomAccessFile(decFile,"r")).writeTo(new FileOutputStream(saveFile));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Errore: lettura DecodeListener. Segnalare il bug!!");
				}
				catch (Base64DecodeException e)
				{
					JOptionPane.showMessageDialog(c,"Errore: questo file non è codificato in base64!!");
				}
			}
			if (rval==JFileChooser.CANCEL_OPTION)
			{
				//TODO nothing
			}
		}
		
	}

}
