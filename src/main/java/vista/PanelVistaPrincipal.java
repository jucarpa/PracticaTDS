package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.EventObject;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;

import componentes.bin.parse.IParseListener;
import componentes.bin.parse.Parse;
import componentes.bin.parse.ParseEvent;
import componentes.bin.pulsador.EncendidoEvent;
import componentes.bin.pulsador.IEncendidoListener;
import componentes.bin.pulsador.Luz;
import controlador.ControladorActualizarUsuario;
import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
//Panel de la VistaPrincipal de la Aplicación
//3 partes principales:
//-Superior : MenuBar
//-Izquierda : Lista de Contactos
//-Derecha : Chat/ CrearContacto / ModificarContacto / Buscar Mensajes

@SuppressWarnings("serial")
public class PanelVistaPrincipal extends JPanel implements PropertyChangeListener, IEncendidoListener, IParseListener {
	private JMenuBar menuIzquierdo, menuDerecho;
	private JMenu mUsuario, mOpciones, mContacto, mModificar;
	private JMenuItem mniNombreUsuario, mniSaludoUsuario, mniCambiarNombreUsuario, mniCambiarSaludoUsuario, mBuscar;
	private JMenuItem mniCrearContacto, mniCrearGrupo, mniConvertirseEnPremium, mniGenerarPDF, mniGenerarEstadistica;
	private JMenuItem mniNombreContacto, mniMovilContacto;
	private JMenuItem mniModificarContacto, mniEliminarMensaje, mniEliminarContacto;
	private JMenuItem mntmEstado;
	private JPanel panel;
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelOld;
	private JPanel panelOldOld;
	
	private Contacto contactoActual;
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	
	//Componentes creados por el Alumno
	private Luz luz;
	private Parse componenteParseador;
	
	public PanelVistaPrincipal() {
		
		//Iniciamos Actualizar Usuario
		ControladorActualizarUsuario.getUnicaInstancia();
		
		//Dibujamos la Pantalla
		crearPantalla();
		//Rellenamos con la información del Usuario
		iniciarPantalla();
		
		//Metodos de los Botones del lado izquierdo
		crearListenersIzquierdos();
		
		//Metodo de los Botonoes del lazo derecho
		crearListenersDerechos();
		
		
		//Añadimos al Panel Como oyente del Usuario
		controlador.getUsuario().addUsuarioChangeListener(this);
		//Como oyente del Componente Luz
		luz.addEncendidoListener(this);
		
		//Como oyente del Componente Parse
		componenteParseador.addParseListener(this);
	}
	
