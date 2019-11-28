package Clases.Manejadores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Clases.*;
import beans.Entidad;
import beans.Propiedad;
import javafx.scene.image.Image;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class ManejadorInit {
	private ServicioPersistencia servPersistencia;
	private ArrayList<Entidad> entidades;
	private Map<Integer, Usuario> usuarios;
	private Map<Integer, ContactoIndividual> contactosIndividuales;
	private Map<Integer, Grupo> grupos;
	private Map<Integer,Mensaje> mensajes;
	
	public ManejadorInit() {
		servPersistencia = 
				FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		entidades = servPersistencia.recuperarEntidades();
		usuarios = new HashMap<Integer, Usuario>();
		contactosIndividuales = new HashMap<Integer, ContactoIndividual>();
		grupos = new HashMap<Integer, Grupo>();
		mensajes = new HashMap<Integer, Mensaje>();
		
		for(int i = 0; i < entidades.size(); i++) {
			Entidad aux = entidades.get(i);
			if(aux.getNombre().equals("Usuario")) {
				usuarios.put(aux.getId(), getUsuario(aux.getId()));
			} else if(aux.getNombre().equals("ContactoIndividual")) {
				contactosIndividuales.put(aux.getId(), getCI(aux.getId()));
			} else if(aux.getNombre().equals("Grupo")) {
				grupos.put(aux.getId(), getGrupo(aux.getId()));
			} else if(aux.getNombre().equals("Mensaje")) {
				mensajes.put(aux.getId(), getMensaje(aux.getId()));
			}
		}
		
		for(Usuario u : usuarios.values()) {
			updateUsuario(u);
		}
		
		for(ContactoIndividual ci : contactosIndividuales.values()) {
			updateCI(ci);
		}
		
		for(Grupo g : grupos.values()) {
			updateGrupo(g);
		}
		
		for(Mensaje m : mensajes.values()) {
			updateMensaje(m);
		}

	}
	
	private Usuario getUsuario(int idU) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(idU);
		
		String nombre = "";
		LocalDate fechaNacimiento = null;
		int movil = 0;
		String usuario = "";
		String contraseña = "";
		Image imagen = null;
		boolean premium = true;
		
		for(Propiedad p : eUsuario.getPropiedades()) {
			if(p.getNombre().equals("")) {
				
			} else if(p.getNombre().equals("nombre")) {
				nombre = p.getValor();
			} else if(p.getNombre().equals("fechaNacimiento")) {
				fechaNacimiento = LocalDate.parse(p.getValor());
			} else if(p.getNombre().equals("movil")) {
				movil = Integer.valueOf(p.getValor());
			} else if(p.getNombre().equals("usuario")) {
				usuario = p.getValor();
			} else if(p.getNombre().equals("contraseña")) {
				contraseña = p.getValor();
			} else if(p.getNombre().equals("imagen")) {
				if(!p.getValor().equals(""))
				imagen = new Image(p.getValor());
			} else if(p.getNombre().equals("premium")) {
				if(p.getValor().equals("false")) premium = false;
			}  
		}
		return new Usuario(nombre, fechaNacimiento, movil, usuario, contraseña, imagen, premium, idU);
	}
	
	private ContactoIndividual getCI(int idCI) {
		Entidad eCI = servPersistencia.recuperarEntidad(idCI);
		
		String nombre = "";
		int movil = 0;
		
		for(Propiedad p : eCI.getPropiedades()) {
			if(p.getNombre().equals("nombre")) {
				nombre = p.getValor();
			} else if(p.getNombre().equals("movil")) {
				movil = Integer.valueOf(p.getValor());
			}
		}
		
		return new ContactoIndividual(nombre, movil, idCI);
	}
	
	private Grupo getGrupo(int idG) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(idG);
		
		String nombre = "";
		
		for(Propiedad p : eGrupo.getPropiedades()) {
			if(p.getNombre().equals("nombre")) {
				nombre = p.getValor();
			}
		}
		
		return new Grupo(nombre, idG);
	}
	
	private Mensaje getMensaje(int idM) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(idM);
		
		String texto = "";
		LocalTime hora = null;
		int emoticono = 0;
		
		for(Propiedad p : eMensaje.getPropiedades()) {
			if(p.getNombre().equals("texto")) {
				texto = p.getValor();
			} else if(p.getNombre().equals("hora")) {
				hora = LocalTime.parse(p.getValor());
			} else if(p.getNombre().equals("emoticono")) {
				emoticono = Integer.valueOf(p.getValor());
			}
		}
		
		return new Mensaje(texto, hora, emoticono, idM);
	}

	private void updateUsuario(Usuario u) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(u.getIdUsuario());
		String idContactos = "";
		String idGAdmins = "";
		
		for(Propiedad p : eUsuario.getPropiedades()) {
			if(p.getNombre().equals("contactos")) {
				idContactos = p.getValor();
			} else if(p.getNombre().equals("gruposAdmin")) {
				idGAdmins = p.getValor();
			}
		}
		
		int ini = 0;
		for(int i = 0; i < idContactos.length(); i++) {
			if(idContactos.charAt(i) == ' ') {
				int idContacto = Integer.valueOf(idContactos.substring(ini, i));
				ini = i + 1;
				if(contactosIndividuales.containsKey(idContacto)) {
					ContactoIndividual ci = contactosIndividuales.get(idContacto);
					u.addContacto(ci.getNombre(), ci);
				} else {
					Grupo g = grupos.get(idContacto);
					u.addContacto(g.getNombre(), g);
				}
			}
		}
		ini = 0;
		for(int i = 0; i < idGAdmins.length(); i++) {
			if(idGAdmins.charAt(i) == ' ') {
				int idG = Integer.valueOf(idGAdmins.substring(ini, i));
				ini = i+1;
				
				u.addGrupoAdmin(grupos.get(idG));
			}
		}
	}

	private void updateCI(ContactoIndividual ci) {
		Entidad eCI = servPersistencia.recuperarEntidad(ci.getId());
		int usuario = 0;
		String mensajes = "";
		
		for(Propiedad p: eCI.getPropiedades()) {
			if(p.getNombre().equals("usuario")) {
				usuario =Integer.valueOf(p.getValor());
			} else if(p.getNombre().equals("mensajes")) {
				mensajes = p.getValor();
			}
		}
		
		ci.setUsuario(usuarios.get(usuario));
		
		
		int ini = 0;
		for(int i = 0; i < mensajes.length(); i++) {
			if(mensajes.charAt(i) == ' ') {
				ci.addMensaje(this.mensajes.get(Integer.valueOf(mensajes.substring(ini, i))));
				ini = i + 1;
			}
		}
	}

	private void updateGrupo(Grupo g) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(g.getId());
		
		String contactos = "";
		String mensajes = "";
		
		for(Propiedad p : eGrupo.getPropiedades()) {
			if(p.getNombre().equals("contactos")) {
				contactos = p.getValor();
			} else if(p.getNombre().equals("admin")) {
				g.setAdmin(usuarios.get(Integer.valueOf(p.getValor())));
			} else if(p.getNombre().equals("mensajes")) {
				mensajes = p.getValor();
			}
		}
		
		int ini = 0;
		for(int i = 0; i < contactos.length(); i++) {
			if(contactos.charAt(i) == ' ') {
				g.addContacto(contactosIndividuales.get(Integer.valueOf(contactos.substring(ini, i))));
				ini = i + 1;
			}
		}
		
		ini = 0;
		for(int i = 0; i < mensajes.length(); i++) {
			if(mensajes.charAt(i) == ' ') {
				g.addMensaje(this.mensajes.get(Integer.valueOf(mensajes.substring(ini, i))));
				ini = i + 1;
			}
		}
	}
	
	private void updateMensaje(Mensaje m) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(m.getId());
		
		for(Propiedad p : eMensaje.getPropiedades()) {
			if(p.getNombre().equals("emisor")) {
				m.setEmisor(usuarios.get(Integer.valueOf(p.getValor())));
			} else if(p.getNombre().equals("receptor")) {
				int idContacto = Integer.valueOf(p.getValor());
				if(contactosIndividuales.containsKey(idContacto))
					m.setReceptor(contactosIndividuales.get(idContacto));
				else m.setReceptor(grupos.get(idContacto));
			}
		}
	}

	
	public Collection<Usuario> getUsuarios(){
		return usuarios.values();
	}

}
