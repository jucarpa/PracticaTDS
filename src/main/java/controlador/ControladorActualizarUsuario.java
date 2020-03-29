package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.PoolDAO;

//Esta Clase utiliza un reloj que actualiza al Usuario manualmente
//Para que todos los oyentes funcionen
public class ControladorActualizarUsuario {
	private static ControladorActualizarUsuario unicaInstancia;
	
	
	//Singleton
	public static ControladorActualizarUsuario getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new ControladorActualizarUsuario();
		return unicaInstancia;
		
	}
	
	private ControladorActualizarUsuario() {
		//Actualizamos Cada 5s
		Timer timer = new Timer (5000, new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {	
		    	PoolDAO.getUnicaInstancia().update();
		    	CatalogoUsuarios.getUnicaInstancia().update();
		    	actualizarUsuario();
		     }
		});
		timer.setRepeats(true);
		timer.start();
	}
	
	private void actualizarUsuario() {
		Usuario oldUsuario = ControladorAppChat.getUnicaInstancia().getUsuario();
		Usuario newUsuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(oldUsuario.getUsuario());
		
		
		List<Contacto> oContactos = oldUsuario.getContactos();
		List<Contacto> newContactos = newUsuario.getContactos();
		
		//Comparamos Contactos Eliminados
		oContactos.stream()
		.filter(c -> !newContactos.contains(c))
		.forEach(c -> {
			oldUsuario.removeContacto(c);
		});
		
		//Comparamos Contactos Nuevos
		newContactos.stream()
		.filter(c -> !oContactos.contains(c))
		.forEach(c-> {
			oldUsuario.addContacto(c);
		});
		
		//Vemos Si tenemos nuevos Mensajes
		List<Contacto> oldContactos = oldUsuario.getContactos();
		for(int i = 0; i < oldContactos.size(); i++) {
			for(int j = 0; j < oldContactos.size(); j++) {
				Contacto oC = oldContactos.get(i);
				Contacto nC = newContactos.get(j);
				
				//Si es el mismo Contacto en ambas listas
				if(oC.equals(nC)) {
					//Si se han eliminado los mensajes
					if(nC.getMensajes().isEmpty()) {
						oC.eliminarMensajes();
					}
					int nTam = nC.getMensajes().size();
					int oTam = oC.getMensajes().size();
					//Si hay nuevos mensajes
					if(nTam > oTam) {
						for(int k = oTam; k < nTam; k++) {
							Mensaje m = nC.getMensajes().get(k);
							System.out.println("MENSAJE NUEVO\nEscrito Por: " + m.getEmisor().getNombre()+"\nContenido: " + m.getTexto());
							oC.addMensaje(m);
						}
					}
					//Si es un ContactoIndividual Comprobamos si la imagen del Usuario ha cambiado
					if(oC instanceof ContactoIndividual) {
						String oIm = ((ContactoIndividual) oC).getImagenUsuario();
						String nIm = ((ContactoIndividual) nC).getImagenUsuario();
						if(!oIm.equals(nIm)) {
							((ContactoIndividual) oC).setImagenUsuario(nIm);
						}
					}
					
				}
			}
		}
		
		ControladorAppChat.getUnicaInstancia().setUsuario(oldUsuario);
		
	}
}
