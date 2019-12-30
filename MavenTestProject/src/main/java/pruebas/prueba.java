package pruebas;

import java.awt.EventQueue;
import java.awt.Image;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import beans.Entidad;
import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.AdaptadorContactoIndividual;
import persistencia.AdaptadorGrupo;
import persistencia.AdaptadorMensaje;
import persistencia.AdaptadorUsuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;

public class prueba {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		prueba p = new prueba();
	}

	/**
	 * Create the application.
	 */
	public prueba() {
		
		ServicioPersistencia servPresistencia = FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
			for(Entidad e : servPresistencia.recuperarEntidades()) {
				servPresistencia.borrarEntidad(e);
			}
			
			Usuario u1 = new Usuario("1", Date.from(Instant.now()), 1,"1","1","1");
			Usuario u2 = new Usuario("2", Date.from(Instant.now()), 2,"2","2","2");
			AdaptadorUsuario.getUnicaInstancia().registrarUsuario(u1);
			AdaptadorUsuario.getUnicaInstancia().registrarUsuario(u2);
			
			ContactoIndividual ci = new ContactoIndividual("1", 1, u1);
			AdaptadorContactoIndividual.getUnicaInstancia().registrarContactoIndividual(ci);
			u2.addContacto(ci);
			AdaptadorUsuario.getUnicaInstancia().modificarUsuario(u2);
			
			ContactoIndividual ci2 = new ContactoIndividual("2", 2, u2);
			AdaptadorContactoIndividual.getUnicaInstancia().registrarContactoIndividual(ci2);
			u1.addContacto(ci2);
			AdaptadorUsuario.getUnicaInstancia().modificarUsuario(u1);
			
			Mensaje m1 = new Mensaje(".", LocalDateTime.now(), 0, u2, ci);
			Mensaje m2 = new Mensaje(".", LocalDateTime.now(), 0, u2, ci2);
			
			AdaptadorMensaje.getUnicaInstancia().registrarMensaje(m1);
			AdaptadorMensaje.getUnicaInstancia().registrarMensaje(m2);
			
			ci.addMensaje(m1);
			ci2.addMensaje(m2);
			
			AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(ci);
			AdaptadorContactoIndividual.getUnicaInstancia().modificarContactoIndividual(ci2);
			System.out.println("DONE");
				
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setMinimumSize(new Dimension(20, 20));
		lblNewLabel.setMaximumSize(new Dimension(20, 20));
		lblNewLabel.setSize(new Dimension(20, 20));
		frame.getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		ImageIcon imagen = new ImageIcon(prueba.class.getResource("/imagenes/ImagenUsuarioDef.png"));
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(128, 128, Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(imagen);
		
		JComboBox comboBox = new JComboBox();
		frame.getContentPane().add(comboBox, BorderLayout.SOUTH);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("New toggle button");
		frame.getContentPane().add(tglbtnNewToggleButton, BorderLayout.EAST);
		System.out.println(imagen.getIconHeight() + "," + imagen.getIconWidth()); // 256,256
		lblNewLabel.repaint();
	}

}
