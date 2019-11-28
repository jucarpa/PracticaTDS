package Clases.Manejadores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Clases.*;
import beans.Entidad;
import beans.Propiedad;
import javafx.scene.image.Image;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class ManejadorBBDD {
	private ServicioPersistencia servicioPersistencia = null;
	
	
	public ManejadorBBDD() {
		servicioPersistencia = 
				FactoriaServicioPersistencia.getInstance().getServicioPersistencia();		 
		
		
	}
	
	public int crearUsuarioBBDD(String nombre, LocalDate fechaNacimiento, int movil, String usuario, String contraseña) {
		Entidad entidad = new Entidad();
		entidad.setNombre("Usuario");
		entidad.setPropiedades(new
				ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", nombre),
						new Propiedad("fechaNacimiento", fechaNacimiento.toString()),
						new Propiedad("movil", String.valueOf(movil)),
						new Propiedad("usuario", usuario),
						new Propiedad("contraseña", contraseña),
						new Propiedad("imagen", ""),
						new Propiedad("premium", "false"),
						new Propiedad("estado", ""),
						new Propiedad("contactos", ""),
						new Propiedad("gruposAdmin", ""))));
		entidad = servicioPersistencia.registrarEntidad(entidad);
		return entidad.getId();
	}
	public int crearCIBBDD(String nombre, int movil, int idUsuario) {
Entidad eci = new Entidad();
		
		eci.setNombre("ContactoIndividual");
		eci.setPropiedades(new
				ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre",nombre),
						new Propiedad("movil", String.valueOf(movil)),
						new Propiedad("usuario", String.valueOf(idUsuario)),
						new Propiedad("mensajes", ""))));
		eci = servicioPersistencia.registrarEntidad(eci);
		
		Entidad eusuario = servicioPersistencia.recuperarEntidad(idUsuario);
		for(Propiedad p : eusuario.getPropiedades()) {
			if(p.getNombre().equals("contactos")) {
				String saux = p.getValor();
				saux = saux + eci.getId() + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(eusuario);
		return eci.getId();
	}
	public int crearGrupoBBDD(String nombre, int idAdmin) {
		Entidad eG = new Entidad();
		eG.setNombre("Grupo");
		eG.setPropiedades(new
				ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", nombre),
						new Propiedad("contactos", String.valueOf(idAdmin) + " "),
						new Propiedad("admin", String.valueOf(idAdmin)),
						new Propiedad("mensajes", ""))));
		
		eG = servicioPersistencia.registrarEntidad(eG);
		Entidad eAdmin = servicioPersistencia.recuperarEntidad(idAdmin);
		for(Propiedad p : eAdmin.getPropiedades()) {
			if(p.getNombre().equals("gruposAdmin")) {
				String saux = p.getValor();
				saux = saux + eG.getId() + " ";
				p.setValor(saux);
			} else if(p.getNombre().equals("contactos")) {
				String saux = p.getValor();
				saux = saux + eG.getId() + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(eAdmin);
		return eG.getId();
		
	}
	public int crearMensajeBBDD(String texto, LocalTime hora, int emoticono, int idEmisor, int idReceptor) {
		Entidad eM = new Entidad();
		eM.setNombre("Mensaje");
		eM.setPropiedades(new
				ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("texto", texto),
						new Propiedad("hora", hora.toString()),
						new Propiedad("emoticono", String.valueOf(emoticono)),
						new Propiedad("emisor", String.valueOf(idEmisor)),
						new Propiedad("receptor", String.valueOf(idReceptor)))));
		eM = servicioPersistencia.registrarEntidad(eM);
		
		Entidad eR = servicioPersistencia.recuperarEntidad(idReceptor);
		for(Propiedad p: eR.getPropiedades()) {
			if(p.getNombre().equals("mensajes")) {
				String saux = p.getValor();
				saux = saux + eM.getId() + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(eR);
		return eM.getId();
	}
	public void anyadirContactoGBBDD(int idUsuario, int idGrupo) {
		Entidad eUsuario = servicioPersistencia.recuperarEntidad(idUsuario);
		for(Propiedad p: eUsuario.getPropiedades()) {
			if(p.getNombre().equals("contactos")) {
				String saux = p.getValor();
				saux = saux + idGrupo + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(eUsuario);
		
		Entidad eGrupo = servicioPersistencia.recuperarEntidad(idGrupo);
		for(Propiedad p: eGrupo.getPropiedades()) {
			if(p.getNombre().equals("contactos")) {
				String saux = p.getValor();
				saux = saux + idUsuario + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(eGrupo);
	}
	
	
	public Usuario getUsuario(int idUsuario) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idUsuario);
		String nombre = "";
		LocalDate fechaNacimiento = null;
		int movil = 0;
		String usuario = "";
		String contraseña = "";
		String imagen = "";
		boolean premium = true;
		String contactos = "";
		String gruposAdmin = "";
		
		for(Propiedad p : entidad.getPropiedades()) {
			if(p.getNombre().equals("nombre")) {
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
				imagen = p.getValor();
			} else if(p.getNombre().equals("premium")) {
				if(p.getValor().equals("false"))
					premium = false;
			} else if(p.getNombre().equals("contactos")) {
				contactos = p.getValor();
			} else if(p.getNombre().equals("gruposAdmin")) {
				gruposAdmin = p.getValor();
			}  
		}
		
		Usuario sol = new Usuario(nombre,fechaNacimiento, movil,usuario, contraseña, idUsuario);
		sol.setPremium(premium);
		if(!imagen.equals("")) sol.setImagen(new Image(imagen)); 
		
		int ini = 0;
		for(int i = 0; i < contactos.length(); i++) {
			if(contactos.charAt(i) == ' ') {
				int idAux = Integer.valueOf(contactos.substring(ini, i));
				Entidad eAux = servicioPersistencia.recuperarEntidad(idAux);
				if(eAux.getNombre().equals("Grupo")) 
					sol.addContacto(getGrupo(idAux, sol));
				else sol.addContacto(getContactoIndividual(idAux, sol));
				ini = i+1;
			}
		}
		ini = 0;
		for(int i = 0; i < gruposAdmin.length(); i++ ) {
			if(gruposAdmin.charAt(i) == ' ') {
				sol.addGrupoAdmin(getGrupo(Integer.valueOf(gruposAdmin.substring(ini, i)), sol));
				ini = i+1;
			}
		}
		return sol;
	}
	public ContactoIndividual getContactoIndividual(int idCI, Usuario u) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idCI);
		String nombre = "";
		int movil = 0;
		String mensajes = "";
		for(Propiedad p : entidad.getPropiedades()) {
			if(p.getNombre().equals("nombre")) {
				nombre = p.getValor();
			} else if(p.getNombre().equals("movil")) {
				movil = Integer.valueOf(p.getValor());
			} else if(p.getNombre().equals("mensajes")) {
				mensajes = p.getValor();
			}
		}
		ContactoIndividual sol = new ContactoIndividual(nombre,movil,u,idCI);
		
		int ini = 0;
		for(int i = 0; i < mensajes.length(); i++) {
			if(mensajes.charAt(i) == ' ') {
				sol.addMensaje(getMensaje(Integer.valueOf(mensajes.substring(ini, i)), u));
				ini = i + 1;
			}
		}
		return sol;
	}
	public Grupo getGrupo(int idG, Usuario u) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idG);
		String nombre = "";
		Usuario admin = null;
		String contactos = "";
		String mensajes = "";
		for(Propiedad p : entidad.getPropiedades()) {
			if(p.getNombre().equals("nombre")) {
				nombre = p.getValor();
			} else if(p.getNombre().equals("admin")) {
				admin = getUsuario(Integer.valueOf(p.getValor()));
			} else if(p.getNombre().equals("contactos")) {
				contactos = p.getValor();
			} else if(p.getNombre().equals("mensajes")) {
				mensajes = p.getValor();
			}
		}
		Grupo sol = new Grupo(nombre, admin, idG);
		
		int ini = 0;
		for(int i = 0; i < contactos.length(); i++) {
			if(contactos.charAt(i) == ' ') {
				Usuario aux = getUsuario(Integer.valueOf(contactos.substring(ini, i)));
				for(Contacto c : u.getContactos()) {
					if(c.getClass() == ContactoIndividual.class) {
						ContactoIndividual ci = (ContactoIndividual) c;
						if(ci.getMovil() == aux.getMovil()) 
							sol.addContacto(ci);
					}
				}
			}
		}
		
		ini = 0;
		for(int i = 0; i < mensajes.length(); i++) {
			if(mensajes.charAt(i) == ' ') {
				sol.addMensaje(getMensaje(Integer.valueOf(mensajes.substring(ini, i)), u));
				ini = i + 1;
			}
		}
		
		return sol;
		
	}
	public Mensaje getMensaje(int idM, Usuario u) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idM);
		String texto = "";
		LocalTime hora = null;
		int emoticono = 0;
		Usuario emisor = null;
		int receptor = 0;
		for(Propiedad p : entidad.getPropiedades()) {
			if(p.getNombre().equals("texto")) {
				texto = p.getValor();
			} else if(p.getNombre().equals("hora")) {
				hora = LocalTime.parse(p.getValor());
			} else if(p.getNombre().equals("emoticono")) {
				emoticono = Integer.valueOf(p.getValor());
			} else if(p.getNombre().equals("emisor")) {
				emisor = getUsuario(Integer.valueOf(p.getValor()));
			} else if(p.getNombre().equals("receptor")) {
				receptor = Integer.valueOf(p.getValor());
			}
		}
		
		entidad = servicioPersistencia.recuperarEntidad(receptor);
		if(entidad.getNombre().equals("Grupo")) {
			return new Mensaje(texto, hora,emoticono, getGrupo(receptor, u), emisor, idM);
		} return new Mensaje(texto, hora,emoticono, getContactoIndividual(receptor, u), emisor, idM);
		
	}

	
	public void modificarPropiedad(int idEntidad, String nombrePropiedad, String valor) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idEntidad);
		for(Propiedad p: entidad.getPropiedades()) {
			if(p.getNombre().equals(nombrePropiedad)) {
				p.setValor(valor);
			}
		}
		servicioPersistencia.modificarEntidad(entidad);
	}
	public void anyadirPropiedad(int idEntidad, String nombrePropiedad, String valor) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idEntidad);
		servicioPersistencia.anadirPropiedadEntidad(entidad, nombrePropiedad, valor);
	}
	public void anyadirValorPropiedad(int idEntidad, String nombrePropiedad, String valor) {
		Entidad entidad = servicioPersistencia.recuperarEntidad(idEntidad);
		for(Propiedad p: entidad.getPropiedades()) {
			if(p.getNombre().equals(nombrePropiedad)) {
				String saux = p.getValor();
				saux = saux + valor + " ";
				p.setValor(saux);
			}
		}
		servicioPersistencia.modificarEntidad(entidad);
	}
	
	public Map<String, Usuario>getUsuariosInit() {
		Map<String, Usuario> usuariosInit = new HashMap<String, Usuario>();
		for(Entidad e: servicioPersistencia.recuperarEntidades()) {
			if(e.getNombre().equals("Usuario")) {
				String usuario = "";
				String contraseña = "";
				for(Propiedad p : e.getPropiedades()) {
					if(p.getNombre().equals("usuario")) {
						usuario = p.getValor();
					} else if(p.getNombre().equals("contraseña")) {
						contraseña = p.getValor();
					} 
				}
				usuariosInit.put(usuario, new Usuario(usuario, contraseña, e.getId()));
			}
		}
		return usuariosInit;
	}
	public Usuario initProgramaUsuario(int idUsuario) {
		return getUsuario(idUsuario);
	}
	

}
