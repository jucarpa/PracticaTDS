package vista;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.ControladorAppChat;

import javax.swing.BoxLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelCambiarSaludo extends JFrame {

	private JPanel contentPane;


	public PanelCambiarSaludo() {
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
		
		String saludo = ControladorAppChat.getUnicaInstancia().getSaludo();
		textField.setText(saludo);
		
		btnNewButton.addActionListener( e -> {
				String nSaludo = textField.getText().trim(); 
				if(!nSaludo.equals("")) {
					ControladorAppChat.getUnicaInstancia().setSaludo(nSaludo);
					dispose();
				}
				
		});
	}

}
