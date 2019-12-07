package pruebas;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import beans.Entidad;
import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import persistencia.AdaptadorGrupo;
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prueba window = new prueba();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public prueba() {
		
		 /*ServicioPersistencia servPersistencia =
		  FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
		  for(Entidad e: servPersistencia.recuperarEntidades())
		  servPersistencia.borrarEntidad(e);
		 
		/*
		ControladorAppChat.getUnicaInstancia().loginUsuario("2", "2");
		System.out.println(ControladorAppChat.getUnicaInstancia().getUsuarioActual().getCIPorNumero(1).getNombre() + ", " + 
				ControladorAppChat.getUnicaInstancia().getUsuarioActual().getCIPorNumero(1).getMovil());
		
		ServicioPersistencia servPersistencia =
				  FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
		for(Entidad e: servPersistencia.recuperarEntidades()) {
			//if(e.getNombre().contentEquals("ContactoIndividual"))
				servPersistencia.borrarEntidad(e);
			
		}
		*/
		//List<Usuario> usuarios = AdaptadorUsuario.getUnicaInstancia().recuperarTodosUsuarios();
				
		ServicioPersistencia servPresistencia = FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
			for(Entidad e : servPresistencia.recuperarEntidades("Mensaje")) {/*
				if(servPresistencia.recuperarPropiedadEntidad(e, "nombre").equals("1")) {
					servPresistencia.eliminarPropiedadEntidad(e, "gruposAdmin");
					servPresistencia.anadirPropiedadEntidad(e, "gruposAdmin", "");
				}*/
				System.out.println(e.getId());
				int idReceptor = Integer.valueOf(servPresistencia.recuperarPropiedadEntidad(e, "receptor"));
				System.out.println(idReceptor);
				Entidad g = servPresistencia.recuperarEntidad(idReceptor);
				System.out.println(servPresistencia.recuperarPropiedadEntidad(g, "mensajes"));
				Grupo g = AdaptadorGrupo.getUnicaInstancia().recuperarGrupo(idReceptor);
				//servPresistencia.borrarEntidad(e);
			}
				/*ContactoIndividual aux = (ContactoIndividual) c;
				System.out.println(u.getNombre() + ", "+ aux.getId() + ", " + aux.getUsuario().getNombre());
			*/
		
		
		
		initialize();
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