	private void crearPantalla() {
		
		setMaximumSize(new Dimension(630,480));
		setSize(630,480);
		setMinimumSize(new Dimension(630,480));
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(new Color(153, 204, 102));
		add(panel, BorderLayout.CENTER);
		Dimension d = new Dimension(315,480);
		panel.setSize(d);
		panel.setMinimumSize(d);
		panel.setMaximumSize(d);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panelIzquierdo = new JPanel();
		panelIzquierdo.setSize(new Dimension(315, 480));
		panelIzquierdo.setPreferredSize(new Dimension(315, 480));
		panelIzquierdo.setMinimumSize(new Dimension(315, 480));
		panelIzquierdo.setMaximumSize(new Dimension(315, 480));
		panel.add(panelIzquierdo);
		panelIzquierdo.setLayout(new BorderLayout(0, 0));
	
		panelDerecho = new JPanel();
		panelDerecho.setSize(new Dimension(315, 480));
		panelDerecho.setMinimumSize(new Dimension(315, 480));
		panelDerecho.setMaximumSize(new Dimension(315, 480));
		panel.add(panelDerecho);
		panelDerecho.setLayout(new BorderLayout(0, 0));
	
		
		/*
		 *  MENUITEMS DEL JMENU DE LA IZQUIERDA
		 */
		menuIzquierdo = new JMenuBar();
		
		mUsuario= new JMenu();
		menuIzquierdo.add(mUsuario);
		mniNombreUsuario = new JMenuItem();
		mniSaludoUsuario = new JMenuItem();
		mniCambiarNombreUsuario = new JMenuItem("Cambiar Imagen");
		mniCambiarSaludoUsuario = new JMenuItem("Cambiar Saludo");
		
		mUsuario.add(mniNombreUsuario); mUsuario.add(mniSaludoUsuario);
		mUsuario.add(mniCambiarNombreUsuario); mUsuario.add(mniCambiarSaludoUsuario);
		
		mOpciones = new JMenu();
		mniCrearContacto = new JMenuItem("Crear Contacto");
		mniCrearGrupo = new JMenuItem("Crear Grupo");
		mniConvertirseEnPremium = new JMenuItem("Convertirse en Premium (40$)");
		mniGenerarPDF = new JMenuItem("Generar PDF");
		mniGenerarEstadistica = new JMenuItem("Información");
		mOpciones.add(mniCrearContacto); mOpciones.add(mniCrearGrupo);
		mOpciones.add(mniConvertirseEnPremium); mOpciones.add(mniGenerarPDF); mOpciones.add(mniGenerarEstadistica);
		menuIzquierdo.add(mOpciones);
		
		mntmEstado = new JMenuItem();
		mntmEstado.setMinimumSize(new Dimension(30, 30));
		mntmEstado.setMaximumSize(new Dimension(30, 30));
		menuIzquierdo.add(mntmEstado);
		
		panelIzquierdo.add(menuIzquierdo, BorderLayout.NORTH);
		
		//FUENTES PARA OPCIONES PREMIUM
		mniGenerarPDF.setForeground(new Color(240, 230, 140));
		mniGenerarPDF.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		
		mniGenerarEstadistica.setForeground(new Color(240, 230, 140));
		mniGenerarEstadistica.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		
		/*
		 *  MENUITEMS DEL JMENU DE LA DERECHA
		 */
		menuDerecho = new JMenuBar();
		menuDerecho.setBackground(Color.WHITE);
		
		mContacto = new JMenu();
		menuDerecho.add(mContacto);
		mniNombreContacto = new JMenuItem();
		mniMovilContacto = new JMenuItem();
		mContacto.add(mniNombreContacto); mContacto.add(mniMovilContacto);
		
		mBuscar = new JMenuItem();
		mBuscar.setMinimumSize(new Dimension(30, 30));
		mBuscar.setMaximumSize(new Dimension(30, 30));
		menuDerecho.add(mBuscar);
				
		mModificar = new JMenu();
		menuDerecho.add(mModificar);
		mniModificarContacto = new JMenuItem("Modificar Contacto");
		mniEliminarMensaje = new JMenuItem("Eliminar Mensajes");
		mniEliminarContacto = new JMenuItem("Eliminar Contacto");
		mModificar.add(mniModificarContacto); mModificar.add(mniEliminarContacto);
		mModificar.add(mniEliminarMensaje);
		
		panelDerecho.add(menuDerecho, BorderLayout.NORTH);
		menuDerecho.setVisible(false);
		
		
		//Anyadimos Componente Luz
		luz = new Luz();
		menuDerecho.add(luz);
		
		//Inicializamos Componente Parse
		componenteParseador = new Parse();
		
	}
	
