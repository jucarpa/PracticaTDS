package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	private Map<String, Usuario> usuarios;
	private static CatalogoUsuarios unicaInstancia;

	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	private CatalogoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarios = new HashMap<String, Usuario>();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public static CatalogoUsuarios getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}

	/* devuelve todos los clientes */
	public List<Usuario> getUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u : usuarios.values())
			lista.add(u);
		return lista;
	}

	public Usuario getUsuario(int movil) {
		for (Usuario u : usuarios.values()) {
			if (u.getMovil() == movil)
				return u;
		}
		return null;
	}
	
	public Usuario getUsuario(String login) {
		return usuarios.get(login);
	}

	public void addUsuario(Usuario usuario) {
		usuarios.put(usuario.getUsuario(), usuario);
	}
	
	//Creamos desde aqui los Usuarios para no romper los patrones GRASP
	public Usuario crearUsuario(String nombre, Date fechaNacimiento,
			int movil, String login, String contrasenya, String email)  {
		Usuario sol = new Usuario(nombre, fechaNacimiento, movil, login, contrasenya, email);
		usuarios.put(sol.getUsuario(), sol);
		return sol;
	}
	
	
	public void removeUsuario(Usuario usuario) {
		usuarios.remove(usuario.getUsuario());
	}

	/* Recupera todos los clientes para trabajar con ellos en memoria */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario usuario : usuariosBD)
			usuarios.put(usuario.getUsuario(), usuario);
	}
	
	public void update() {
		unicaInstancia = new CatalogoUsuarios();
	}
	
	public boolean existeUsuario(String login) {
		return usuarios.containsKey(login);
	}
	
	public boolean existeUsuario(int movil) {
		return null != usuarios.values().stream()
			.filter(u -> u.esMovil(movil))
			.findAny().orElse(null);
	}
	
	public Usuario login(String login, String pass) {
		Usuario sol = usuarios.get(login);
		if(sol.isPassword(pass))
			return sol;
		return null;
	}
	
	
}
