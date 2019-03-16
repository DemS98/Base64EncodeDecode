package com.demetrio.base64.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/** Class for constructing the main application interface.<br/>
 * 	It's a subclass of {@link javax.swing.JFrame JFrame} without any override.<br/>
 * 	It has a {@link #B64Frame(int, int) public constructor} that creates the interface.
 * 	@author Alessandro Chiariello (Demetrio)
 * 	@version 1.0
 * 	@see {@link #B64Frame(int, int)}*/
public class B64Frame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4233267367718036468L;
	
	/** This public constructor creates the main application interface.
	 * 	It consists in 3 panels:
	 * 	<ul>
	 * 		<li>
	 * 			North Panel - the title of the application and two buttons, <b>Encode file</b>
	 * 			and <b>Decode file</b>, respectively for encoding a file from plain text to base64 text and
	 * 			for decoding a file from base64 text to plain text
	 * 		</li>
	 * 		<li>
	 * 			Central Panel - two text areas, one for plain text and the other for base64 text. When entering a text
	 * 			in an area, it's translated in the other area
	 * 		</li>
	 * 		<li>
	 * 			South Panel - it's only a label with the autor of the application and the license.
	 * 		</li>
	 * The constructor takes two parameter for sizing the frame but it doesn't set the location, the default close operation
	 * and the visibility. This is done in the {@link com.demetrio.base64.main.Main#main(String[]) main} method.
	 * @param width - the width of the window containing the frame
	 * @param height - the height of the window containing the frame
	 * @author Alessandro Chiariello (Demetrio)
	 * @version 1.0 */
	public B64Frame(int width,int height)
	{
		super("Base64 Encode Decode");
		
		/* North Panel */
		JPanel northPanel = new JPanel(new GridLayout(2,5));
		
		JLabel intro1 = new JLabel("Base64",SwingConstants.CENTER);
		intro1.setFont(new Font(intro1.getFont().getName(),Font.BOLD,intro1.getFont().getSize()+10));
		JLabel intro2 = new JLabel("Encode",SwingConstants.CENTER);
		intro2.setFont(new Font(intro2.getFont().getName(),Font.BOLD,intro2.getFont().getSize()+10));
		JLabel intro3 = new JLabel("Decode",SwingConstants.CENTER);
		intro3.setFont(new Font(intro3.getFont().getName(),Font.BOLD,intro3.getFont().getSize()+10));
		JButton encode = new JButton("Encode file"); encode.addActionListener(new EncodeListener());
		JButton decode = new JButton("Decode file"); decode.addActionListener(new DecodeListener());
		
		northPanel.add(new JLabel());
		northPanel.add(intro1);
		northPanel.add(intro2);
		northPanel.add(intro3);
		northPanel.add(new JLabel());
		northPanel.add(new JLabel());
		northPanel.add(encode);
		northPanel.add(new JLabel());
		northPanel.add(decode);
		northPanel.add(new JLabel());
		
		
		/* Central Panel */
		
		JPanel fields = new JPanel(new GridLayout(2,1,0,10));
		
		JTextArea plain = new JTextArea(8,25); 
		plain.setLineWrap(true);
		//plain.setFont(new Font(Font.DIALOG,Font.PLAIN,plain.getFont().getSize()));
		JScrollPane one = new JScrollPane(plain);
		one.setBorder(new TitledBorder(new EtchedBorder(),"Plain Text"));
		
		JTextArea base64 = new JTextArea(8,25);
		base64.setLineWrap(true);
		JScrollPane two = new JScrollPane(base64);
		two.setBorder(new TitledBorder(new EtchedBorder(),"Base64 Text"));
		
		fields.add(one);
		fields.add(two);
		
		plain.addKeyListener(new PlainListener(base64,true));
		base64.addKeyListener(new PlainListener(plain,false));
		
		/* Frame construction */
		
		setSize(width, height);
		add(northPanel,BorderLayout.NORTH);
		add(fields,BorderLayout.CENTER);
		add(new JLabel("Developed by Alessandro Chiariello (Demetrio). GNU GPL",SwingConstants.RIGHT),BorderLayout.SOUTH);
	}
}
