package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controlador.ControladorAppChat;
import modelo.Usuario;

import javax.swing.JScrollPane;
import java.awt.Point;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class PRegistrarUsuario extends JPanel {
	static final int ANCHOW=400;
	static final int ALTOW=320;
	
	private VMain ventana;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblcalendar;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDNI;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	private JButton btnRegistrar;
	private JButton btnVolver;
	
	private JPanel jpanelAnterior;
	private JLabel lblNombreError;
	private JLabel lblApellidosError;
	private JLabel lblcalendarError;
	private JLabel lblEmailError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;
	private JCalendar calendar;
	private JScrollPane scrollPane;
	
	public PRegistrarUsuario(VMain frame){
		setPreferredSize(new Dimension(630, 480));
		setMinimumSize(new Dimension(630, 480));
		setMaximumSize(new Dimension(630, 480));
		ventana=frame;
		//jpanelAnterior = (JPanel) ventana.getContentPane();
		
		setLayout(new BorderLayout());
		
		
		
		JPanel datosPersonales = new JPanel ();
		datosPersonales.setPreferredSize(new Dimension(630, 480));
		datosPersonales.setMinimumSize(new Dimension(630, 480));
		datosPersonales.setMaximumSize(new Dimension(630, 480));
		//add(datosPersonales, BorderLayout.NORTH);
		datosPersonales.setLayout(new BoxLayout(datosPersonales,BoxLayout.Y_AXIS));
		
		JPanel linea_1=new JPanel();
		linea_1.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_2=new JPanel(); 
		linea_2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_3=new JPanel(); 
		linea_3.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_5=new JPanel(); 
		linea_5.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_6=new JPanel(); 
		linea_6.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_7=new JPanel(); 
		linea_7.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel linea_9=new JPanel(); 
		linea_9.setLayout(new FlowLayout(FlowLayout.CENTER));
		lblNombre = new JLabel("Nombre :",JLabel.RIGHT); 
		lblNombre.setAlignmentX(Component.RIGHT_ALIGNMENT);
		////fixedSize(lblNombre,75,20);
		txtNombre = new JTextField(); 
		txtNombre.setAlignmentX(Component.RIGHT_ALIGNMENT);
		fixedSize(txtNombre,150,20);
		lblNombreError=new JLabel("El nombre es obligatorio",JLabel.RIGHT); 
		lblNombreError.setAlignmentX(Component.CENTER_ALIGNMENT);
		////fixedSize(lblNombreError,224,15);
		lblNombreError.setForeground(Color.RED);
		linea_1.add(lblNombre); 
		linea_1.add(txtNombre);
		lblApellidos = new JLabel("Movil :",JLabel.RIGHT); 
		////fixedSize(lblApellidos,75,20);
		txtApellidos = new JTextField(); 
		fixedSize(txtApellidos,150,20);
		lblApellidosError=new JLabel("Los apellidos son obligatorios",JLabel.RIGHT); 
		lblApellidosError.setAlignmentX(Component.CENTER_ALIGNMENT);
		////fixedSize(lblApellidosError,255,15);
		lblApellidosError.setForeground(Color.RED);
		linea_2.add(lblApellidos); 
		linea_2.add(txtApellidos);
		////fixedSize(lblcalendar,75,20);
		lblcalendarError=new JLabel("La Fecha de Nacimiento es obligatoria",SwingConstants.CENTER);
		lblcalendarError.setAlignmentX(Component.CENTER_ALIGNMENT);
		////fixedSize(lblcalendarError,150,15);
		lblcalendarError.setForeground(Color.RED);
		
		calendar = new JCalendar();
		linea_3.add(calendar);
		lblEmail = new JLabel("Email :",JLabel.RIGHT); 
		////fixedSize(lblEmail,75,20);
		txtEmail = new JTextField(); 
		fixedSize(txtEmail,150,20);
		lblEmailError=new JLabel("El Email es obligatorio",JLabel.LEFT); 
		////fixedSize(lblEmailError,150,15);
		lblEmailError.setForeground(Color.RED);
		linea_5.add(lblEmail); 
		linea_5.add(txtEmail); 
		linea_5.add(lblEmailError);
		lblUsuario = new JLabel("Usuario :",JLabel.RIGHT); 
		////fixedSize(lblUsuario,75,20);
		txtUsuario = new JTextField(); 
		fixedSize(txtUsuario,150,20);
		lblUsuarioError=new JLabel("El usuario ya existe",JLabel.LEFT); 
		////fixedSize(lblUsuarioError,150,15);
		lblUsuarioError.setForeground(Color.RED);
		linea_6.add(lblUsuario); 
		linea_6.add(txtUsuario); 
		linea_6.add(lblUsuarioError);
		lblPassword = new JLabel("Password :",JLabel.RIGHT); 
		////fixedSize(lblPassword,75,20);
		txtPassword = new JPasswordField(); 
		fixedSize(txtPassword,150,20);
		lblPasswordChk = new JLabel("Otra vez:",JLabel.RIGHT); 
		////fixedSize(lblPasswordChk,60,20);
		txtPasswordChk = new JPasswordField(); 
		fixedSize(txtPasswordChk,150,20);
		lblPasswordError=new JLabel("Error al introducir las contraseñas",JLabel.CENTER); 
		lblPasswordError.setAlignmentX(Component.CENTER_ALIGNMENT);
		////fixedSize(lblPasswordError,ANCHOW,15);
		lblPasswordError.setForeground(Color.RED);
		linea_7.add(lblPassword); 
		linea_7.add(txtPassword); 
		linea_7.add(lblPasswordChk); 
		linea_7.add(txtPasswordChk);
		btnVolver= new JButton("Volver"); 
		////fixedSize(btnVolver,90,30);
		btnRegistrar= new JButton("Registrar"); 
		////fixedSize(btnRegistrar,90,30);
		linea_9.add(Box.createHorizontalStrut(75)); 
		linea_9.add(btnVolver);
		linea_9.add(Box.createHorizontalStrut(80)); 
		linea_9.add(btnRegistrar);
		
		datosPersonales.add(linea_1);
		datosPersonales.add(lblNombreError);
		datosPersonales.add(linea_2);
		datosPersonales.add(lblApellidosError);
		lblcalendar = new JLabel("Fecha de Nacimiento:",JLabel.RIGHT); 
		lblcalendar.setAlignmentX(Component.CENTER_ALIGNMENT);
		datosPersonales.add(lblcalendar);
		datosPersonales.add(linea_3);
		datosPersonales.add(lblcalendarError);
		datosPersonales.add(linea_5);
		datosPersonales.add(linea_6);
		datosPersonales.add(linea_7);
		datosPersonales.add(lblPasswordError);
		datosPersonales.add(linea_9);/*Nombre*//*Apellidos*//*DNI*//*Edad*//*Email*//*Usuario*//*Password y passwordchk*//*Movil*//*Botones*/
		
		/*linea 1*/
		
		/*linea 2*/
		
		/*linea 3*/
		
		/*linea 4*/
		
		/*linea 5*/
		
		/*linea 6*/
		
		/*linea 7*/
		
		/*linea 8*/
		
		/*linea 9*/
		
		scrollPane = new JScrollPane(datosPersonales, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(630, 480));
		scrollPane.setMinimumSize(new Dimension(630, 480));
		scrollPane.setMaximumSize(new Dimension(630, 480));
		add(scrollPane, BorderLayout.CENTER);
		
		ocultarErrores();

		ventana.setContentPane(this);
		ventana.revalidate(); /*redibujar con el nuevo JPanel*/
		
		/*Manejador bot�n volver*/
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(jpanelAnterior);
				ventana.setTitle("Login App Char");	
				ventana.revalidate();
			}
		});
		/*
		/*Manejador bot�n Registrar*/
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean OK=false;
				OK=checkFields();
				if (OK) {
						 Usuario registrado = ControladorAppChat.getUnicaInstancia().registrarUsuario(
										txtNombre.getText(),
										calendar.getDate(),
										Integer.parseInt(txtApellidos.getText()),
										txtUsuario.getText(),
										String.valueOf(txtPassword.getPassword()),
										txtEmail.getText());
						if (registrado != null) {
							JOptionPane.showMessageDialog(
										ventana,
										"Usuario registrado correctamente.",
										"Registro",
										JOptionPane.INFORMATION_MESSAGE);
							ventana.cambioPanelPrincipal(registrado.getMovil()); //movilUsuario
						} else JOptionPane.showMessageDialog(ventana,
								"No se ha podido llevar a cabo el registro.\n",
								"Registro",
								JOptionPane.ERROR_MESSAGE);
						ventana.setTitle("Login Gestor Eventos");	
				}
			} 
		});
		
	} /*constructor*/
	
	/**
	 * Comprueba que los campos de registro est�n bien
	 */
	private boolean checkFields() {
		boolean salida=true;
		/*borrar todos los errores en pantalla*/
		ocultarErrores();
		if (txtNombre.getText().trim().isEmpty()) {
			lblNombreError.setVisible(true); salida=false;
		}
		if (txtApellidos.getText().trim().isEmpty()) {
			lblApellidosError.setVisible(true); salida=false;
		}
		if (calendar.getDate().toString().trim().isEmpty()) {
			lblcalendarError.setVisible(true); salida=false;
		}
		if (txtEmail.getText().trim().isEmpty()) {
			lblEmailError.setVisible(true); salida=false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			lblUsuarioError.setText("El usuario es obligatorio");
			lblUsuarioError.setVisible(true); salida=false;
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPasswordChk.getPassword());
		if (password.equals("")) {
			lblPasswordError.setText("El password no puede estar vacio");
			lblPasswordError.setVisible(true); salida=false;
		} else if (!password.equals(password2)) {
			lblPasswordError.setText("Los dos passwords no coinciden");
			lblPasswordError.setVisible(true); salida=false;
		}
		// Comprobar que no exista otro usuario con igual login
		if (ControladorAppChat.getUnicaInstancia().existeUsuario(txtUsuario.getText())) {
			lblUsuarioError.setText("Ya existe ese usuario");
			lblUsuarioError.setVisible(true); salida=false;
		}
		return salida;
	}
	
	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		lblNombreError.setVisible(false);
		lblApellidosError.setVisible(false);
		lblcalendarError.setVisible(false);
		lblEmailError.setVisible(false);
		lblUsuarioError.setVisible(false);
		lblPasswordError.setVisible(false);
	}
	/**
	 * Fija el tama�o de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d= new Dimension(x,y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}
}
