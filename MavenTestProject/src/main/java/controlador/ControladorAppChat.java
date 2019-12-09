package controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import sun.text.UCompactIntArray;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorCI;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;


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

	public Usuario registrarUsuario(String nombre, Date fechaNacimiento, int movil, String login, String contrasenya,
			String email) {
		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, login, contrasenya, email);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return usuario;
	}

	public int loginUsuario(String login, String contrasena) {
		Usuario u = catalogoUsuarios.getUsuario(login);
		if (u.getContrasenya().equals(contrasena)) {
			return u.getMovil();
		} else
			return -1;
	}

	public Grupo registrarGrupo(String nombre, ArrayList<Integer> contactos, int movilUA) {
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
		Grupo grupo = new Grupo(nombre, usuarioActual);
		for(int movil : contactos) {
			ContactoIndividual aux = usuarioActual.getCIPorNumero(movil);
			grupo.addContacto(aux);
		}
		adaptadorGrupo.registrarGrupo(grupo);
		usuarioActual.addContacto(grupo);
		usuarioActual.addGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		for(ContactoIndividual c : grupo.getContactos()) {
			Usuario aux = c.getUsuario();
			aux.addContacto(grupo);
			adaptadorUsuario.modificarUsuario(aux);
		}
		catalogoUsuarios.addUsuario(usuarioActual);
		return grupo;
	}

	public ContactoIndividual registrarContactoIndividual(String nombre, int movil, int movilUA) {
		Usuario u = catalogoUsuarios.getUsuario(movil);
		ContactoIndividual ci = new ContactoIndividual(nombre, movil,u);
		adaptadorCI.registrarContactoIndividual(ci);
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
		usuarioActual.addContacto(ci);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		catalogoUsuarios.addUsuario(usuarioActual);
		return ci;
	}
	
	public boolean existeContacto(int movil, int movilUA) {
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
		return usuarioActual.getCIPorNumero(movil) != null;
	}

	public Mensaje registrarMensajeG(String texto, int emoticono, int receptor, int movilUA) {

		Grupo g = adaptadorGrupo.recuperarGrupo(receptor);
		Mensaje mensaje = null;
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
		mensaje = new Mensaje(texto, LocalTime.now(), emoticono, usuarioActual, g);
		adaptadorMensaje.registrarMensaje(mensaje);
		g.addMensaje(mensaje);
		adaptadorGrupo.modificarGrupo(g);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		for (ContactoIndividual ci : g.getContactos()) {
			adaptadorUsuario.modificarUsuario(ci.getUsuario());
		}
		return mensaje;
	}
	public Mensaje registrarMensajeCI(String texto, int emoticono, int movilReceptor, int movilUA) {
		
		Mensaje mensaje = null;
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
		ContactoIndividual ci = usuarioActual.getCIPorNumero(movilReceptor);
		mensaje = new Mensaje(texto, LocalTime.now(), emoticono, usuarioActual, ci);
		adaptadorMensaje.registrarMensaje(mensaje);
		ci.addMensaje(mensaje);
		adaptadorCI.modificarContactoIndividual(ci);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		Usuario usuarioCI = ci.getUsuario();
		ContactoIndividual cIUA = usuarioCI.getCIPorNumero(usuarioActual.getMovil());
		if(cIUA == null) {
			cIUA = new ContactoIndividual(String.valueOf(usuarioActual.getMovil()), usuarioActual.getMovil(), usuarioCI);
			adaptadorCI.registrarContactoIndividual(cIUA);
			usuarioCI.addContacto(cIUA);
			adaptadorUsuario.modificarUsuario(usuarioCI);
		}
		cIUA.addMensaje(mensaje);
		adaptadorCI.modificarContactoIndividual(cIUA);
		adaptadorUsuario.modificarUsuario(usuarioCI);
		
		return mensaje;
	}

	public void modificarContactoIndividual(ContactoIndividual ci) {
		adaptadorCI.modificarContactoIndividual(ci);
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

	public void eliminarGrupo(Grupo g, int movilUA) {
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
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

	public void eliminarContactoIndividual(ContactoIndividual ci, int movilUA) {
		Usuario usuarioActual = catalogoUsuarios.getUsuario(movilUA);
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

	public boolean existeUsuario(String login) {
		return catalogoUsuarios.getUsuario(login) != null;
	}
	
	public Usuario getUsuario(int movilUA) {
		return catalogoUsuarios.getUsuario(movilUA);
	}
	
	public ContactoIndividual getContactoIndividual(int movilContacto, int movilUA) {
		return catalogoUsuarios.getUsuario(movilUA).getCIPorNumero(movilContacto);
	}
	
	public Grupo getGrupo(String nombreGrupo, int movilUA) {
		return catalogoUsuarios.getUsuario(movilUA).getGrupoPorNombre(nombreGrupo);
	}
	
	public boolean realizarPago(int movilUA) {
		Usuario u = catalogoUsuarios.getUsuario(movilUA); 
		u.realizarPago();
		adaptadorUsuario.modificarUsuario(u);
		return u.isPremium();
	}

}
