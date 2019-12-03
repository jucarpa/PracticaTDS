package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorAppChat;

public class PanelLogin extends JPanel {
	private Font fuenteGrande = new Font("Arial", Font.BOLD, 32);
	private JLabel rotulo;
	private JPanel datosCliente;
	private JLabel lUsuario, lcontrasena, lalerta, lalerta2, lalerta3;
	private JTextField usuario, contrasena;
	private JButton btnLogin, btnRegistrar, btnCancelar;
	private VentanaMain ventana;

	public PanelLogin(VentanaMain v) {
		crearPantalla();
		ventana = v;
	}

	private void crearPantalla() {
		setSize(Constantes.x_size, Constantes.y_size);
		setLayout(new BorderLayout());
		rotulo = new JLabel("Login Usuario", JLabel.CENTER);
		fixedSize(rotulo, Constantes.x_size, 60);
		rotulo.setFont(fuenteGrande);
		add(rotulo, BorderLayout.NORTH);

		datosCliente = new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));

		lUsuario = new JLabel("Usuario:", JLabel.RIGHT);
		fixedSize(lUsuario, 170, 24);
		usuario = new JTextField();
		fixedSize(usuario, 300, 24);

		lcontrasena = new JLabel("Contraseña:", JLabel.RIGHT);
		fixedSize(lcontrasena, 170, 24);
		contrasena = new JTextField();
		fixedSize(contrasena, 300, 24);
		btnLogin = new JButton("Login");
		fixedSize(btnLogin, 100, 30);
		btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 100, 30);
		btnCancelar = new JButton("Cancelar");
		fixedSize(btnCancelar, 100, 30);

		lalerta = new JLabel("Los campos Usuario y Contraseña son obligatorios", JLabel.CENTER);
		lalerta.setForeground(Color.RED);
		fixedSize(lalerta, Constantes.x_size, 30);
		lalerta.setVisible(false);

		lalerta2 = new JLabel("Usuario y Contraseña no coinciden. ", JLabel.CENTER);
		lalerta2.setForeground(Color.RED);
		fixedSize(lalerta2, Constantes.x_size, 30);
		lalerta2.setVisible(false);

		lalerta3 = new JLabel("Usuario no existe. ", JLabel.CENTER);
		lalerta3.setForeground(Color.RED);
		fixedSize(lalerta3, Constantes.x_size, 30);
		lalerta3.setVisible(false);

		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size, 75)));
		datosCliente.add(lUsuario);
		datosCliente.add(usuario);
		datosCliente.add(lcontrasena);
		datosCliente.add(contrasena);
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size, 75)));
		datosCliente.add(Box.createRigidArea(new Dimension(170, 20)));
		datosCliente.add(btnLogin);
		datosCliente.add(btnRegistrar);
		datosCliente.add(Box.createRigidArea(new Dimension(30, 20)));
		datosCliente.add(btnCancelar);
		datosCliente.add(lalerta);
		datosCliente.add(lalerta2);
		datosCliente.add(lalerta3);

		add(datosCliente, BorderLayout.CENTER);

		// Manejadores
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auxUsuario = usuario.getText().trim();
				String auxContrasena = contrasena.getText().trim();
				if (auxUsuario.isEmpty() || auxContrasena.isEmpty())
					lalerta.setVisible(true);
				else {
					lalerta.setVisible(false);
					if (!ControladorAppChat.getUnicaInstancia().existeUsuario(auxUsuario))
						lalerta3.setVisible(true);
					else {
						if (!ControladorAppChat.getUnicaInstancia().loginUsuario(auxUsuario, auxContrasena))
							lalerta2.setVisible(true);
						else {
							JOptionPane.showMessageDialog(ventana, "Usuario Logueado Correctamente", "Login Usuario",
									JOptionPane.PLAIN_MESSAGE);
							usuario.setText("");
							contrasena.setText("");
							lalerta.setVisible(false);

							ventana.cambioPanelPrincipal();
						}
					}
				}
			}
		});

		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelRegistro();
				// AQUI PASAMOS A LA VENTA DE REGISTRO
				// setContentPane(new PanelAltaCliente(ventana));
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usuario.setText("");
				contrasena.setText("");
				lalerta.setVisible(false);
			}
		});
	}

	private void fixedSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
}
