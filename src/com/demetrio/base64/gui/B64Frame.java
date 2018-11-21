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

public class B64Frame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4233267367718036468L;

	public B64Frame(int width,int height)
	{
		super("Base64 Encode Decode");
		
		/* Pannello nord */
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
		
		
		/* Pannello centrale */
		
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
		
		/* Costruzione frame */
		
		this.setSize(width, height);
		this.add(BorderLayout.NORTH,northPanel);
		this.add(BorderLayout.CENTER,fields);
		this.add(BorderLayout.SOUTH,new JLabel("Scritto da Alessandro Chiariello. Licenza GNU GPL",SwingConstants.RIGHT));
	}
}
