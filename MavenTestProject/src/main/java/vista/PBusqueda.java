package vista;

import javax.swing.JPanel;

import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.Mensaje;
import modelo.Usuario;
import javax.swing.BoxLayout;

public class PBusqueda extends JPanel {

	/**
	 * Create the panel.
	 */
	public PBusqueda(String texto, int movilUA) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Usuario u = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA);
		
		for(Contacto c : u.getContactos()) {
			for(Mensaje m : c.getMensajes()) {
				if(m.getTexto().contains(texto)) {
					add(new ItemBusqueda(m));
				}
			}
		}
	}

}
