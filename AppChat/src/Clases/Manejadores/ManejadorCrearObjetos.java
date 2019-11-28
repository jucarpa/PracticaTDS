package Clases.Manejadores;

import java.time.LocalDate;
import java.time.LocalTime;

import Clases.*;

public class ManejadorCrearObjetos {
	
	private ManejadorBBDD manejadorBBDD = null;
	private CatalogoUsuarios usuarios;
	private Usuario usuario;
	public ManejadorCrearObjetos(CatalogoUsuarios usuarios) {
		manejadorBBDD = new ManejadorBBDD();
		this.usuarios = usuarios;
	}
	
	public Usuario addUsuario(String nombre, LocalDate fechaNacimiento, int movil, String usuario, String contraseña) {
		int id = manejadorBBDD.crearUsuarioBBDD(nombre, fechaNacimiento, movil, usuario, contraseña);
		Usuario sol = manejadorBBDD.getUsuario(id);
		usuarios.addUsuario(sol.getMovil(), sol);
		return sol;
	}
	
	public ContactoIndividual addCI(String nombre, int movil) {
		int id = manejadorBBDD.crearCIBBDD(nombre, movil, usuario.getIdUsuario());
		ContactoIndividual sol = manejadorBBDD.getContactoIndividual(id, usuario);
		usuario.addContacto(sol);
		return sol;
	}
	
	public Grupo addGrupo(String nombre) {
		int id = manejadorBBDD.crearGrupoBBDD(nombre, usuario.getIdUsuario());
		Grupo sol = manejadorBBDD.getGrupo(id, usuario);
		usuario.addContacto(sol);
		usuario.addGrupoAdmin(sol);
		return sol;
	}
	
	public Mensaje addMensaje(String texto, LocalTime hora, int emoticono, Usuario emisor, Contacto receptor) {
		int id = 0;
		if(receptor.getClass() == ContactoIndividual.class) {
		ContactoIndividual aux = (ContactoIndividual) receptor;
		id = manejadorBBDD.crearMensajeBBDD(texto, hora, emoticono, emisor.getIdUsuario(), aux.getId());
		//Hacemos llamada a Usuario de Contactoindividual Para avisar al Otro Participante
		} else {
			Grupo aux = (Grupo) receptor;
			id = manejadorBBDD.crearMensajeBBDD(texto, hora, emoticono, emisor.getIdUsuario(), aux.getId());
			//Hacemos llamada a los Usuarios de los Contactos para avisar a los Participantes
		}
		return manejadorBBDD.getMensaje(id, emisor);
	}
	
	public void anyadirCIaGrupo() {}
}
