package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import controlador.ControladorAppChat;
import javafx.embed.swing.SwingFXUtils;
import modelo.Usuario;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JSplitPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Color;

public class PanelVistaPrinciaplScene extends JPanel{
	private VentanaMain ventana;
	private Usuario usuario;
	
	private JMenuBar menuBar;
	private JMenu mnIconoUsuario, mnEstado, mnOpciones, mnInfoCuenta, mnBusqueda, mnEliminaciones;
	private JMenuItem mntmImagen, mntmNombreUsuario, mntmSaludo, mntmEstado,
		mntmCrearContacto, mntmCrearGrupo, mntmModificarGrupo,
		mntmMostrarContactos, mntmConvertirseEnPremium, mntmEliminarMensajes, mntmEliminarContacto;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public PanelVistaPrinciaplScene(VentanaMain ventana) {
		initialize();
		this.ventana = ventana;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		add(menuBar, BorderLayout.NORTH);
		
		mnIconoUsuario = new JMenu("Icono Usuario");
		menuBar.add(mnIconoUsuario);
		/*
		mntmImagen = new JMenuItem("Imagen");
		mnIconoUsuario.add(mntmImagen);
		*/
		mntmNombreUsuario = new JMenuItem("Nombre Usuario");
		mntmNombreUsuario.setForeground(Color.BLACK);
		mntmNombreUsuario.setFont(new Font("Rockwell Nova Extra Bold", Font.BOLD, 14));
		mnIconoUsuario.add(mntmNombreUsuario);
		
		mntmSaludo = new JMenuItem("Saludo");
		mntmSaludo.setForeground(new Color(70, 130, 180));
		mntmSaludo.setFont(new Font("Rockwell", Font.ITALIC, 14));
		mnIconoUsuario.add(mntmSaludo);
		
		mnEstado = new JMenu("Estado\r\n");
		menuBar.add(mnEstado);
		
		mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		mntmCrearContacto = new JMenuItem("Crear Contacto");
		mnOpciones.add(mntmCrearContacto);
		
		mntmCrearGrupo = new JMenuItem("Crear Grupo");
		mnOpciones.add(mntmCrearGrupo);
		
		mntmModificarGrupo = new JMenuItem("Modificar Grupo");
		mnOpciones.add(mntmModificarGrupo);
		
		mntmMostrarContactos = new JMenuItem("Mostrar Contactos");
		mnOpciones.add(mntmMostrarContactos);
		
		mntmConvertirseEnPremium = new JMenuItem("Convertirse En Premium");
		mnOpciones.add(mntmConvertirseEnPremium);
		
		mnInfoCuenta = new JMenu("Info Cuenta");
		mnInfoCuenta.setActionCommand("Info Cuenta");
		menuBar.add(mnInfoCuenta);
		
		mnBusqueda = new JMenu("Busqueda");
		menuBar.add(mnBusqueda);
		
		mnEliminaciones = new JMenu("Eliminaciones");
		mnEliminaciones.setActionCommand("Eliminaciones");
		menuBar.add(mnEliminaciones);
		
		mntmEliminarMensajes = new JMenuItem("Eliminar Mensajes");
		mnEliminaciones.add(mntmEliminarMensajes);
		
		mntmEliminarContacto = new JMenuItem("Eliminar Contacto");
		mnEliminaciones.add(mntmEliminarContacto);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setMaximumSize(new Dimension(243, 27));
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 127));
		splitPane.setLeftComponent(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(250, 27));
		splitPane.setRightComponent(panel_1);
		
	}
	
	public void update() {
		usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
		ImageIcon imagen = usuario.getImagen();
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(imagen);
		mnIconoUsuario.setText("");
		mnIconoUsuario.repaint();
		
		mntmNombreUsuario.setText(usuario.getNombre());
		mntmSaludo.setText("\"" + usuario.getSaludo() + "\""  );
		
		ImageIcon imEstado= new ImageIcon(PanelVistaPrinciaplScene.class.getResource("/imagenes/ImagenEstado"));
		Image iEstado = imEstado.getImage();
		mnEstado.setIcon(imEstado);
		
		
		
	}

}
