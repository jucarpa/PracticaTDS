package vista;

import javax.swing.JPanel;

import manejadores.ManejadorChatCI;
import manejadores.ManejadorChatG;
import tds.BubbleText;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;
import java.awt.Component;

public class PEmoticonos extends JPanel {

	private int nEmoticonos;
	private JButton lbl_0;
	private JButton lbl_1;
	private JButton lbl_2;
	private JButton lbl_3;
	private JButton lbl_4;
	private JButton lbl_5;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private PChatG vG;
	private PChatCI vCI;
	/**
	 * Create the panel.
	 */
	public PEmoticonos(PChatG vG) {
		this();
		this.vG = vG;
	}
	
	public PEmoticonos(PChatCI vCI) {
		this();
		this.vCI = vCI;
	}
	
	public PEmoticonos() {
		setBackground(Color.WHITE);
		nEmoticonos = 5;
		ImageIcon im0 = BubbleText.getEmoji(0);
		ImageIcon im1 = BubbleText.getEmoji(1);
		ImageIcon im2 = BubbleText.getEmoji(2);
		ImageIcon im3 = BubbleText.getEmoji(3);
		ImageIcon im4 = BubbleText.getEmoji(4);
		ImageIcon im5 = BubbleText.getEmoji(5);
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("<-");
		panel.add(btnNewButton);
		
		JButton button = new JButton("->");
		panel.add(button);
		
		panel_3 = new JPanel();
		panel_3.setOpaque(false);
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setSize(new Dimension(50, 0));
		panel_3.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		
		lbl_0 = new JButton("");
		lbl_0.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lbl_0);
		lbl_0.setBackground(Color.WHITE);
		
		lbl_0.setIcon(im0);
		lbl_0.addActionListener(getIcon());
		
		lbl_0.setMargin(new Insets(2, 50, 2, 50));
		lbl_0.setLocation(new Point(10, 10));
		lbl_0.setMinimumSize(new Dimension(50, 50));
		lbl_0.setMaximumSize(new Dimension(50, 50));
		
		lbl_2 = new JButton("");
		lbl_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lbl_2);
		lbl_2.setBackground(Color.WHITE);
		lbl_2.setIcon(im2);
		lbl_2.addActionListener(getIcon());
		
		lbl_2.setMargin(new Insets(2, 50, 2, 50));
		lbl_2.setLocation(new Point(10, 10));
		lbl_2.setMinimumSize(new Dimension(50, 50));
		lbl_2.setMaximumSize(new Dimension(50, 50));
		
		lbl_4 = new JButton("");
		lbl_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lbl_4);
		lbl_4.setBackground(Color.WHITE);
		lbl_4.setIcon(im4);
		lbl_4.addActionListener(getIcon());
		
		lbl_4.setMargin(new Insets(2, 50, 2, 50));
		lbl_4.setLocation(new Point(50, 50));
		lbl_4.setMinimumSize(new Dimension(50, 50));
		lbl_4.setMaximumSize(new Dimension(50, 50));
		
		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_3.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		lbl_1 = new JButton("");
		lbl_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lbl_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lbl_1);
		lbl_1.setBackground(Color.WHITE);
		lbl_1.setIcon(im1);
		lbl_1.addActionListener(getIcon());
		
		lbl_1.setMargin(new Insets(2, 50, 2, 50));
		lbl_1.setLocation(new Point(10, 10));
		lbl_1.setMinimumSize(new Dimension(50, 50));
		lbl_1.setMaximumSize(new Dimension(50, 50));
		
		lbl_3 = new JButton("");
		lbl_3.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lbl_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lbl_3);
		lbl_3.setBackground(Color.WHITE);
		lbl_3.setIcon(im3);
		lbl_3.addActionListener(getIcon());
		
		lbl_3.setMargin(new Insets(2, 50, 2, 50));
		lbl_3.setLocation(new Point(10, 10));
		lbl_3.setMinimumSize(new Dimension(50, 50));
		lbl_3.setMaximumSize(new Dimension(50, 50));
		
		lbl_5 = new JButton("");
		lbl_5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lbl_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lbl_5);
		lbl_5.setBackground(Color.WHITE);
		lbl_5.setIcon(im5);
		lbl_5.addActionListener(getIcon());
		
		lbl_5.setMargin(new Insets(2, 50, 2, 50));
		lbl_5.setLocation(new Point(10, 10));
		lbl_5.setMinimumSize(new Dimension(50, 50));
		lbl_5.setMaximumSize(new Dimension(50, 50));
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nEmoticonos > 5) {
					nEmoticonos -= 11;
					lbl_0.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_1.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_2.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_3.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_4.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_5.setIcon(BubbleText.getEmoji(nEmoticonos));
				}
				
			}
		});
		
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nEmoticonos < 23) {
					nEmoticonos += 1;
					lbl_0.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_1.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_2.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_3.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_4.setIcon(BubbleText.getEmoji(nEmoticonos));
					nEmoticonos += 1;
					lbl_5.setIcon(BubbleText.getEmoji(nEmoticonos));
				}
				
			}
		});

	}
	
	private ActionListener getIcon() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int nEmojiAux = 0;
				if(e.getSource() == lbl_0) {nEmojiAux = nEmoticonos - 5;}
				else if (e.getSource() == lbl_1) {nEmojiAux = nEmoticonos - 4;}
				else if (e.getSource() == lbl_2) {nEmojiAux = nEmoticonos - 3;}
				else if (e.getSource() == lbl_3) {nEmojiAux = nEmoticonos - 2;}
				else if (e.getSource() == lbl_4) {nEmojiAux = nEmoticonos - 1;}
				else if (e.getSource() == lbl_5) {nEmojiAux = nEmoticonos;}
				 if(vG == null) {
					 vCI.sendEmoji(nEmojiAux);
				 } else {
					 vG.sendEmoji(nEmojiAux);
				 }
			}
		};  
	}
}
