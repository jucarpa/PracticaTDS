package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controlador.ControladorAppChat;
import java.awt.Component;

//Panel del Login del Usuario
@SuppressWarnings("serial")
public class PanelLoginUsuario extends JPanel {

	private VentanaMain ventana;
	private JLabel rotulo;
	
	private JPanel datosUsuario;
	
	private JLabel lLogin, lPass;
	private JTextField tLogin;
	private JPasswordField tPass;
	
	private JButton btnAceptar, btnCancelar, btnRegistrar;
	
	private JLabel lAlerta1, lAlerta2, lAlerta3;
	private Component rigidArea;
	
	public PanelLoginUsuario(VentanaMain v) {
		ventana = v;
		crearPantalla();
	}
	
	private void crearPantalla() {
		setMaximumSize(new Dimension(630, 480));
		setMinimumSize(new Dimension(630,480));
		setSize(630,480);
		setLayout(new BorderLayout());
		
		rotulo = new JLabel("Iniciar Sesion", JLabel.CENTER);
		fixedSize(rotulo, 630, 60);
		rotulo.setFont(new Font("Arial",Font.BOLD,32));
		add(rotulo, BorderLayout.NORTH);
		
		datosUsuario = new JPanel();
		datosUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(datosUsuario,630,420);
		
		lLogin = new JLabel("Usuario:", JLabel.RIGHT);
		fixedSize(lLogin, 170, 24);
		tLogin = new JTextField();
		fixedSize(tLogin, 300, 24);
		
		lPass= new JLabel("Contraseña:", JLabel.RIGHT);
		fixedSize(lPass, 170, 24);
		tPass = new JPasswordField();
		fixedSize(tPass, 300, 24);
		
		btnAceptar = new JButton("Aceptar");
		fixedSize(btnAceptar, 100, 30);
		
		btnCancelar = new JButton("Cancelar");
		fixedSize(btnCancelar, 100, 30);
		
		btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 100, 30);
		
		lAlerta1 = new JLabel("Los campos Usuario y Contraseña son obligatorios.", JLabel.CENTER);
		lAlerta1.setForeground(Color.RED);
		fixedSize(lAlerta1, 630, 30);
		lAlerta1.setVisible(false);
		
		lAlerta2 = new JLabel("Usuario y Contraseña no coinciden.", JLabel.CENTER);
		lAlerta2.setForeground(Color.RED);
		fixedSize(lAlerta2, 630, 30);
		lAlerta2.setVisible(false);
		
		lAlerta3 = new JLabel("Usuario no existe.", JLabel.CENTER);
		lAlerta3.setForeground(Color.RED);
		fixedSize(lAlerta3, 630, 30);
		lAlerta3.setVisible(false);
		
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 75)));
		datosUsuario.add(lLogin); datosUsuario.add(tLogin);
		rigidArea = Box.createRigidArea(new Dimension(630, 15));
		datosUsuario.add(rigidArea);
		datosUsuario.add(lPass); datosUsuario.add(tPass);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 75)));
		datosUsuario.add(Box.createRigidArea(new Dimension(100, 20)));
		datosUsuario.add(btnAceptar);
		datosUsuario.add(btnRegistrar);
		datosUsuario.add(Box.createRigidArea(new Dimension(30, 20)));
		datosUsuario.add(btnCancelar);
		datosUsuario.add(lAlerta1);
		datosUsuario.add(lAlerta2);
		datosUsuario.add(lAlerta3);
		
		add(datosUsuario);
		
		btnAceptar.addActionListener(e -> {
			aceptar();
		});
		
		btnRegistrar.addActionListener(e -> {
			registrar();
		});
		
		btnCancelar.addActionListener(e -> {
			cancelar();
		});
	}
	
	private void aceptar() {
		if(check()) {
			
			JOptionPane.showMessageDialog( this, "Usuario loggeado correctamente.",
					"Login",JOptionPane.INFORMATION_MESSAGE);
			
			ventana.usuarioCorrecto();
		}
	}
	
	private boolean check() {
		lAlerta1.setVisible(false);
		lAlerta2.setVisible(false);
		lAlerta3.setVisible(false);
		
		String login = tLogin.getText().trim();
		String pass = new String(tPass.getPassword());
		
		if(login.isEmpty() || pass.isEmpty()) {
			lAlerta1.setVisible(true);
			return false;
		}
		
		if(!ControladorAppChat.getUnicaInstancia().existeUsuario(login)) {
			lAlerta3.setVisible(true);
			return false;
		}
		
		if(!ControladorAppChat.getUnicaInstancia().login(login, pass)) {
			lAlerta2.setVisible(true);
			return false;
		}
		return true;
	}
	
	private void registrar() {
		tLogin.setText("");
		tPass.setText("");
		
		ventana.cambioRegistro();
	}
	
	private void cancelar() {
		tLogin.setText("");
		tPass.setText("");
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
