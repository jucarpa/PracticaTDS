package manejadores;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.Contacto;
import modelo.Mensaje;

public class OrdenarContactos {
	private static OrdenarContactos unicaInstancia;
	
	public static OrdenarContactos getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new OrdenarContactos();
		return unicaInstancia;
	}
	
	private OrdenarContactos() {
	}
	
	public List<Contacto> Bubble(List<Contacto> contactos) {
		 for (int i = 1; i < contactos.size(); i++) {
			 for (int j = 0 ; j < contactos.size() - 1; j++) {
				 LocalDateTime horaJ = null;
				 LocalDateTime horaJ1 = null;
				 if(contactos.get(j).getMensajes().size() != 0) {
					 horaJ = contactos.get(j).getMensajes().get(contactos.get(j).getMensajes().size() - 1).getHora();
				 }
				 if(contactos.get(j +1).getMensajes().size() != 0) { 
					 horaJ1 = contactos.get(j + 1).getMensajes().get(contactos.get(j + 1).getMensajes().size() - 1).getHora();
				 }	if(horaJ1 != null)
					if (horaJ == null || horaJ.isBefore(horaJ1)) {
					 Contacto temp = contactos.get(j);
					 contactos.set(j, contactos.get(j+1));
					 contactos.set(j + 1, temp);
				 }
			 }
		 }
		 return contactos;
	}
}
