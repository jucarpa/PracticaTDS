package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class PanelCrearGrupo extends JPanel {
	private JTextField textField;
	private PanelVistaPrinciaplScene ventana;
	/**
	 * Create the panel.
	 */
	public PanelCrearGrupo(PanelVistaPrinciaplScene ventana) {
		this.ventana = ventana;
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(201, 13, 116, 22);
		add(textField);
		textField.setColumns(10);
		
		JPanel panelContactosAnadidos = new JPanel();
		panelContactosAnadidos.setBounds(319, 13, 186, 237);
		add(panelContactosAnadidos);
		panelContactosAnadidos.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panelContactosAnadidos.add(scrollPane_1, BorderLayout.CENTER);
		
		JLabel lblContactosAadidos = new JLabel("Contactos A\u00F1adidos");
		panelContactosAnadidos.add(lblContactosAadidos, BorderLayout.NORTH);
		lblContactosAadidos.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelContactos = new JPanel();
		panelContactos.setBounds(12, 13, 186, 274);
		add(panelContactos);
		panelContactos.setLayout(new BorderLayout(0, 0));
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setHorizontalAlignment(SwingConstants.CENTER);
		panelContactos.add(lblContactos, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		panelContactos.add(scrollPane, BorderLayout.CENTER);
		
		JButton button = new JButton("-->");
		button.setBounds(210, 98, 97, 25);
		add(button);
		
		JButton button_1 = new JButton("<--");
		button_1.setBounds(210, 163, 97, 25);
		add(button_1);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(225, 262, 77, 25);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(372, 262, 97, 25);
		add(btnCancelar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					System.out.println("Holi");
			}
		});
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelContacto();
			}
		});

	}
}
