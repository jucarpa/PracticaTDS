package manejadores;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorAppChat;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;
import vista.VMain;

public class ManejadorChatCI {
	private static ManejadorChatCI unicaInstancia;
	
	public static ManejadorChatCI getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new ManejadorChatCI();
		return unicaInstancia;
		
	}
	private ManejadorChatCI() {}
	
	public void addBubbleText(String texto, int movilReceptor, int movilEmisor, JPanel panel) {
		if(!texto.equals("")) {
			ControladorAppChat.getUnicaInstancia().registrarMensajeCI(texto,
						0, movilReceptor,movilEmisor);	
			BubbleText mensaje = new BubbleText(panel, texto, Color.GREEN,
					"", BubbleText.SENT);
			panel.add(mensaje);
		}
	}
	
	public void addBubbleText(int emoji, int movilReceptor, int movilEmisor, JPanel panel) {
			ControladorAppChat.getUnicaInstancia().registrarMensajeCI("",
						emoji, movilReceptor,movilEmisor);	
			BubbleText mensaje = new BubbleText(panel, emoji, Color.GREEN,
					"", BubbleText.SENT,18);
			panel.add(mensaje);
	}
	
	public int initChat(int movilCI, int movilUA, JPanel panel) {
		ContactoIndividual ci = ControladorAppChat.getUnicaInstancia().getContactoIndividual(movilCI, movilUA);
		for(Mensaje m : ci.getMensajes()) {
			ContactoIndividual receptor = (ContactoIndividual) m.getReceptor();
			if(receptor.getMovil() == movilUA) {
				BubbleText mensaje = null;
				if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN,"", BubbleText.RECEIVED);
				else
					mensaje = new BubbleText(panel, m.getEmoticon(),
							Color.GREEN,"", BubbleText.RECEIVED, 18);
				panel.add(mensaje);
			} else {
				BubbleText mensaje = null;
				if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN, "", BubbleText.SENT);
				else
					mensaje = new BubbleText(panel, m.getEmoticon(),
							Color.GREEN, "", BubbleText.SENT, 18);
				panel.add(mensaje);
			}
		}
		return ci.getMensajes().size();
	}
	
	
	public int update(int ultnMensajes, List<Mensaje> mensajesActualizados, JPanel panel) {
		if(ultnMensajes < mensajesActualizados.size()) {
			for(int i = ultnMensajes; i < mensajesActualizados.size(); i++) {
				Mensaje m = mensajesActualizados.get(i);
				BubbleText mensaje = null;
				if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN,"", BubbleText.RECEIVED);
				else
					mensaje = new BubbleText(panel, m.getEmoticon(),
							Color.GREEN,"", BubbleText.RECEIVED, 18);
				panel.add(mensaje);
			}
		}
		return mensajesActualizados.size();
	}
}
