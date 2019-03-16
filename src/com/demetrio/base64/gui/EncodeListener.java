package com.demetrio.base64.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.demetrio.base64.base64.Base64;

/** Listener class for encoding a file selected by a {@link javax.swing.JFileChooser JFileChooser}.
 * 	It has a default constructor (because it isn't specified), a inner class {@link OpenSaveListener} and 
 * 	implements {@link java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent)} 
 * 	from the {@link java.awt.event.ActionListener ActionListener} interface.
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see java.awt.event.ActionListener ActionListener */
class EncodeListener implements ActionListener 
{
	/** Inner class that implements {@link java.awt.event.ActionListener ActionListener} interface.
	 * 	It has a default constructor and defines the method {@link java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 * 	actionPerformed(ActionEvent)}.
	 * 	This class is a listener for opening and encoding a file in base64 or saving the result of the encoding in a file.
	 * 	@author Alessandro Chiariello (Demetrio)
	 * 	@version 1.0
	 * 	@see java.awt.event.ActionListener ActionListener */
	class OpenSaveListener implements ActionListener
	{
		/** Implementation of {@link java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent)}
		 * 	from the {@link java.awt.event.ActionListener ActionListener} interface.
		 * 	This method, first of all, checks if the event was fired by the <b>open</b> button or the <b>save</b> button:<br/>
		 * 	<ul>
		 * 		<li>
		 * 			If it was fired by the <b>Open</b> button, it opens a <i>file choosing</i> dialog; after the user choose
		 * 			the file to be encoded, it encodes the file with the {@link com.demetrio.base64.base64.Base64#encode(RandomAccessFile)
		 * 			Base64.encode(RandomAccessFile)} method and set the text area of the interface with the base64 string.
		 * 		</li>
		 * 		<li>
		 * 			If it was fired by the <b>Save</b> button, it opens a <i>file saving</i> dialog; after the user specifies
		 * 			the new file, it creates this file and writes the string contained in the text area to it.
		 * 			It shows a message and doesn't write anything if the file already exists.
		 * 		</li>
		 * </ul>
		 * @author Alessandro Chiariello (Demetrio)
		 * @version 1.0
		 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent)
		 * */	
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			String action = ((JButton) event.getSource()).getText();
			boolean isOpen = action.equals("Open");
			JFileChooser c = new JFileChooser();
		     int rVal = isOpen ? c.showOpenDialog(window) : c.showSaveDialog(window);
		     if (rVal == JFileChooser.APPROVE_OPTION) 
		     {
		        if (isOpen)
		        {
					try 
					{
						result = Base64.encode(new RandomAccessFile(c.getSelectedFile().getAbsolutePath(),"r"));
						text.setText(null);
						int wrap = text.getWidth() / text.getFontMetrics(text.getFont()).stringWidth("A") - 1;
						int i;
						for(i=0;i+wrap<result.length();i+=wrap)
						{
							text.append(result.substring(i,i+wrap));
							text.append("\n");
						}
						text.append(result.substring(i));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Error reading in EncodeListener. Report this bug!!");
					}
					catch (IndexOutOfBoundsException e)
					{
						JOptionPane.showMessageDialog(null,"Error inserting string. Report this bug!!");
					}
		        }
		        else
		        {
		        	File f = c.getSelectedFile();
		        	try 
		        	{
						if (f.createNewFile())
						{
							FileWriter out = null;
							try 
							{
								out = new FileWriter(f);
								out.write(result);
							} 
							catch (IOException e) 
							{
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,"Error writing in EncodeListener. Report this bug!!");
							}
							finally
							{
								out.close();
							}
						}
						else
							JOptionPane.showMessageDialog(c,"File already exists");
					} 
		        	catch (IOException e) 
		        	{
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Errore reading file in EncodeListener. Report this bug!!");
					}
		        }
		     }
		     if (rVal == JFileChooser.CANCEL_OPTION) {
		        //TODO nothing
		     }
		}
		
	}
	
	/** Implementation of {@link java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent)}
	 * 	from the {@link java.awt.event.ActionListener ActionListener} interface.
	 * 	This method creates a new window with:
	 * 	<ul>
	 * 		<li>
	 * 			Two buttons, <b>Open</b> and <b>Save</b>, for respectively opening and encoding a file and saving the result
	 * 			of the encoding in a file. An {@link OpenSaveListener} is added to the buttons.
	 * 		</li>
	 * 		<li>
	 * 			A text area, in which the result of the encoding of a file is inserted.
	 * 		</li>
	 * </ul>
	 * @author Alessandro Chiariello (Demetrio)
	 * @version 1.0
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		window = new JFrame("Encode");
		window.setSize(640,480);
		window.setLocationRelativeTo(SwingUtilities.getRoot((JButton) event.getSource()));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		
		JPanel northPanel = new JPanel(new GridLayout(1,5));
		JButton open = new JButton("Open"), save = new JButton("Save");
		OpenSaveListener listener = new OpenSaveListener();
		open.addActionListener(listener);
		save.addActionListener(listener);
		northPanel.add(new JLabel());
		northPanel.add(open);
		northPanel.add(new JLabel());
		northPanel.add(save);
		northPanel.add(new JLabel());
		
		text = new JTextArea();
		text.setEditable(false);
		text.setFont(new Font(Font.MONOSPACED,text.getFont().getStyle(),text.getFont().getSize()));
		JScrollPane area = new JScrollPane(text);
		area.setBorder(new TitledBorder(new EtchedBorder(),"Encoded Data"));
		
		window.add(northPanel,BorderLayout.NORTH);
		window.add(area);
		
		window.setVisible(true);
	}
	
	private JFrame window;
	private JTextArea text;
	private String result="";
}
