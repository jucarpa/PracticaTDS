package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import com.itextpdf.text.DocumentException;

import componentes.Luz;
import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;
import javafx.embed.swing.SwingFXUtils;
import manejadores.ManejadorPDF;
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
import javax.swing.Box;

public class PVistaPrincipal extends JPanel {
	private VMain ventana;
	private int movilUA;
	private Contacto contactoActual;

	private JMenuBar menuBar;
	private JMenu mnIconoUsuario, mnEstado, mnOpciones, mnInfoCuenta, mnBusqueda, mnEliminaciones;
	private JMenuItem mntmImagen, mntmNombreUsuario, mntmSaludo, mntmEstado, mntmCrearContacto, mntmCrearGrupo,
			mntmModificarGrupo, mntmCambiarImagen, mntmCambiarSaludo, mntmMostrarContactos, mntmConvertirseEnPremium,
			mntmEliminarMensajes, mntmEliminarContacto, mntmEstadisticas, mntmGenerarPDF, mntmInfoMovilusuario;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_6;

	private PListaContactos panelUtlimosContactos;
	private JPanel panel;
	private JSplitPane splitPane;
	public PVistaPrincipal panelPrincipal = this;
	private JPanel panelActual;
	private Luz luz;
	private Component horizontalStrut;
	private JPanel panel_1;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public PVistaPrincipal(VMain ventana, int movilUA) {
		setBackground(Color.CYAN);
		this.ventana = ventana;
		this.movilUA = movilUA;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ActualizarBBDD.getUnicaInstancia().addPanelPVP(this);
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
		
		horizontalStrut = Box.createHorizontalStrut(300);
		menuBar.add(horizontalStrut);

		mnInfoCuenta = new JMenu("Info Cuenta");
		mnInfoCuenta.setActionCommand("Info Cuenta");
		menuBar.add(mnInfoCuenta);
		mntmInfoMovilusuario = new JMenuItem();
		mnInfoCuenta.add(mntmInfoMovilusuario);
		mntmInfoMovilusuario.setVisible(false);

		mnBusqueda = new JMenu("");
		menuBar.add(mnBusqueda);
		mnBusqueda.add(new PBuscar(this));

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
		
		luz = new Luz(movilUA);
		menuBar.add(luz);
		
		splitPane = new JSplitPane();
		splitPane.setBackground(new Color(51, 204, 102));
		splitPane.setMaximumSize(new Dimension(243, 27));
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelUtlimosContactos = new PListaContactos(movilUA, this);
		panelUtlimosContactos.setLayout(new BoxLayout(panelUtlimosContactos, BoxLayout.X_AXIS));
		splitPane.setLeftComponent(panelUtlimosContactos);
		add(splitPane, BorderLayout.CENTER);
		JPanel pAux = new JPanel();
		splitPane.setRightComponent(pAux);

		Usuario usuario = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA);
		ImageIcon imagen = usuario.getImagen();
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(imagen);
		mnIconoUsuario.setText("");
		mnIconoUsuario.repaint();
		mntmNombreUsuario.setText(usuario.getNombre());
		mntmSaludo.setText("\"" + usuario.getSaludo() + "\"");

		ImageIcon iC = new ImageIcon(VMain.class.getResource("/imagenes/ImagenConfiguracion.png"));
		Image i = iC.getImage();
		iC = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnOpciones.setIcon(iC);

		ImageIcon iB = new ImageIcon(VMain.class.getResource("/imagenes/ImagenBuscar.png"));
		i = iB.getImage();
		iB = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnBusqueda.setIcon(iB);

		ImageIcon iA = new ImageIcon(VMain.class.getResource("/imagenes/ImagenAjustes.png"));
		i = iA.getImage();
		iA = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnEliminaciones.setIcon(iA);
		
		mnInfoCuenta.setEnabled(false);
		mnBusqueda.setEnabled(false);
		mnEliminaciones.setEnabled(false);
		luz.setEnabled(false);
		
		panel_1 = new JPanel();
		panel_1.setMaximumSize(new Dimension(10, 10));
		panel_1.setBackground(new Color(51, 204, 102));
		menuBar.add(panel_1);
		
		
		
		
		
		mntmCrearContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				splitPane.setRightComponent(new PCrearContacto(panelPrincipal, movilUA));

