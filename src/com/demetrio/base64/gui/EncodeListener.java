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

class EncodeListener implements ActionListener 
{
	class OpenListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			String action = ((JButton) event.getSource()).getText();
			JFileChooser c = new JFileChooser();
		     int rVal = action.equals("Open") ? c.showOpenDialog(window) : c.showSaveDialog(window);
		     if (rVal == JFileChooser.APPROVE_OPTION) 
		     {
		        if (action.equals("Open"))
		        {
					try 
					{
						result = Base64.base64encode(new RandomAccessFile(c.getSelectedFile().getAbsolutePath(),"r"));
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
						JOptionPane.showMessageDialog(null,"Errore: lettura EncodeListener. Segnalare il bug!!");
					}
					catch (IndexOutOfBoundsException e)
					{
						JOptionPane.showMessageDialog(null,"Errore: inserimento stringa. Segnalare il bug!!");
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
								JOptionPane.showMessageDialog(null,"Errore: scrittura EncodeListener. Segnalare il bug!!");
							}
							finally
							{
								out.close();
							}
						}
						else
							JOptionPane.showMessageDialog(c,"Il file esiste già");
					} 
		        	catch (IOException e) 
		        	{
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Errore: lettura file EncodeListener. Segnalare il bug!!");
					}
		        }
		     }
		     if (rVal == JFileChooser.CANCEL_OPTION) {
		        //TODO nothing
		     }
		}
		
	}
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
		open.addActionListener(new OpenListener());
		save.addActionListener(new OpenListener());
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
		
		window.add(BorderLayout.NORTH,northPanel);
		window.add(area);
		
		window.setVisible(true);
	}
	
	private JFrame window;
	private JTextArea text;
	private String result="";
}
