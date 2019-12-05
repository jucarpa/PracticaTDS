package controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorCI;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private Usuario usuarioActual;

	private ControladorAppChat() {
		inicializarAdaptadores();
		inicializarCatalogos();
	}

	public static ControladorAppChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}

	public void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorCI = factoria.getCIDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	public void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	public void registrarUsuario(String nombre, LocalDate fechaNacimiento, int movil, String login, String contraseña,
			String email) {
		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, login, contraseña, email);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		usuarioActual = usuario;
	}

	public boolean loginUsuario(String login, String contrasena) {
		Usuario u = catalogoUsuarios.getUsuario(login);
		if (u.getContraseña().equals(contrasena)) {
			usuarioActual = u;
			return true;
		} else
			return false;
	}

	public void registrarGrupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		Grupo grupo = new Grupo(nombre, contactos, usuarioActual);
		adaptadorGrupo.registrarGrupo(grupo);
		usuarioActual.addContacto(grupo);
		usuarioActual.addGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		for(ContactoIndividual c : grupo.getContactos()) {
			Usuario aux = c.getUsuario();
			aux.addContacto(grupo);
			adaptadorUsuario.modificarUsuario(aux);
		}
	}

	public ContactoIndividual registrarContactoIndividual(String nombre, int movil) {
		Usuario u = catalogoUsuarios.getUsuario(movil);
		ContactoIndividual ci = new ContactoIndividual(nombre, movil,u);
		adaptadorCI.registrarContactoIndividual(ci);
		usuarioActual.addContacto(ci);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return ci;
	}
	
	public boolean existeContacto(int movil) {
		return usuarioActual.getCIPorNumero(movil) != null;
	}

	public void registrarMensaje(String texto, int emoticono, int receptor) {
		Grupo g = adaptadorGrupo.recuperarGrupo(receptor);
		Mensaje mensaje = null;
		if (g == null) {
			ContactoIndividual ci = adaptadorCI.recuperarContactoIndividual(receptor);
			mensaje = new Mensaje(texto, LocalTime.now(), emoticono, usuarioActual, ci);
			adaptadorMensaje.registrarMensaje(mensaje);
			ci.addMensaje(mensaje);
			adaptadorCI.modificarContactoIndividual(ci);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		} else {
			mensaje = new Mensaje(texto, LocalTime.now(), emoticono, usuarioActual, g);
			g.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo(g);
			for (ContactoIndividual ci : g.getContactos()) {
				adaptadorUsuario.modificarUsuario(ci.getUsuario());
			}
		}
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public void modificarContactoIndividual(ContactoIndividual ci) {
		adaptadorCI.modificarContactoIndividual(ci);;
	}
	
	public void modificarGrupo(Grupo g, List<ContactoIndividual> contactosEliminados) {
		for(ContactoIndividual c : g.getContactos()) {
			for(ContactoIndividual cE : contactosEliminados) {
				if(c.getId() == cE.getId()) {
					cE.getUsuario().eliminarContacto(cE);
					adaptadorUsuario.modificarUsuario(cE.getUsuario());
				}
			}
		}
		adaptadorGrupo.modificarGrupo(g);
	}
	public void recibirMensajeGrupo(Mensaje m) {
	}

	public void recibirMensajeCI(Mensaje m) {
	}

	public void eliminarGrupo(Grupo g) {
		for(ContactoIndividual c : g.getContactos()) {
			Usuario aux = c.getUsuario();
			aux.eliminarContacto(g);
			adaptadorUsuario.modificarUsuario(aux);
		}
		usuarioActual.eliminarContacto(g);
		usuarioActual.eliminarGrupoAdmin(g);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		adaptadorGrupo.borrarGrupo(g);
	}

	public void eliminarContactoIndividual(ContactoIndividual ci) {
		usuarioActual.eliminarContacto(ci);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		adaptadorCI.borrarContactoIndividual(ci);
	}
	
	public void eliminarMensajes(Contacto c) {
		for(Mensaje m : c.getMensajes()) {
			adaptadorMensaje.borrarMensaje(m);
		}
		c.setMensajes(new LinkedList<Mensaje>());
		if(c.getClass() == Grupo.class)
			adaptadorGrupo.modificarGrupo((Grupo) c);
		else
			adaptadorCI.modificarContactoIndividual((ContactoIndividual) c);
	}
	public void salirGrupo(Grupo g) {
	}

	public boolean existeUsuario(String login) {
		return catalogoUsuarios.getUsuario(login) != null;

	}

}
