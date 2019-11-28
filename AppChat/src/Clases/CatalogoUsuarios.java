package Clases;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Clases.DAO.FactoriaDAO;

public class CatalogoUsuarios {
	private static CatalogoUsuarios unicaInstancia;
	private FactoriaDAO factoria;
	
	private HashMap<Integer, Usuario> usuariosPorID;
	private HashMap<String, Usuario> usuariosPorLogin;
	
	public static CatalogoUsuarios getUnicaInstancia( ) {
		if(unicaInstancia == null) unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}
	
	private CatalogoUsuarios() {
		usuariosPorID = new HashMap<Integer, Usuario>();
		usuariosPorLogin = new HashMap<String, Usuario>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			List<Usuario> listaUsuarios =
					factoria.getUsuarioDAO().getAll();
			for(Usuario usuario : listaUsuarios) {
				usuariosPorID.put(usuario.getIdUsuario(), usuario);
				usuariosPorLogin.put(usuario.getUsuario(), usuario);
			}
		} catch(DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuariosPorLogin.values());
	}
	
	public Usuario getUsuario(String login) {
		return usuariosPorLogin.get(login);
	}
	
	public Usuario getUsuario(int id) {
		return usuariosPorID.get(id);
	}
	
	public void addUsuario(Usuario usuario) {
		usuariosPorID.put(usuario.getIdUsuario(), usuario);
		usuariosPorLogin.put(usuario.getUsuario(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuariosPorID.remove(usuario.getIdUsuario());
		usuariosPorLogin.remove(usuario.getUsuario());
	}
}
