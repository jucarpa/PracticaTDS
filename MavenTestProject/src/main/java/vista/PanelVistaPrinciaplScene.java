package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import com.itextpdf.text.DocumentException;

import controlador.ControladorAppChat;
import controlador.ManejadorPDF;
import javafx.embed.swing.SwingFXUtils;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
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
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Color;

public class PanelVistaPrinciaplScene extends JPanel {
	private VentanaMain ventana;
	private int movilUA;
	private Contacto contactoActual;
	ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();

	private JMenuBar menuBar;
	private JMenu mnIconoUsuario, mnEstado, mnOpciones, mnInfoCuenta, mnBusqueda, mnEliminaciones;
	private JMenuItem mntmImagen, mntmNombreUsuario, mntmSaludo, mntmEstado, mntmCrearContacto, mntmCrearGrupo,
			mntmModificarGrupo, mntmCambiarImagen, mntmCambiarSaludo, mntmMostrarContactos, mntmConvertirseEnPremium,
			mntmEliminarMensajes, mntmEliminarContacto, mntmEstadisticas, mntmGenerarPDF, mntmInfoMovilusuario;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;

	private PanelUltimosContactos2 panelUtlimosContactos;
	private JPanel panel;
	private JSplitPane splitPane;
	public PanelVistaPrinciaplScene panelPrincipal = this;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public PanelVistaPrinciaplScene(VentanaMain ventana, int movilUA) {
		setBackground(Color.CYAN);
		this.ventana = ventana;
		this.movilUA = movilUA;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setSize(Constantes.x_size, Constantes.y_size);
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

		mntmCambiarImagen = new JMenuItem("Cambiar Imagen");
		mnIconoUsuario.add(mntmCambiarImagen);
		mntmCambiarSaludo = new JMenuItem("Cambiar Saludo");
		mnIconoUsuario.add(mntmCambiarSaludo);

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


		mntmMostrarContactos = new JMenuItem("Mostrar Contactos");
		mnOpciones.add(mntmMostrarContactos);

		mntmConvertirseEnPremium = new JMenuItem("Convertirse En Premium");
		mnOpciones.add(mntmConvertirseEnPremium);
		
		mntmEstadisticas = new JMenuItem("Estadisticas");
		mntmEstadisticas.setForeground(new Color(255, 215, 0));
		mntmEstadisticas.setFont(new Font("Rockwell", Font.ITALIC, 14));
		mnOpciones.add(mntmEstadisticas);
		
		mntmGenerarPDF = new JMenuItem("Generar PDF");
		mntmGenerarPDF.setForeground(new Color(255, 215, 0));
		mntmGenerarPDF.setFont(new Font("Rockwell", Font.ITALIC, 14));
		mnOpciones.add(mntmGenerarPDF); 

		panel_4 = new JPanel();
		menuBar.add(panel_4);
		panel_4.setMaximumSize(new Dimension(250, 10));
		panel_4.setBackground(new Color(51, 204, 102));

		mnInfoCuenta = new JMenu("Info Cuenta");
		mnInfoCuenta.setActionCommand("Info Cuenta");
		menuBar.add(mnInfoCuenta);
		mntmInfoMovilusuario = new JMenuItem();
		mnInfoCuenta.add(mntmInfoMovilusuario);
		mntmInfoMovilusuario.setVisible(false);
		
		
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
		
		mntmModificarGrupo = new JMenuItem("Modificar");
		mnEliminaciones.add(mntmModificarGrupo);

		splitPane = new JSplitPane();
		splitPane.setBackground(new Color(51, 204, 102));
		splitPane.setMaximumSize(new Dimension(243, 27));
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelUtlimosContactos = new PanelUltimosContactos2(this, movilUA);
		panelUtlimosContactos.setLayout(new BoxLayout(panelUtlimosContactos, BoxLayout.X_AXIS));
		splitPane.setLeftComponent(panelUtlimosContactos);
		add(splitPane, BorderLayout.CENTER);
		JPanel pAux = new JPanel();
		splitPane.setRightComponent(pAux);

		Usuario usuario = controlador.getUsuario(movilUA);
		ImageIcon imagen = usuario.getImagen();
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(imagen);
		mnIconoUsuario.setText("");
		mnIconoUsuario.repaint();
		mntmNombreUsuario.setText(usuario.getNombre());
		mntmSaludo.setText("\"" + usuario.getSaludo() + "\"");
/*
		ImageIcon iU = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenUsuarioDef.png"));
		Image i = iU.getImage();
		iU = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(iU);*/

		ImageIcon iC = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenConfiguracion.png"));
		Image i = iC.getImage();
		iC = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnOpciones.setIcon(iC);

		ImageIcon iB = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenBuscar.png"));
		i = iB.getImage();
		iB = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnBusqueda.setIcon(iB);

		ImageIcon iA = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenAjustes.png"));
		i = iA.getImage();
		iA = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnEliminaciones.setIcon(iA);
		
		mntmCrearContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				splitPane.setRightComponent(new PanelCrearContacto(panelPrincipal, movilUA));
			}
		});
		mntmCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelCrearGrupo g = new PanelCrearGrupo(panelPrincipal, movilUA);
				splitPane.setRightComponent(g);
				g.update();
				
			}
		});
		
		mntmModificarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(contactoActual != null) {
					if(contactoActual.getClass() == ContactoIndividual.class) {
						ContactoIndividual ci = (ContactoIndividual) contactoActual;
						splitPane.setRightComponent(new PanelModificarContacto(panelPrincipal, ci.getMovil(), movilUA));	
					}else {
						Grupo aux = (Grupo) contactoActual;
						if(aux.getAdmin().getMovil() == movilUA)
						splitPane.setRightComponent(new PanelModificarGrupo(panelPrincipal,aux.getNombre(), movilUA));
						else JOptionPane.showMessageDialog(ventana, "No eres el administrador del Grupo", "Modificar Grupo",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		mntmEliminarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(contactoActual != null) {
					if(contactoActual.getClass() == ContactoIndividual.class) {
						controlador.eliminarContactoIndividual((ContactoIndividual)contactoActual, movilUA);
						contactoActual = null;
						JOptionPane.showMessageDialog(ventana, "Contacto Eliminado!", "Eliminar Contacto",
								JOptionPane.PLAIN_MESSAGE);
					}
					else {
						Grupo aux = (Grupo) contactoActual;
						if(aux.getAdmin().getMovil() == movilUA) {
						controlador.eliminarGrupo(aux, movilUA);
						contactoActual = null;
						JOptionPane.showMessageDialog(ventana, "Grupo Eliminado!", "Eliminar Grupo",
								JOptionPane.PLAIN_MESSAGE);
						}
						else JOptionPane.showMessageDialog(ventana, "No eres el administrador del Grupo", "Modificar Grupo",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		
		mntmEliminarMensajes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.eliminarMensajes(contactoActual);
				JOptionPane.showMessageDialog(ventana, "Mensajes Eliminados !", "Eliminar Mensaje",
						JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		
		mntmConvertirseEnPremium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(controlador.realizarPago(movilUA) == true) {
					mntmConvertirseEnPremium.setVisible(false);
					mntmEstadisticas.setVisible(true);
					mntmGenerarPDF.setVisible(true);
				}
			}
		});
		System.out.println(usuario.isPremium());
		if(usuario.isPremium() == true) {
			mntmConvertirseEnPremium.setVisible(false);
			mntmEstadisticas.setVisible(true);
			mntmGenerarPDF.setVisible(true);
		} else {
			mntmConvertirseEnPremium.setVisible(true);
			mntmEstadisticas.setVisible(false);
			mntmGenerarPDF.setVisible(false);
		}
		
		mntmEstadisticas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ESTADISTICAS!!");
				
			}
		});
		
		mntmGenerarPDF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ManejadorPDF.getUnicaInstancia().printContactos(movilUA);
				} catch (DocumentException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(ventana, "PDF Con Contctos Creado!", "Generar PDF",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		mntmCambiarImagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FramecambiarImagen aux = new FramecambiarImagen(panelPrincipal);
				aux.setVisible(true);
				
			}
		});
	}

	
	public void setContactoSeleccionado(Contacto c, int i) {
		contactoActual = c;
		cambioPanelContacto(i);
		if(ContactoIndividual.class == c.getClass()) {
			ContactoIndividual aux = (ContactoIndividual) c;
			ImageIcon iC = aux.getUsuario().getImagen();
			Image im = iC.getImage();
			iC = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			mnInfoCuenta.setIcon(iC);
			mnInfoCuenta.setText(aux.getNombre());
			mntmInfoMovilusuario.setText(aux.getMovil() + "");
			mntmInfoMovilusuario.setVisible(true);
		}
		
		
	}
	public void cambioPanelContacto(int i ){
		if(i == 1) {
		PanelChatCI ux = new PanelChatCI((ContactoIndividual) contactoActual, movilUA);
		splitPane.setRightComponent(ux);
		} else {
			PanelChatGrupo ux = new PanelChatGrupo((Grupo) contactoActual, movilUA);
			splitPane.setRightComponent(ux);
		}
	}
	
	public int getMovilUA() {
		return movilUA;
	}
	
	public void addPanelContacto(Contacto c, int i) {
		panelUtlimosContactos.addPanelContacto(c, i);
	}
	
	public void modificarContacto(Contacto c, int i) {
		panelUtlimosContactos.modificarContacto(c,i);
	}
}