	//Obtenemos la información del Usuario y a parte cambiamos las imágenes del panel
	private void iniciarPantalla() {
		
		//Informacion del Usuario
		String saludo = controlador.getSaludo();
		String nombre = controlador.getNombre();
		String urlImagen =controlador.getImagen();
		
		ImageIcon imIco = new ImageIcon(urlImagen);
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		mniSaludoUsuario.setText(saludo);
		mniNombreUsuario.setText(nombre);
		mUsuario.setIcon(imIco);
		
		//Imagenes para el menuBar
		ImageIcon imOpciones =  new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenConfiguracion.png"));
		Image iOpciones = imOpciones.getImage();
		imOpciones= new ImageIcon(iOpciones.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mOpciones.setIcon(imOpciones);
		
		ImageIcon imBucar = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenBuscar.png"));
		Image iBuscar = imBucar.getImage();
		imBucar= new ImageIcon(iBuscar.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mBuscar.setIcon(imBucar);
		
		ImageIcon imModificar = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenAjustes.png"));
		Image iModificar= imModificar.getImage();
		imModificar = new ImageIcon(iModificar.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mModificar.setIcon(imModificar);
		
		ImageIcon imEstado = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenEstado.png"));
		Image iEstado= imEstado.getImage();
		imEstado = new ImageIcon(iEstado.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mntmEstado.setIcon(imEstado); 
		
		//PanelIzquierdo
		PanelContactos pContactos = new PanelContactos(this);
		panelIzquierdo.add(pContactos, BorderLayout.CENTER);
		
		if(!controlador.esPremium()) {
			mniGenerarPDF.setVisible(false);
			mniGenerarEstadistica.setVisible(false);
		}else
			mniConvertirseEnPremium.setVisible(false);
	}
	
	//Creamos ActionListeners De los JMenuItems
	private void crearListenersIzquierdos() {
		mniCambiarSaludoUsuario.addActionListener(e -> {
			PanelCambiarSaludo sol = new PanelCambiarSaludo();
			sol.setVisible(true);
		});
		
		mniCambiarNombreUsuario.addActionListener(e ->  {
			PanelCambiarImagen sol= new PanelCambiarImagen();
			sol.setVisible(true);
		});
		
		mniCrearContacto.addActionListener(e -> {
			PanelCrearModificarContacto sol = new PanelCrearModificarContacto(this);
			cambiarPanelDerecho(sol);
		});
		
		mniCrearGrupo.addActionListener(e -> {
			PanelCrearModificarGrupo sol = new PanelCrearModificarGrupo(this);
			cambiarPanelDerecho(sol);
		});
		
		mniConvertirseEnPremium.addActionListener(e -> {
			premium();
			
		});
		
		mniGenerarPDF.addActionListener(e -> {
			try {
				controlador.generarPDF();
				JOptionPane.showMessageDialog( this, "PDF Generado.",
						"Generar PDf",JOptionPane.INFORMATION_MESSAGE);
			} catch (DocumentException | IOException e1) {
				e1.printStackTrace();
			}
		});
		
		mniGenerarEstadistica.addActionListener(e -> {
			PanelInformacion pInfo = new PanelInformacion();
			pInfo.imprimir();
			JOptionPane.showMessageDialog( this, "Información Generada.",
					"Generar Informacion Uso",JOptionPane.INFORMATION_MESSAGE);
		});
		
		mntmEstado.addActionListener(e -> {
			PanelEstado sol = new PanelEstado(this);
			cambiarPanelDerecho(sol);
		});
	}
	
	private void crearListenersDerechos() {
		mniModificarContacto.addActionListener(e -> {
			if(contactoActual instanceof Grupo) {
				PanelCrearModificarGrupo sol = new PanelCrearModificarGrupo(this, (Grupo) contactoActual);
				cambiarPanelDerecho(sol);
			} else {
				PanelCrearModificarContacto sol = new PanelCrearModificarContacto(this, (ContactoIndividual) contactoActual);
				cambiarPanelDerecho(sol);
			}
		});
		
		mniEliminarMensaje.addActionListener(e -> {
			accionEliminarMensajes();
		});
		
		mniEliminarContacto.addActionListener(e -> {
			accionEliminarContacto();
		});
		
		mBuscar.addActionListener(e -> {
			PanelBuscar pB = new PanelBuscar(contactoActual, this);
			cambiarPanelDerecho(pB);
		});
	}
	
	private void accionEliminarMensajes() {
		if(contactoActual instanceof Grupo) {
			boolean esAdmin = controlador.esAdmin((Grupo) contactoActual);
			if(!esAdmin) {
				JOptionPane.showMessageDialog( this, "Debes de ser el Administrador del Grupo.",
						"Eliminar Mensajes",JOptionPane.INFORMATION_MESSAGE);
			return;
			}
		}
		int i = JOptionPane.showConfirmDialog( this, "¿Estás Seguro de que deseas eliminar los Mensajes ?.",
				"Eliminar Mensajes",JOptionPane.YES_NO_OPTION);
		
		//Botón Sí
		if(i == 0) {
			controlador.eliminarMensajes(contactoActual);
		}
	}
	
	private void accionEliminarContacto() {
		if(contactoActual instanceof Grupo) {
			boolean esAdmin = controlador.esAdmin((Grupo) contactoActual);
			if(!esAdmin) {
				JOptionPane.showMessageDialog( this, "Debes de ser el Administrador del Grupo.",
						"Eliminar Contacto",JOptionPane.INFORMATION_MESSAGE);
			return;
			}
		}
		int i = JOptionPane.showConfirmDialog( this, "¿Estás Seguro de que deseas eliminar el Contacto?.",
				"Eliminar Contacto",JOptionPane.YES_NO_OPTION);
		
		//Botón Sí
		if(i == 0) {
			controlador.eliminarContacto(contactoActual);
			cambiarPanelDerecho(new JPanel());
			
		}
	}
	
	//Metodo al pulsar el Listener de ser premium
	//Se llama al Controlador para pagar
	//Si no hay dinero suficiente se pide hacer un ingreso
	private void premium() {
		int i = JOptionPane.showConfirmDialog( this, "¿Estás Seguro de que deseas convertirte en Premium?.",
				"Ser premium",JOptionPane.YES_NO_OPTION);
		if(i == 0) {
			String saldo = controlador.getSaldo() + "";
			boolean pagado = controlador.pagar();
			if(!pagado){
				double precio = controlador.getPrecio();
				int j = JOptionPane.showConfirmDialog( this, "tienes " + saldo + "$ y cuesta " + precio + "$ con descuentos. ¿Quieres ingresar 40 $?",
						"Ser premium",JOptionPane.YES_NO_OPTION);
				if(j == 0) {
				controlador.addSaldo(40);
				pagado = controlador.pagar();
				}
			}
			if(pagado) {
				JOptionPane.showMessageDialog( this, "Enhorabuena, Ya eres Premium.",
						"Ser Premium",JOptionPane.INFORMATION_MESSAGE);
				
					mniGenerarPDF.setVisible(true);
					mniGenerarEstadistica.setVisible(true);
					mniConvertirseEnPremium.setVisible(false);
			}
		}
	}
	
	//Cambiar Saludo del Usuario
	//Se llama desde el metodo properyChange
	private void setSaludo(String saludo) {
		mniSaludoUsuario.setText(saludo);
	} 
	
	//Cambiar Image Usuario
	//Se llama desde el metodo properyChange
	private void setImagen(String imagen) {
		ImageIcon imIco = new ImageIcon(imagen);
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mUsuario.setIcon(imIco);
	}
	
	//Cambia panel derecho de la vista
	//Guarda el panel que estaba anteriormente
	//Esto se hace en el caso de cuando cambiamos por un
	//Panel que no sea de Chat, podamos volver al PanelChat
	private void cambiarPanelDerecho(JPanel panelNuevo) {
		if(panelOld != null)
			panelDerecho.remove(panelOld);
		if(!(panelNuevo instanceof PanelChat))
			menuDerecho.setVisible(false);
		else
			menuDerecho.setVisible(true);
		panelDerecho.add(panelNuevo, BorderLayout.CENTER);
		panelDerecho.revalidate();
		panelOldOld = panelOld;
		panelOld = panelNuevo;
	}
	
	/*
	 * METODOS QUE SE LLAMAN EXTERNAMENTE
	 */
	
	//Metodo que se llama al pulsar botón cancelar en otras ventanas
	public void cancelar() {
		if (panelOldOld == null)
			panelOldOld = new JPanel();
		cambiarPanelDerecho(panelOldOld);
	}
	
	//Metodo que es llamado desde PanelContacto al Seleccionar un Contacto//Al crearlo o al Modificarlo
	public void contactoSeleccionado(Contacto c) {
		PanelChat panelChat = new PanelChat(c);
		contactoActual = c;
		cambiarPanelDerecho(panelChat);
		
		if(contactoActual instanceof ContactoIndividual)
			informacionContacto((ContactoIndividual) contactoActual);
		else
			informacionContacto((Grupo) contactoActual);
	}
	
	//Informacion del Contacto en el MenuBar de la Derecha
	//Llamada auxiliar del metodo contactoSeleccionado
	private void informacionContacto(ContactoIndividual ci) {
		ImageIcon imContacto = new ImageIcon(ci.getUsuario().getImagen());
		Image im = imContacto.getImage();
		imContacto = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mContacto.setIcon(imContacto);
		mniNombreContacto.setText(ci.getNombre());;
		mniMovilContacto.setText(ci.getMovil() + "");
	}
	
	//Usamos genericidad
	private void informacionContacto(Grupo g ) {
		ImageIcon imContacto = new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenGrupoDef.png"));
		Image im = imContacto.getImage();
		imContacto = new ImageIcon(im.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		mContacto.setIcon(imContacto);
		
		mniNombreContacto.setText(g.getNombre());
		mniMovilContacto.setText("Admin: " + g.getAdmin().getNombre());
	}
	
	/*
	 * METODOS DE EVENTOS (INTERFACES IMPLEMENTADAS)
	 */
	
	//Metodo de la Interfaz PropertyChangeListener
	//Inlucida en el modelo de Negocio en las clases Usuario y Contacto
	public void propertyChange(PropertyChangeEvent e) {
		String evento = e.getPropertyName();
		if(evento.equals("imagen")) {
			String nImagen = (String) e.getNewValue();
			setImagen(nImagen);
		}
		if(evento.equals("saludo")) {
			String nSaludo = (String)e.getNewValue();
			setSaludo(nSaludo);
		}
	}

	//Evento de Componente Luz
	public void enteradoCambioEncendido(EventObject e) {
		EncendidoEvent event= (EncendidoEvent) e;
		boolean encendido = event.getNewEncendido();
		if(encendido) {
			//Dibujamos el comoponente parseador
			componenteParseador.paint();
			//Apagamos el botón para poder clickarle otra vez
			luz.setEncendido(false);
		}
	}
	
	//Evento de Componente Parse
	public void enteradoParse(EventObject e) {
		ParseEvent event = (ParseEvent) e;
		controlador.parse(event.getFichero(), event.getFormatDate(), event.getPlataforma(), contactoActual);
	}

}
