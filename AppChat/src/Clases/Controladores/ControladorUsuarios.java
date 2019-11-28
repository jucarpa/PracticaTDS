package Clases.Controladores;

import java.time.LocalDate;

import Clases.CatalogoUsuarios;
import Clases.Usuario;
import Clases.DAO.FactoriaDAO;
import Clases.DAO.UsuarioDAO;

public final class ControladorUsuarios {
	private Usuario usuarioActual;
	private static ControladorUsuarios unicaInstancia;
	private FactoriaDAO factoria;
	
	private ControladorUsuarios() {
		usuarioActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public static ControladorUsuarios getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new ControladorUsuarios();
		return unicaInstancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login)!=null;
	}
	
	public boolean loginUsuario(String nombre,String password) {
		Usuario usuario =
				CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getContraseña().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre,LocalDate fechaNacimiento, int movil, String login, String password) {
		if (esUsuarioRegistrado(login)) return false;
		
		Usuario usuario = new Usuario(nombre,fechaNacimiento,movil,login,password);
		/*Adaptador DAO para almacenar el nuevo Usuario en la BD*/
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.create(usuario);
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
	
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario())) return false;
		
		/*Adaptador DAO para borrar el Usuario de la BD*/
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.delete(usuario);
		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
}
