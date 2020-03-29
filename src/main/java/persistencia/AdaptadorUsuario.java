package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Estado;
import modelo.Grupo;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuario implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuario unicaInstancia;

	public static AdaptadorUsuario getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorUsuario();
		return unicaInstancia;
	}

	private AdaptadorUsuario() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			existe = false;
		}

		if (existe)
			return;

		// Registramos Primero Los Atributos Que Son Objetos
		// Registrar Contactos
		AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
		for (Contacto c : usuario.getContactos()) {
			if (c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				aG.registrarGrupo(aux);
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				aCI.registrarContactoIndividual(aux);
			}
		}
		
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		if(usuario.getEstado() != null) {
			Estado aux = usuario.getEstado();
			aE.registrarEstado(aux);
		}
		// Crear Entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("fechaNacimiento", new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(usuario.getFechaNacimiento())),
				new Propiedad("movil", String.valueOf(usuario.getMovil())), 
				new Propiedad("email", usuario.getEmail()),
				new Propiedad("usuario", usuario.getUsuario()), 
				new Propiedad("contrasenya", usuario.getPassword()),
				new Propiedad("saludo", usuario.getSaludo()), 
				new Propiedad("imagen", usuario.getImagen()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("contactos", obtenerIDContactos(usuario.getContactos())))));
				new Propiedad("estado", obtenerIDEstado(usuario.getEstado()));
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		AdaptadorContactoIndividual aci = AdaptadorContactoIndividual.getUnicaInstancia();
		for (Contacto c : usuario.getContactos()) {
			if (c.getClass() == ContactoIndividual.class) {
				aci.borrarContactoIndividual((ContactoIndividual) c);
			}
		}
		
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		aE.borrarEstado(usuario.getEstado());
		
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fechaNacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fechaNacimiento", new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(usuario.getFechaNacimiento()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "movil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "movil", String.valueOf(usuario.getMovil()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "usuario");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "usuario", usuario.getUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contrasenya");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contrasenya", usuario.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImagen());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "premium");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "premium", String.valueOf(usuario.isPremium()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", obtenerIDContactos(usuario.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "email");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "email", usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "saludo");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "saludo", usuario.getSaludo());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "estado");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "estado", obtenerIDEstado(usuario.getEstado()));
	}

	public Usuario recuperarUsuario(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		// Recuperar Propiedades Que no Son Objetos

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		int movil = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "movil"));
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		String contrasenya = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasenya");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		boolean premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, login, contrasenya, imagen, premium, email);
		usuario.setId(id);
		usuario.setSaludo(saludo);

		// A�adimos el Objeto a Pool
		PoolDAO.getUnicaInstancia().addObjeto(id, usuario);
		// Recuperamos las Propiedades que son Objetos de sus Adaptadores
		List<Contacto> contactos = obtenerContactosDeId(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));

		usuario.setContactos(contactos);
		
		String est = servPersistencia.recuperarPropiedadEntidad(eUsuario, "estado");
		if(est!= null && !est.equals("")) {
			Estado estado = obtenerEstadoDeID(est);
			usuario.setEstado(estado);
		}
		// Devolver Objeto
		return usuario;

	}

	public List<Usuario> recuperarTodosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("Usuario");
		for (Entidad eU : eUsuarios) {
			usuarios.add(recuperarUsuario(eU.getId()));
		}
		return usuarios;
	}

	// Funciones Auxiliares;

	private String obtenerIDContactos(List<Contacto> contactos) {
		String sol = "";
		for (Contacto c : contactos) {
			if (c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				sol = sol + aux.getId() + " ";
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				sol = sol + aux.getId() + " ";
			}
		}
		return sol.trim();
	}

	private List<Contacto> obtenerContactosDeId(String contactosID) {
		List<Contacto> contactos = new LinkedList<Contacto>();
		StringTokenizer strTok = null;
		try {
			strTok = new StringTokenizer(contactosID, " ");
		} catch (NullPointerException e) {
			return contactos;
		}
		AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			int id = Integer.valueOf((String) strTok.nextElement());
			if (servPersistencia.recuperarEntidad(id).getNombre().equals("Grupo"))
				contactos.add(aG.recuperarGrupo(id));
			else
				contactos.add(aCI.recuperarContactoIndividual(id));
		}
		return contactos;
	}
	public void update() {
		unicaInstancia = new AdaptadorUsuario();
		PoolDAO.getUnicaInstancia().update();
	}
	
	private String obtenerIDEstado(Estado estado) {
		if(estado == null)
			return "";
		return estado.getId() + "";
	}
	
	private Estado obtenerEstadoDeID(String ID) {
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		if(ID.equals(""))
			return null;
		int id = Integer.valueOf(ID);
		return aE.recuperarEstado(id); 
	}
}
