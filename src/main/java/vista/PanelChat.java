package vista;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import controlador.ControladorAppChat;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class PanelChat extends JPanel implements PropertyChangeListener {

	private Contacto contacto;
	private JPanel pChat;
	private JTextField textField;
	private int on = 0;
	PanelEmoticonos ventanaEmoticonos;
	
	public PanelChat(Contacto c) {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Dimension d = new Dimension(310,450);
		setMinimumSize(d);
		setSize(d);
		setMaximumSize(d);
		
		contacto = c;
		//Agregamos al PanelChat como oyente, para la actualización de mensajes
		contacto.addContactoChangeListener(this);
		ControladorAppChat.getUnicaInstancia().getUsuario().addUsuarioChangeListener(this);
		
		crearPanel();
	}
	
	private void crearPanel() {
		setLayout(null);
		
		ventanaEmoticonos = new PanelEmoticonos(this);
		ventanaEmoticonos.setBounds(0,215, 133, 200);
		add(ventanaEmoticonos);
		ventanaEmoticonos.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ventanaEmoticonos.setVisible(false);
		fixedSize(ventanaEmoticonos, 50, 80);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds(0, 0, 308, 448);
		fixedSize(panelPrincipal, 310, 450);
		add(panelPrincipal);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		
		pChat = new JPanel();
		pChat.setAutoscrolls(true);
		pChat.setOpaque(false);
		pChat.setBounds(new Rectangle(0, 0, 315, 40));
		pChat.setAlignmentY(Component.TOP_ALIGNMENT);
		
		BoxLayout bL = new BoxLayout(pChat, BoxLayout.Y_AXIS);
		pChat.setLayout(bL);
		
		JScrollPane scroll = new JScrollPane(pChat
				, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setOpaque(false);
		scroll.setBounds(new Rectangle(0, 0, 315, 40));
		fixedSize(scroll, 315, 40);
		
		panelPrincipal.add(scroll, BorderLayout.CENTER);
		
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		//Obtenemos la imagen del Emoticono
		ImageIcon emoji= new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenEmojiDef.png"));
		Image i = emoji.getImage();
		emoji= new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		//Creamos el boton del emoticono
		JButton btnEmoji = new JButton("");
		btnEmoji.setMaximumSize(new Dimension(30, 30));
		btnEmoji.setMinimumSize(new Dimension(30, 30));
		btnEmoji.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEmoji.setBackground(new Color(152, 251, 152));
		btnEmoji.setPreferredSize(new Dimension(30, 30));
		panelInferior.add(btnEmoji);
		btnEmoji.setIcon(emoji);
		
		textField = new JTextField();
		panelInferior.add(textField);
		
		iniciarMensajes();
		
		//Texto al escribir
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String texto = textField.getText().trim();
					if(!texto.isEmpty())
						enviarMensaje(texto);
					textField.setText("");
				}
			}
		});
		
		btnEmoji.addActionListener(e -> {
			if(on == 0) {
				on = 1;
				ventanaEmoticonos.setVisible(true);
			} else {
				on = 0;
				ventanaEmoticonos.setVisible(false);
			}
		});
				
		
	}
	
	//METODO PARA RECUPERAR LOS MENSAJES DEL CONTACTO
	//Y AÑADIRLOS AL PANEL
	private void iniciarMensajes() {
		List<Mensaje> mensajes = contacto.getMensajes();
		
		if(contacto instanceof ContactoIndividual) { 
			
			mensajes.stream()
				.forEach(m -> {
					addMensajeCI(m.getTexto(), m.getEmoticon(), m.getEmisor());
				});
		} else {
			mensajes.stream()
			.forEach(m -> {
				addMensajeG(m.getTexto(), m.getEmoticon(), m.getEmisor());
			});
		}
	}
	
	//Cuando el Chat es Para ContactoIndividual
	private void addMensajeCI(String texto, int emoticono, Usuario emisor) {
		Usuario usuarioActual = ControladorAppChat.getUnicaInstancia().getUsuario();
		BubbleText mensaje = null;
		if(emisor.equals(usuarioActual)) {
			if(texto.isEmpty()) 
				mensaje = new BubbleText(pChat, emoticono,
						Color.GREEN,"", BubbleText.SENT, 18);
			else
				mensaje = new BubbleText(pChat, texto,
						Color.GREEN,"", BubbleText.SENT);
			
		} else {
			if(texto.isEmpty()) 
				mensaje = new BubbleText(pChat, emoticono,
						Color.GREEN,"", BubbleText.RECEIVED, 18);
			else
				mensaje = new BubbleText(pChat, texto,
						Color.GREEN,"", BubbleText.RECEIVED);
			
		}
		pChat.add(mensaje);
	}
	
	//Cuando el Chat es para Grupo
	private void addMensajeG(String texto, int emoticono, Usuario emisor) {
		Usuario usuarioActual = ControladorAppChat.getUnicaInstancia().getUsuario();
		BubbleText mensaje = null;
		
		if(emisor.equals(usuarioActual)) {
			if(texto.isEmpty()) 
				mensaje = new BubbleText(pChat, emoticono,
						Color.GREEN,"", BubbleText.SENT, 18);
			else
				mensaje = new BubbleText(pChat, texto,
						Color.GREEN,"", BubbleText.SENT);
			
		}
		else {
			String nombreUsuario;
			if(ControladorAppChat.getUnicaInstancia().existeContacto(emisor.getMovil()))
				nombreUsuario = ControladorAppChat.getUnicaInstancia().getNombreContacto(emisor);
			else
				nombreUsuario = emisor.getMovil() + "";
			
			if(texto.isEmpty()) 
				mensaje = new BubbleText(pChat, emoticono,
						Color.GREEN,nombreUsuario, BubbleText.RECEIVED, 18);
			else
				mensaje = new BubbleText(pChat, texto,
						Color.GREEN,nombreUsuario, BubbleText.RECEIVED);
		}
		
		pChat.add(mensaje);
	}
	
	//Metodo para enviar Mensaje
	private void enviarMensaje(String texto) {
		//Guardamos los mensajes ya sean en grupo o en ContactoIndividual
		if(contacto instanceof Grupo) {
			ControladorAppChat.getUnicaInstancia().crearMensajeG(texto, 0, (Grupo) contacto);
		} else { 
			ControladorAppChat.getUnicaInstancia().crearMensajeCI(texto, 0, (ContactoIndividual) contacto);
		}

	}
	
	void enviarMensaje(int emoticono) {
		on = 0;
		ventanaEmoticonos.setVisible(false);
		
		if(contacto instanceof Grupo) {
			ControladorAppChat.getUnicaInstancia().crearMensajeG("", emoticono, (Grupo) contacto);
		} else { 
			ControladorAppChat.getUnicaInstancia().crearMensajeCI("", emoticono, (ContactoIndividual) contacto);
		}
		
	}
	
	//Eliminar los mensajes del Contacto
	public void mensajesEliminados() {
		pChat.removeAll();
		pChat.revalidate();
	}
	
	

	//Metodo para Actualizar el Chat
	//Cuando llegan Mensajes nuevos, incluidos los nuestro
	//Cuando se quiere borrar el Chat o Mensajes
	public void propertyChange(PropertyChangeEvent e) {
		String evento = e.getPropertyName();
		//SII LLEGA CONTACTO
		if(evento.equals("contacto")) {
			Contacto oContacto = (Contacto) e.getOldValue();
			Contacto nContacto = (Contacto) e.getNewValue();
			
			//Si el oContacto no es NULL
			if(oContacto != null) {
				//SI oCOntactos somos nostroso y el nuevo es NULL 
				if(oContacto.equals(contacto) 
						&& nContacto == null) {
					//Contacto ELIMINADO. Esto se envia desde usuario. PanelVistaPrincipal -> ControladorAppChat -> Usuario.removeContacto
					contacto.removeContactoChangeListener(this);
					ControladorAppChat.getUnicaInstancia().getUsuario().removeUsuarioChangeListener(this);
					setVisible(false);
				}
			}
		}
			
		if(evento.equals("mensaje")) {
			Mensaje nMensaje = (Mensaje) e.getNewValue();
			//SII nMensaje == null -> mensajes eliminados
			if(nMensaje == null) {
				mensajesEliminados();
			} else {
				//Mensaje nuevo
				if(contacto instanceof Grupo)
					addMensajeG(nMensaje.getTexto(), nMensaje.getEmoticon(), nMensaje.getEmisor());
				else
					addMensajeCI(nMensaje.getTexto(), nMensaje.getEmoticon(), nMensaje.getEmisor());
			}
		}
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

}
