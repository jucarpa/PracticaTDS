package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import controlador.ControladorAppChat;
import javafx.embed.swing.SwingFXUtils;
import modelo.Usuario;
import pruebas.prueba;

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
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	
	private PanelUltimosContactos panelUtlimosContactos;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public PanelVistaPrinciaplScene(VentanaMain ventana) {
		setBackground(Color.CYAN);
		initialize();
		this.ventana = ventana;
		panelUtlimosContactos = new PanelUltimosContactos();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 204, 102));
		menuBar.setForeground(Color.BLACK);
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		add(menuBar, BorderLayout.NORTH);
		
		mnIconoUsuario = new JMenu("");
		mnIconoUsuario.setBackground(Color.GREEN);
		menuBar.add(mnIconoUsuario);
		mntmNombreUsuario = new JMenuItem("Nombre Usuario");
		mntmNombreUsuario.setForeground(Color.BLACK);
		mntmNombreUsuario.setFont(new Font("Rockwell Nova Extra Bold", Font.BOLD, 14));
		mnIconoUsuario.add(mntmNombreUsuario);
		
		mntmSaludo = new JMenuItem("Saludo");
		mntmSaludo.setForeground(new Color(70, 130, 180));
		mntmSaludo.setFont(new Font("Rockwell", Font.ITALIC, 14));
		mnIconoUsuario.add(mntmSaludo);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 204, 102));
		panel_2.setMaximumSize(new Dimension(30, 10));
		menuBar.add(panel_2);
		
		mnEstado = new JMenu("");
		menuBar.add(mnEstado);
		
		panel_3 = new JPanel();
		panel_3.setMaximumSize(new Dimension(30, 10));
		panel_3.setBackground(new Color(51, 204, 102));
		menuBar.add(panel_3);
		
		mnOpciones = new JMenu("");
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
		
		
		panel_4 = new JPanel();
		menuBar.add(panel_4);
		panel_4.setMaximumSize(new Dimension(250, 10));
		panel_4.setBackground(new Color(51, 204, 102));
		
		mnInfoCuenta = new JMenu("Info Cuenta");
		mnInfoCuenta.setActionCommand("Info Cuenta");
		menuBar.add(mnInfoCuenta);
		
		panel_5 = new JPanel();
		panel_5.setMaximumSize(new Dimension(10, 10));
		panel_5.setBackground(new Color(51, 204, 102));
		menuBar.add(panel_5);
		
		mnBusqueda = new JMenu("");
		menuBar.add(mnBusqueda);
		
		panel_6 = new JPanel();
		panel_6.setMaximumSize(new Dimension(10, 10));
		panel_6.setBackground(new Color(51, 204, 102));
		menuBar.add(panel_6);
		
		mnEliminaciones = new JMenu("");
		mnEliminaciones.setActionCommand("Eliminaciones");
		menuBar.add(mnEliminaciones);
		
		mntmEliminarMensajes = new JMenuItem("Eliminar Mensajes");
		mnEliminaciones.add(mntmEliminarMensajes);
		
		mntmEliminarContacto = new JMenuItem("Eliminar Contacto");
		mnEliminaciones.add(mntmEliminarContacto);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(new Color(51, 204, 102));
		splitPane.setMaximumSize(new Dimension(243, 27));
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(splitPane, BorderLayout.CENTER);
		
		
		splitPane.setLeftComponent(panelUtlimosContactos.getPane());//
		
		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(243, 27));
		panel_1.setMaximumSize(new Dimension(243, 27));
		panel_1.setPreferredSize(new Dimension(243, 27));
		splitPane.setRightComponent(panel_1);
		
		ImageIcon im = new ImageIcon(prueba.class.getResource("/imagenes/ImagenEstado.png"));
		Image i = im.getImage();
		im = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnEstado.setIcon(im);
		
		ImageIcon iU = new ImageIcon(prueba.class.getResource("/imagenes/ImagenUsuarioDef.png"));
		i = iU.getImage();
		iU = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(iU);
		
		ImageIcon iC = new ImageIcon(prueba.class.getResource("/imagenes/ImagenConfiguracion.png"));
		i = iC.getImage();
		iC = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnOpciones.setIcon(iC);
		
		ImageIcon iB = new ImageIcon(prueba.class.getResource("/imagenes/ImagenBuscar.png"));
		i = iB.getImage();
		iB = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnBusqueda.setIcon(iB);
		
		ImageIcon iA = new ImageIcon(prueba.class.getResource("/imagenes/ImagenAjustes.png"));
		i = iA.getImage();
		iA = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnEliminaciones.setIcon(iA);
	}
	
	public void update() {
		usuario = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
		ImageIcon imagen = usuario.getImagen();
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(imagen);
		mnIconoUsuario.setText("");
		mnIconoUsuario.repaint();
		
		mntmNombreUsuario.setText(usuario.getNombre());
		mntmSaludo.setText("\"" + usuario.getSaludo() + "\""  );
		
		
		
		
	}

}
