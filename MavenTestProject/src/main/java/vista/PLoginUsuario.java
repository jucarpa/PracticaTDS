package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import controlador.ControladorAppChat;

public class PLoginUsuario extends JPanel {
	private Font fuenteGrande = new Font("Arial", Font.BOLD, 32);
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();

	public PLoginUsuario(VMain ventana) {
		setSize(Constantes.x_size, Constantes.y_size);
		setLayout(new BorderLayout());
		JLabel rotulo = new JLabel("Login Usuario", JLabel.CENTER);
		fixedSize(rotulo, Constantes.x_size, 60);
		rotulo.setFont(fuenteGrande);
		add(rotulo, BorderLayout.NORTH);

		JPanel datosCliente = new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel lUsuario = new JLabel("Usuario:", JLabel.RIGHT);
		fixedSize(lUsuario, 170, 24);
		JTextField usuario = new JTextField();
		fixedSize(usuario, 300, 24);

		JLabel lcontrasena = new JLabel("Contraseña:", JLabel.RIGHT);
		fixedSize(lcontrasena, 170, 24);
		JTextField contrasena = new JTextField();
		fixedSize(contrasena, 300, 24);
		JButton btnLogin = new JButton("Login");
		fixedSize(btnLogin, 100, 30);
		JButton btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 100, 30);
		JButton btnCancelar = new JButton("Cancelar");
		fixedSize(btnCancelar, 100, 30);

		JLabel lalerta = new JLabel("Los campos Usuario y Contraseña son obligatorios", JLabel.CENTER);
		lalerta.setForeground(Color.RED);
		fixedSize(lalerta, Constantes.x_size, 30);
		lalerta.setVisible(false);

		JLabel lalerta2 = new JLabel("Usuario y Contraseña no coinciden. ", JLabel.CENTER);
		lalerta2.setForeground(Color.RED);
		fixedSize(lalerta2, Constantes.x_size, 30);
		lalerta2.setVisible(false);

		JLabel lalerta3 = new JLabel("Usuario no existe. ", JLabel.CENTER);
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
				System.out.println("Falluco?");
				if (auxUsuario.isEmpty() || auxContrasena.isEmpty())
					lalerta.setVisible(true);
				else {
					lalerta.setVisible(false);
					if (!controlador.existeUsuario(auxUsuario)) {
						lalerta3.setVisible(true);
					}else {
						int movilUA = controlador.loginUsuario(auxUsuario, auxContrasena); 
						if (movilUA == -1)
							lalerta2.setVisible(true);
						else {
							usuario.setText("");
							contrasena.setText("");
							lalerta.setVisible(false);

							ventana.cambioPanelPrincipal(movilUA);
						}
					}
				}
			}
		});
		btnLogin.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String auxUsuario = usuario.getText().trim();
					String auxContrasena = contrasena.getText().trim();
					if (auxUsuario.isEmpty() || auxContrasena.isEmpty())
						lalerta.setVisible(true);
					else {
						lalerta.setVisible(false);
						if (!controlador.existeUsuario(auxUsuario))
							lalerta3.setVisible(true);
						else {
							int movilUA = controlador.loginUsuario(auxUsuario, auxContrasena); 
							if (movilUA == -1)
								lalerta2.setVisible(true);
							else {
								usuario.setText("");
								contrasena.setText("");
								lalerta.setVisible(false);

								ventana.cambioPanelPrincipal(movilUA);
							}
						}
					}

				}
				
			}
		});
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelRegistro();
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
