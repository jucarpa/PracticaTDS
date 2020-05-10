package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorAppChat;

import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelContacto extends JPanel implements PropertyChangeListener{
	private JLabel imagen, texto, nombre, hora; 
	private Contacto contacto;
	private JPanel panel_1;
	private PanelVistaPrincipal ventana;
	
	public PanelContacto(Contacto c, PanelVistaPrincipal p) {
		ventana = p;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Dimension d = new Dimension(300, 50);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		
		contacto = c;
		
		crearPanel();
		infoContacto();
		
		c.addContactoChangeListener(this);
		ControladorAppChat.getUnicaInstancia().addUsuarioChangeListener(this);
	}
	
	private void crearPanel() {
		setLayout(null);
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLocation(0, 0);
		panel_1.setSize(new Dimension(300, 50));
		panel_1.setMinimumSize(new Dimension(300, 50));
		panel_1.setMaximumSize(new Dimension(300, 50));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		add(panel_1);
		
		imagen = new JLabel();
		imagen.setSize(new Dimension(50, 50));
		imagen.setMinimumSize(new Dimension(50, 50));
		imagen.setMaximumSize(new Dimension(50, 50));
		panel_1.add(imagen);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setSize(new Dimension(230, 50));
		panel.setMinimumSize(new Dimension(230, 50));
		panel.setMaximumSize(new Dimension(230, 50));
		panel_1.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		nombre = new JLabel();
		nombre.setSize(new Dimension(230, 20));
		nombre.setMinimumSize(new Dimension(230, 20));
		nombre.setMaximumSize(new Dimension(230, 20));
		texto = new JLabel();
		texto.setVerticalAlignment(SwingConstants.BOTTOM);
		texto.setSize(new Dimension(230, 25));
		texto.setMinimumSize(new Dimension(230, 25));
		texto.setMaximumSize(new Dimension(230, 25));
		hora = new JLabel();
		hora.setSize(new Dimension(35, 50));
		hora.setMinimumSize(new Dimension(35, 50));
		hora.setMaximumSize(new Dimension(35, 50));
		panel.add(nombre);
		panel.add(texto);
		panel_1.add(hora);
		nombre.setFont(new Font("Arial",Font.BOLD,11));
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setOpaque(false);
		btnNewButton.setBounds(0, 0, 300, 50);
		add(btnNewButton);
		
		
		//Botono invisible que al pulsar al Panel Muestra El Chat del Contacto
		btnNewButton.addActionListener(e -> {
			ControladorAppChat.getUnicaInstancia().setContacto(contacto);
			ventana.contactoSeleccionado();
		});
		
		
	}
	
	//Se Ponen Los datos del Contacto
	private void infoContacto() {
		ImageIcon imIco = null;
		if(contacto instanceof ContactoIndividual) {
			Usuario usuarioContacto = ((ContactoIndividual) contacto).getUsuario();
			String urlImagen = usuarioContacto.getImagen();
			imIco = new ImageIcon(urlImagen);
		} else {
			URL urlImagen = VentanaMain.class.getResource("/imagenes/ImagenGrupoDef.png");
			imIco = new ImageIcon(urlImagen);
		}
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		imagen.setIcon(imIco);
		
		nombre.setText(contacto.getNombre());
		
		if(!contacto.getMensajes().isEmpty()) {
			int tamMensaje = contacto.getMensajes().size();
			Mensaje mensaje = contacto.getMensajes().get(tamMensaje - 1);
			addMensaje(mensaje);
		}
	}
	
	private void addMensaje(Mensaje mensaje) {
		LocalDateTime horaLocal = mensaje.getHora();
		String solHora = horaLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
		if(mensaje.getTexto().isEmpty()) {
			int nEmoticono = mensaje.getEmoticon();
			Image emoticono = BubbleText.getEmoji(nEmoticono).getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
			ImageIcon solEmoticono = new ImageIcon(emoticono);

			setInfoMensaje(solEmoticono, solHora);
		} else {
			String texto = mensaje.getTexto();
			setInfoMensaje(texto, solHora);
		}
	}
	
	private void setInfoMensaje(String texto, String hora) {
		this.texto.setIcon(null);
		this.texto.setText(texto);
		this.hora.setText(hora);
	}
	
	private void setInfoMensaje(ImageIcon imagen, String hora) {
		this.texto.setIcon(imagen);
		this.texto.setText("");
		this.hora.setText(hora);
	}
	
	
	//SII llega un Contacto
	//Solo se filtra si es el mismo Contacto
	//Esto Significa que lo van a eliminar
	//Eliminamos los oyentes y este Panel
	
	//SII llega mensaje
	//Bien puede ser porque se han eliminado todos
	//nMensaje == null
	//Bien puede ser porque hay un mensaje nuevo
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String evento = e.getPropertyName();
		if(evento.equals("contacto")) {
			Contacto oContacto = (Contacto) e.getOldValue();
			Contacto nContacto = (Contacto) e.getNewValue();
			if(oContacto != null) {
				if(oContacto.equals(contacto) 
						&& nContacto == null) {
					contacto.removeContactoChangeListener(this);
					ControladorAppChat.getUnicaInstancia().removeUsuarioChangeListener(this);
					setVisible(false);
				}
			}	
		}
		
		if(evento.equals("mensaje")) {
			Mensaje nMensaje = (Mensaje) e.getNewValue();
			if(nMensaje == null) {
				setInfoMensaje("", "");
			} else {
				addMensaje(nMensaje);
			}
		}
		
		if(evento.equals("nombreContacto")) {
			String nMensaje = (String) e.getNewValue();
			nombre.setText(nMensaje);
		}
		
		if(evento.equals("contactoImagen")) {
			String nImagen = (String) e.getNewValue();
			Image im = new ImageIcon(nImagen).getImage();
			ImageIcon imIco = new ImageIcon(im.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			imagen.setIcon(imIco);
		}
	}
}