				mnInfoCuenta.setEnabled(false);
				mnBusqueda.setEnabled(false);
				mnEliminaciones.setEnabled(false);
				luz.setEnabled(false);
			}
		});
		mntmCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PCrearGrupo g = new PCrearGrupo(panelPrincipal, movilUA);
				splitPane.setRightComponent(g);
				g.update();

				mnInfoCuenta.setEnabled(false);
				mnBusqueda.setEnabled(false);
				mnEliminaciones.setEnabled(false);
				luz.setEnabled(false);
			}
		});
		
		mntmModificarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mnInfoCuenta.setEnabled(false);
				mnBusqueda.setEnabled(false);
				mnEliminaciones.setEnabled(false);
				luz.setEnabled(false);
				
				if(contactoActual != null) {
					if(contactoActual.getClass() == ContactoIndividual.class) {
						ContactoIndividual ci = (ContactoIndividual) contactoActual;
						splitPane.setRightComponent(new PModificarContacto(panelPrincipal, ci.getMovil(), movilUA));	
					}else {
						Grupo aux = (Grupo) contactoActual;
						if(aux.getAdmin().getMovil() == movilUA)
						splitPane.setRightComponent(new PModificarGrupo(panelPrincipal,aux.getNombre(), movilUA));
						else JOptionPane.showMessageDialog(ventana, "No eres el administrador del Grupo", "Modificar Grupo",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		mntmEliminarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if(contactoActual.getClass() == ContactoIndividual.class) {
						panelUtlimosContactos.removeCI((ContactoIndividual)contactoActual);
						PChatCI aux = (PChatCI) panelActual;
						aux.removeUpdate();
						aux.setVisible(false);
						ControladorAppChat.getUnicaInstancia().eliminarContactoIndividual((ContactoIndividual)contactoActual, movilUA);
						contactoActual = null;
					
						mnInfoCuenta.setEnabled(false);
						mnBusqueda.setEnabled(false);
						mnEliminaciones.setEnabled(false);
						luz.setEnabled(false);
						
						JOptionPane.showMessageDialog(ventana, "Contacto Eliminado!", "Eliminar Contacto",
								JOptionPane.PLAIN_MESSAGE);
					}
					else {
						Grupo aux = (Grupo) contactoActual;
						if(aux.getAdmin().getMovil() == movilUA) {
							
						panelUtlimosContactos.removeG((Grupo)contactoActual);
						PChatG pG = (PChatG) panelActual;
						pG.removeUpdate();
						pG.setVisible(false);
						JOptionPane.showMessageDialog(ventana, "Grupo Eliminado!", "Eliminar Grupo",
								JOptionPane.PLAIN_MESSAGE);
						
						mnInfoCuenta.setEnabled(false);
						mnBusqueda.setEnabled(false);
						mnEliminaciones.setEnabled(false);
						luz.setEnabled(false);
						ControladorAppChat.getUnicaInstancia().eliminarGrupo((Grupo) contactoActual, movilUA);
						contactoActual = null;
						}
						else JOptionPane.showMessageDialog(ventana, "No eres el administrador del Grupo", "Modificar Grupo",
								JOptionPane.PLAIN_MESSAGE);
					}
			}
		});
		
		mntmEliminarMensajes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ControladorAppChat.getUnicaInstancia().eliminarMensajes(contactoActual);
				JOptionPane.showMessageDialog(ventana, "Mensajes Eliminados !", "Eliminar Mensaje",
						JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		
		mntmConvertirseEnPremium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ControladorAppChat.getUnicaInstancia().realizarPago(movilUA) == true) {
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
				
				JOptionPane.showMessageDialog(ventana, "PDF Con Contactos Creado!", "Generar PDF",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		mntmCambiarImagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PCambiarImagen aux = new PCambiarImagen(panelPrincipal);
				aux.setVisible(true);
				
			}
		});
		
		mntmCambiarSaludo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PCambiarSaludo aux = new PCambiarSaludo(panelPrincipal);
				aux.setVisible(true);
				
			}
		});
		
		mntmMostrarContactos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PMostrarContactos pMC = new PMostrarContactos(panelPrincipal, movilUA);
				splitPane.setLeftComponent(pMC);
				
			}
		});
	}

	
	public void setContactoSeleccionado(Contacto c, int i) {
		System.out.println(c);
		contactoActual = c;
		if(ContactoIndividual.class == c.getClass()) {
			ContactoIndividual aux = (ContactoIndividual) c;
			ImageIcon iC = aux.getUsuario().getImagen();
			Image im = iC.getImage();
			iC = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			mnInfoCuenta.setIcon(iC);
			mnInfoCuenta.setText(aux.getNombre());
			mntmInfoMovilusuario.setText(aux.getMovil() + "");
			mntmInfoMovilusuario.setVisible(true);
			
			luz.setContacto(aux.getMovil());
		} else {
			mnInfoCuenta.setIcon(null);
			mnInfoCuenta.setText(c.getNombre());
			panelActual = new PChatG((Grupo)c, movilUA);
			splitPane.setRightComponent(panelActual);
		}
		cambioPanelContacto(i);
		mnInfoCuenta.setEnabled(true);
		mnBusqueda.setEnabled(true);
		mnEliminaciones.setEnabled(true);
		luz.setEnabled(true);
		
		
	}
	public void cambioPanelContacto(int i ){
		
		if(i == 1) {
			PChatCI pAc = null;
			ContactoIndividual cIa = (ContactoIndividual) contactoActual;
			if(panelActual == null || panelActual.getClass() != PChatCI.class) {
				PChatCI ux = new PChatCI(cIa, movilUA);
				splitPane.setRightComponent(ux);
				panelActual = ux;
			}
			pAc = (PChatCI) panelActual;
			if(pAc.getmovilCI() != cIa.getMovil()) {
				PChatCI ux = new PChatCI(cIa, movilUA);
				splitPane.setRightComponent(ux);
				panelActual = ux;
			}
		} else {
			PChatG pAg = (PChatG) panelActual;
			Grupo cGa = (Grupo) contactoActual;
			if(panelActual == null) {
				PChatG ux = new PChatG(cGa, movilUA);
				splitPane.setRightComponent(ux);
				panelActual = ux;
			}
			else if(pAg.getIDG() != cGa.getId()) {
				PChatG ux = new PChatG(cGa, movilUA);
				splitPane.setRightComponent(ux);
				panelActual = ux;
			}
		}
	}
	
	public int getMovilUA() {
		return movilUA;
	}
	
	public void addPanelContacto(Contacto c, int i) {
		
		mnInfoCuenta.setEnabled(false);
		mnBusqueda.setEnabled(false);
		mnEliminaciones.setEnabled(false);
		luz.setEnabled(false);
		
		panelUtlimosContactos.addPanelContacto(c, i);
	}
	
	public void modificarContacto(Contacto c, int i) {
		
		mnInfoCuenta.setEnabled(false);
		mnBusqueda.setEnabled(false);
		mnEliminaciones.setEnabled(false);
		luz.setEnabled(false);
		
		panelUtlimosContactos.modificarContacto(c,i);
	}
	
	public void update() {
		if(contactoActual != null && contactoActual.getClass() == ContactoIndividual.class) {
			ContactoIndividual aux = (ContactoIndividual) contactoActual;
			contactoActual = aux = ControladorAppChat.getUnicaInstancia().getContactoIndividual(aux.getMovil(), movilUA);
			ImageIcon iC = aux.getUsuario().getImagen();
			Image im = iC.getImage();
			iC = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			
		}
	}
	
	public void cambiarImagenPerfil() {
		Usuario u = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA);
		ImageIcon imagen = u.getImagen();
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mnIconoUsuario.setIcon(imagen);
	}
	
	public void setSaludo(String texto) {
		mntmSaludo.setText("\"" + texto + "\"");
		ControladorAppChat.getUnicaInstancia().setSaludo(texto,  movilUA);
	}
	
	public void busqueda(String texto) {
		splitPane.setRightComponent(new PBusqueda(texto, movilUA));
	}
	
	public void pGUCEliminado(int id) {
		if(contactoActual == null || contactoActual.getId() == id) {
			PChatG aux = (PChatG)panelActual;
			aux.setVisible(false);
			aux.removeUpdate();
			
			mnInfoCuenta.setEnabled(false);
			mnBusqueda.setEnabled(false);
			mnEliminaciones.setEnabled(false);
			luz.setEnabled(false);
		}
	}
	
	public void showOpcionesContacto() {
		mnInfoCuenta.setEnabled(true);
		mnBusqueda.setEnabled(true);
		mnEliminaciones.setEnabled(true);
		luz.setEnabled(true);
	}
	
	public void cambiarListaUltimosContactos() {
		splitPane.setLeftComponent(panelUtlimosContactos);
	}
}
