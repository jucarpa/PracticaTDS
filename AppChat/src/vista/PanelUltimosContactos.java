package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Container;

import javax.swing.SwingConstants;

import javafx.scene.layout.Pane;

import java.awt.Font;
import java.awt.Color;

public class PanelUltimosContactos extends JPanel{

	private JFrame frame;
	public Container getPane() {
	return frame.getContentPane();	
	}
	public PanelUltimosContactos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 204, 102));
		frame.getContentPane().setPreferredSize(new Dimension(315, 200));
		frame.getContentPane().setMinimumSize(new Dimension(315, 200));
		frame.getContentPane().setMaximumSize(new Dimension(315, 200));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblUltimasConversaciones = new JLabel("Ultimas Conversaciones");
		lblUltimasConversaciones.setBackground(new Color(102, 204, 0));
		lblUltimasConversaciones.setFont(new Font("Nirmala UI", Font.BOLD, 15));
		lblUltimasConversaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblUltimasConversaciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblUltimasConversaciones, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(102, 204, 51));
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
