package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import controlador.ControladorAppChat;

@SuppressWarnings("serial")
public class PanelRegistroUsuario extends JPanel {

	private VentanaMain ventana;
	private JLabel rotulo;
	
	private JScrollPane sPane;
	private JPanel datosUsuario;
	
	private JLabel lNombre, lMovil, lEmail, lLogin, lpass, lpass2;
	private JTextField tNombre, tMovil, tEmail, tLogin;
	private JPasswordField tPass, tPass2;
	private JDateChooser cFecha;
	
	private JLabel lAlerta1, lAlerta2, lAlerta3;
	
	private JButton btnRegistrar, btnCancelar;
	
	public PanelRegistroUsuario(VentanaMain v) {
		ventana = v;
		crearPantalla();
	}
	
	private void crearPantalla() {
		setMaximumSize(new Dimension(630,480));
		setSize(630,480);
		setMinimumSize(new Dimension(630,480));
		setLayout(new BorderLayout());
		
		rotulo = new JLabel("Registrar Usuario", JLabel.CENTER);
		fixedSize(rotulo, 630, 60);
		rotulo.setFont(new Font("Arial",Font.BOLD,32));
		add(rotulo, BorderLayout.NORTH);
		
		datosUsuario = new JPanel();
		datosUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(datosUsuario, 630, 420);
		
		lNombre = new JLabel("Nombre:", JLabel.RIGHT);
		fixedSize(lNombre, 170, 24);
		tNombre = new JTextField();
		fixedSize(tNombre, 300, 24);
		
		lMovil = new JLabel("Numero de Telefono:", JLabel.RIGHT);
		fixedSize(lMovil, 170, 24);
		tMovil = new JTextField();
		fixedSize(tMovil, 300, 24);
		
		lEmail = new JLabel("Email:", JLabel.RIGHT);
		fixedSize(lEmail, 170, 24);
		tEmail = new JTextField();
		fixedSize(tEmail, 300, 24);
		
		cFecha = new JDateChooser();
		fixedSize(cFecha, 170, 24);
		
		
		lLogin = new JLabel("Login:", JLabel.RIGHT);
		fixedSize(lLogin, 170, 24);
		tLogin = new JTextField();
		fixedSize(tLogin, 300, 24);
		
		lpass = new JLabel("Contraseña:", JLabel.RIGHT);
		fixedSize(lpass, 170, 24);
		tPass= new JPasswordField();
		fixedSize(tPass, 300, 24);
		
		lpass2 = new JLabel("Repetir Contraseña:", JLabel.RIGHT);
		fixedSize(lpass2, 170, 24);
		tPass2= new JPasswordField();
		fixedSize(tPass2, 300, 24);
		
		btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 100, 30);
		
		btnCancelar = new JButton("Cancelar");
		fixedSize(btnCancelar, 100, 30);
		
		lAlerta1=new JLabel("Todos los Campos son obligtorios",JLabel.CENTER);
		lAlerta1.setForeground(Color.RED); fixedSize(lAlerta1,630,30);
		lAlerta1.setVisible(false);
		
		lAlerta2=new JLabel("El Usuario ya Existe",JLabel.CENTER);
		lAlerta2.setForeground(Color.RED); fixedSize(lAlerta2,630,30);
		lAlerta2.setVisible(false);
		
		lAlerta3=new JLabel("Las Contraseñas deben Coincidir",JLabel.CENTER);
		lAlerta3.setForeground(Color.RED); fixedSize(lAlerta3,630,30);
		lAlerta3.setVisible(false);
		
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 30)));
		datosUsuario.add(lNombre); datosUsuario.add(tNombre);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 15)));
		datosUsuario.add(lMovil); datosUsuario.add(tMovil);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 15)));
		datosUsuario.add(lEmail); datosUsuario.add(tEmail);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 15)));
		datosUsuario.add(Box.createRigidArea(new Dimension(200, 15)));
		datosUsuario.add(cFecha);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 15)));
		datosUsuario.add(lLogin); datosUsuario.add(tLogin);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 15)));
		datosUsuario.add(lpass); datosUsuario.add(tPass);
		datosUsuario.add(lpass2); datosUsuario.add(tPass2);
		datosUsuario.add(Box.createRigidArea(new Dimension(630, 30)));
		datosUsuario.add(Box.createRigidArea(new Dimension(170, 20)));
		datosUsuario.add(btnRegistrar);
		datosUsuario.add(Box.createRigidArea(new Dimension(90, 20)));
		datosUsuario.add(btnCancelar);
		datosUsuario.add(lAlerta1);datosUsuario.add(lAlerta2);
		datosUsuario.add(lAlerta3);
		
		sPane = new JScrollPane(datosUsuario, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fixedSize(sPane, 630, 420);
		add(sPane, BorderLayout.CENTER);
		
		btnRegistrar.addActionListener( e -> {
			registrar();
		});
		
		btnCancelar.addActionListener(e -> {
			cancelar();
		});
		
		
		
	}
	
	
	//MEtodo Para Registrar
	//Si es posible registrarlo
	//Se registra el Usuario y se avisa a la ventana para abrir
	//La pantalla Principal del Usuario
	private void registrar() {
		if(check()) {
			JOptionPane.showMessageDialog( this, "Usuario registrado correctamente.",
					"Registro",JOptionPane.INFORMATION_MESSAGE);
			
			ventana.usuarioCorrecto();
		}
	}
	
	
	//Metodo del Botón Cancelar
	//Se vuelve a la Ventana Login
	private void cancelar() {
		tNombre.setText("");
		tMovil.setText("");
		tEmail.setText("");
		cFecha.setCalendar(null);
		tLogin.setText("");
		tPass.setText("");
		tPass2.setText("");
		
		ventana.cambioLogin();
	}
	
	
	
	
	//Funcion para ver si se han completado todos los campos
	// el usuario ya exstía
	//Las contraseñas no son iguales
	private boolean check() {
		lAlerta1.setVisible(false);
		lAlerta2.setVisible(false);
		lAlerta2.setVisible(false);
		

		String nombre = tNombre.getText().trim();
		int movil = Integer.valueOf(tMovil.getText().trim());
		String email = tEmail.getText().trim();
		Date fecha = cFecha.getDate();
		String login = tLogin.getText().trim();
		String pass = new String(tPass.getPassword());
		String pass2 = new String(tPass2.getPassword());
		
		if(nombre.isEmpty() || movil == 0 || email.isEmpty()
				|| fecha == null || login.isEmpty() || pass.isEmpty()
				|| pass2.isEmpty()) {
			lAlerta1.setVisible(true);
			return false;
		}
		if(ControladorAppChat.getUnicaInstancia().existeUsuario(login)) {
			lAlerta2.setVisible(true);
			return false;
		}
		if(!pass.equals(pass2)) {
			lAlerta3.setVisible(true);
			return false;
		}
		ControladorAppChat.getUnicaInstancia()
			.crearUsuario(nombre, fecha, movil, login, pass2, email);
		return true;
		
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

}
