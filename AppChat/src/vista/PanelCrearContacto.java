package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import controlador.ControladorAppChat;

import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;

public class PanelCrearContacto extends JPanel {
	private JTextField textTelefono;
	private JTextField textNombre;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private PanelVistaPrinciaplScene ventana;
	/**
	 * Create the panel.
	 */
	public PanelCrearContacto(PanelVistaPrinciaplScene ventana) {
		this.ventana = ventana;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{45, 56, 0, 116, 56, 116, 0};
		gridBagLayout.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		textNombre = new JTextField();
		textNombre.setMaximumSize(new Dimension(170, 24));
		textNombre.setMinimumSize(new Dimension(170, 24));
		textNombre.setSize(new Dimension(170, 24));
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.anchor = GridBagConstraints.NORTHWEST;
		gbc_textNombre.gridx = 3;
		gbc_textNombre.gridy = 3;
		add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Movil:");
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textTelefono = new JTextField();
		textTelefono.setSize(new Dimension(170, 24));
		textTelefono.setMaximumSize(new Dimension(170, 24));
		textTelefono.setMinimumSize(new Dimension(170, 24));
		GridBagConstraints gbc_textTelefono = new GridBagConstraints();
		gbc_textTelefono.anchor = GridBagConstraints.NORTHWEST;
		gbc_textTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textTelefono.gridx = 3;
		gbc_textTelefono.gridy = 5;
		add(textTelefono, gbc_textTelefono);
		textTelefono.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 7;
		add(btnAceptar, gbc_btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 7;
		add(btnCancelar, gbc_btnCancelar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!ControladorAppChat.getUnicaInstancia().existeContacto(Integer.valueOf(textTelefono.getText()))) {
					ControladorAppChat.getUnicaInstancia().registrarContactoIndividual(textNombre.getText(), Integer.valueOf(textTelefono.getText()));
					System.out.println("OK");
				}else System.out.println("Ya era Contacto");
				
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelContacto();
			}
		});
	}

}
