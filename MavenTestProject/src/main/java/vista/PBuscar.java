package vista;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

public class PBuscar extends JPanel {

	/**
	 * Create the panel.
	 */
	public PBuscar(PVistaPrincipal ventana) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JTextField textField = new JTextField();
		textField.setSize(new Dimension(300, 25));
		textField.setMinimumSize(new Dimension(300, 25));
		textField.setMaximumSize(new Dimension(300, 25));
		textField.setBounds(new Rectangle(0, 0, 301, 25));
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(303, 0, 97, 25);
		add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().trim().equals("")) {
					ventana.busqueda(textField.getText().trim());
				}
				
			}
		});
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().trim().equals("")) {
					ventana.busqueda(textField.getText().trim());
				}
				
			}
		});

	}

}
