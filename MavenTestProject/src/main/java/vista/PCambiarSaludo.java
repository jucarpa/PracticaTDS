package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class PCambiarSaludo extends JFrame {

	private JPanel contentPane;


	public PCambiarSaludo(PVistaPrincipal ventana) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 90);
		setTitle("Cambiar Saludo");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTextField textField = new JTextField();
		textField.setSize(new Dimension(300, 25));
		textField.setMinimumSize(new Dimension(300, 25));
		textField.setMaximumSize(new Dimension(300, 25));
		textField.setBounds(new Rectangle(0, 0, 301, 25));
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(303, 0, 97, 25);
		getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().trim().equals("")) {
					ventana.setSaludo(textField.getText().trim());
					dispose();
				}
				
			}
		});
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().trim().equals("")) {
					ventana.setSaludo(textField.getText().trim());
					dispose();
				}
				
			}
		});

	}

}
