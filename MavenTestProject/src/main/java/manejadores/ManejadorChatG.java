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
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;
import vista.VMain;

public class ManejadorChatG {
	private static ManejadorChatG unicaInstancia;
	
	public static ManejadorChatG getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new ManejadorChatG();
		return unicaInstancia;
		
	}
	private ManejadorChatG() {}
	
	public void addBubbleText(String texto, int movilReceptor, int movilEmisor, JPanel panel) {
		if(!texto.equals("")) {
			ControladorAppChat.getUnicaInstancia().registrarMensajeG(texto,
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
	
	public int initChat(int idGrupo, int movilUA, JPanel panel) {
		Grupo g = ControladorAppChat.getUnicaInstancia().getGrupoPorId(idGrupo, movilUA);
		for(Mensaje m : g.getMensajes()) {
			Usuario emisor = m.getEmisor();
			if(emisor.getMovil() != movilUA) {
				ContactoIndividual ci = ControladorAppChat.getUnicaInstancia().getContactoIndividual(emisor.getMovil(), movilUA);
				BubbleText mensaje = null;
				if(ci != null) {
					if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN,ci.getNombre(), BubbleText.RECEIVED);
				else
					mensaje = new BubbleText(panel, m.getEmoticon(),
							Color.GREEN,ci.getNombre(), BubbleText.RECEIVED,18);
				}else {
					if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN,String.valueOf(emisor.getMovil()), BubbleText.RECEIVED);
				else
					mensaje = new BubbleText(panel, Integer.valueOf(m.getTexto()),
							Color.GREEN,String.valueOf(emisor.getMovil()), BubbleText.RECEIVED, 18);
				}
				
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
		return g.getMensajes().size();
	}
	
	
	public int update(int ultnMensajes, List<Mensaje> mensajesActualizados, int movilUA, JPanel panel) {
		if(ultnMensajes < mensajesActualizados.size()) {
			for(int i = ultnMensajes; i < mensajesActualizados.size(); i++) {
				Mensaje m = mensajesActualizados.get(i);
				ContactoIndividual ci = ControladorAppChat.getUnicaInstancia().
						getContactoIndividual(m.getEmisor().getMovil(), movilUA);
				
				BubbleText mensaje = null;
				if(ci != null) {
					if(!m.getTexto().equals(""))
						mensaje = new BubbleText(panel, m.getTexto(),
								Color.GREEN,ci.getNombre(), BubbleText.RECEIVED);
					else
						mensaje = new BubbleText(panel, m.getEmoticon(),
								Color.GREEN,ci.getNombre(), BubbleText.RECEIVED, 18);
				}
				else {
					if(!m.getTexto().equals(""))
				mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN,String.valueOf(m.getEmisor().getMovil()), BubbleText.RECEIVED);
					else
						mensaje = new BubbleText(panel, m.getEmoticon(),
								Color.GREEN,String.valueOf(m.getEmisor().getMovil()), BubbleText.RECEIVED, 18);
				}
				panel.add(mensaje);
				 
			}
		}
		return mensajesActualizados.size();
	}
}
