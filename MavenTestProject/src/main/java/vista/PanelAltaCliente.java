package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorAppChat;

@SuppressWarnings("serial")
public class PanelAltaCliente extends JPanel {
	private Font fuenteGrande = new Font("Arial", Font.BOLD, 32);
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();

	public PanelAltaCliente(VentanaMain ventana) {
		setSize(Constantes.x_size, Constantes.y_size);
		setLayout(new BorderLayout());
		JLabel rotulo = new JLabel("Alta Usuario", JLabel.CENTER);
		fixedSize(rotulo, Constantes.x_size, 60);
		rotulo.setFont(fuenteGrande);
		add(rotulo, BorderLayout.NORTH);

		JPanel datosCliente = new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel lNombre = new JLabel("Nombre:", JLabel.RIGHT);
		fixedSize(lNombre, 170, 24);
		JTextField nombre = new JTextField();
		fixedSize(nombre, 300, 24);

		JLabel lfechaNacimiento = new JLabel("FechaNacimento:", JLabel.RIGHT);
		fixedSize(lfechaNacimiento, 170, 24);
		JTextField fechaNacimiento = new JTextField();
		fixedSize(fechaNacimiento, 300, 24);

		JLabel lmovil = new JLabel("Movil:", JLabel.RIGHT);
		fixedSize(lmovil, 170, 24);
		JTextField movil = new JTextField();
		fixedSize(movil, 300, 24);

		JLabel lEmail = new JLabel("Email:", JLabel.RIGHT);
		fixedSize(lEmail, 170, 24);
		JTextField email = new JTextField();
		fixedSize(email, 300, 24);

		JLabel lUsuario = new JLabel("Usuario:", JLabel.RIGHT);
		fixedSize(lUsuario, 170, 24);
		JTextField usuario = new JTextField();
		fixedSize(usuario, 300, 24);

		JLabel lcontrasena = new JLabel("Contrase�a:", JLabel.RIGHT);
		fixedSize(lcontrasena, 170, 24);
		JTextField contrasena = new JTextField();
		fixedSize(contrasena, 300, 24);

		JButton btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 100, 30);
		JButton btnCancelar = new JButton("Cancelar");
		fixedSize(btnCancelar, 100, 30);

		JLabel lalerta = new JLabel("Todos los campos son obligatorios", JLabel.CENTER);
		lalerta.setForeground(Color.RED);
		fixedSize(lalerta, Constantes.x_size, 30);
		lalerta.setVisible(false);
		
		datosCliente.add(lNombre);
		datosCliente.add(nombre);
		datosCliente.add(lfechaNacimiento);
		datosCliente.add(fechaNacimiento);
		datosCliente.add(lmovil);
		datosCliente.add(movil);
		datosCliente.add(lEmail);
		datosCliente.add(email);
		datosCliente.add(lUsuario);
		datosCliente.add(usuario);
		datosCliente.add(lcontrasena);
		datosCliente.add(contrasena);
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size, 75)));
		datosCliente.add(Box.createRigidArea(new Dimension(170, 20)));
		datosCliente.add(btnRegistrar);
		datosCliente.add(Box.createRigidArea(new Dimension(90, 20)));
		datosCliente.add(btnCancelar);
		datosCliente.add(lalerta);

		add(datosCliente, BorderLayout.CENTER);

		// Manejadores
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auxNombre = nombre.getText().trim();
				String auxFechaNacimineto = fechaNacimiento.getText().trim();
				String auxMovil = movil.getText().trim();
				String auxLogin = usuario.getText().trim();
				String auxContrasena = contrasena.getText().trim();
				String auxEmail = email.getText().trim();

				if (auxNombre.isEmpty() || auxFechaNacimineto.isEmpty() || auxMovil.isEmpty() || auxLogin.isEmpty()
						|| auxContrasena.isEmpty() || auxEmail.isEmpty())
					lalerta.setVisible(true);
				else {
					lalerta.setVisible(false);
					int movilUA = controlador.registrarUsuario(auxNombre,
							LocalDate.parse(auxFechaNacimineto), Integer.parseInt(auxMovil), auxLogin, auxContrasena,
							auxEmail);
					JOptionPane.showMessageDialog(ventana, "Usuario dado de alta correctamente", "Registrar Usuario",
							JOptionPane.PLAIN_MESSAGE);
					usuario.setText("");
					contrasena.setText("");
					lalerta.setVisible(false);
					ventana.cambioPanelPrincipal(movilUA);
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelLogin();
			}
		});
	}

	private void fixedSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
}
