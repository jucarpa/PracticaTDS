package controlador;

import java.time.LocalDate;
import java.time.LocalTime;

import modelo.CatalogoUsuarios;
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
		if(unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}
	
	public void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch(DAOException e) {
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

	public void registrarUsuario(String nombre, LocalDate fechaNacimiento, int movil, String login, String contraseña) {
		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, login, contraseña);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		usuarioActual = usuario;
	}
	
	public boolean loginUsuario(String login, String contrasena) {
		Usuario u = catalogoUsuarios.getUsuario(login);
		if(u.getContraseña().equals(contrasena)) {
			usuarioActual = u;
			return true;
		} else return false;
	}
	
	public void registrarGrupo(String nombre) {
		Grupo grupo = new Grupo(nombre, usuarioActual);
		adaptadorGrupo.registrarGrupo(grupo);
		usuarioActual.addContacto(grupo);
		usuarioActual.addGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public void registrarContactoIndividual(String nombre, int movil) {
		ContactoIndividual ci = new ContactoIndividual(nombre, movil);
		adaptadorCI.registrarContactoIndividual(ci);
		usuarioActual.addContacto(ci);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public void registrarMensaje(String texto, int emoticono, int receptor) {
		Grupo g = adaptadorGrupo.recuperarGrupo(receptor);
		Mensaje mensaje = null;
		if(g == null) {
			ContactoIndividual ci = adaptadorCI.recuperarContactoIndividual(receptor);
			mensaje = new Mensaje(texto,LocalTime.now(), emoticono, usuarioActual, ci);
			adaptadorMensaje.registrarMensaje(mensaje);
			ci.addMensaje(mensaje);
			adaptadorCI.modificarContactoIndividual(ci);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		} else {
			mensaje = new Mensaje(texto, LocalTime.now(), emoticono, usuarioActual, g);
			g.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo(g);
			for(ContactoIndividual ci : g.getContactos()) {
				adaptadorUsuario.modificarUsuario(ci.getUsuario());
			}
		}
	}
	public void recibirMensajeGrupo(Mensaje m) {}
	public void recibirMensajeCI(Mensaje m) {}
	public void eliminarGrupo(Grupo g) {}
	public void eliminarContactoIndividual(ContactoIndividual ci) {}
	public void salirGrupo(Grupo g) {}
	
	public boolean existeUsuario(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
		
	}
	
	
}
