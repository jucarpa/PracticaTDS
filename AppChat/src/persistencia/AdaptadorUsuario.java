package persistencia;

import java.awt.Image;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

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
			eUsuario = servPersistencia.recuperarEntidad(usuario.getIdUsuario());
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
		// Registrar GruposAdmin
		for (Grupo g : usuario.getGruposAdmin()) {
			aG.registrarGrupo(g);
		}
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		aE.registrarEstado(usuario.getEstado());
		// Crear Entidad Usuario

		eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("fechaNacimiento", usuario.getFechaNacimiento().toString()),
				new Propiedad("movil", String.valueOf(usuario.getMovil())), new Propiedad("email", usuario.getEmail()),
				new Propiedad("usuario", usuario.getUsuario()), new Propiedad("contraseña", usuario.getContraseña()),
				new Propiedad("saludo", usuario.getSaludo()), new Propiedad("imagen", usuario.getImagenUrl()),
				new Propiedad("premium", String.valueOf(usuario.isPremium())),
				new Propiedad("estado", String.valueOf(usuario.getEstado().getId())),
				new Propiedad("contactos", obtenerIDContactos(usuario.getContactos())),
				new Propiedad("gruposAdmin", obtenerIDGruposAdmin(usuario.getGruposAdmin())))));

		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setIdUsuario(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		AdaptadorContactoIndividual aci = AdaptadorContactoIndividual.getUnicaInstancia();
		for (Contacto c : usuario.getContactos()) {
			if (c.getClass() == ContactoIndividual.class) {
				aci.borrarContactoIndividual((ContactoIndividual) c);
			}
		}
		AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
		for (Grupo g : usuario.getGruposAdmin()) {
			aG.borrarGrupo(g);
		}
		
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		aE.borrarEstado(usuario.getEstado());
		eUsuario = servPersistencia.recuperarEntidad(usuario.getIdUsuario());
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getIdUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fechaNacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fechaNacimiento", usuario.getFechaNacimiento().toString());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "movil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "movil", String.valueOf(usuario.getMovil()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "usuario");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "usuario", usuario.getUsuario());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contraseña");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contraseña", usuario.getContraseña());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImagenUrl());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "premium");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "premium", String.valueOf(usuario.isPremium()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "estado");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "estado", String.valueOf(usuario.getEstado().getId()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", obtenerIDContactos(usuario.getContactos()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "gruposAdmin");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "gruposAdmin",
				obtenerIDGruposAdmin(usuario.getGruposAdmin()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "email");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "email", usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "saludo");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "saludo", usuario.getSaludo());
	}

	public Usuario recuperarUsuario(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		// Recuperar Propiedades Que no Son Objetos

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		LocalDate fechaNacimiento = LocalDate
				.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		int movil = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "movil"));
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		String contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		boolean premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));

		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, login, contraseña, imagen, premium, email);
		usuario.setIdUsuario(id);
		usuario.setSaludo(saludo);

		// Añadimos el Objeto a Pool
		PoolDAO.getUnicaInstancia().addObjeto(id, usuario);
		// Recuperamos las Propiedades que son Objetos de sus Adaptadores
		List<Contacto> contactos = obtenerContactosDeId(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		List<Grupo> gruposAdmin = obtenerGruposAdminDeId(
				servPersistencia.recuperarPropiedadEntidad(eUsuario, "gruposAdmin"));

		usuario.setContactos(contactos);
		usuario.setGruposAdmin(gruposAdmin);
		AdaptadorEstado aE = AdaptadorEstado.getUnicaInstancia();
		Estado e = aE.recuperarEstado(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "estado")));
		usuario.setEstado(e);
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

	private String obtenerIDGruposAdmin(List<Grupo> gruposAdmin) {
		String sol = "";
		for (Grupo g : gruposAdmin) {
			sol = sol + g.getId() + " ";
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

	private List<Grupo> obtenerGruposAdminDeId(String gruposAdminID) {
		List<Grupo> gruposAdmin = new LinkedList<Grupo>();
		StringTokenizer strTok = null;
		try {
			strTok = new StringTokenizer(gruposAdminID, " ");
		} catch (NullPointerException e) {
			return gruposAdmin;
		}
		AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			int id = Integer.valueOf((String) strTok.nextElement());
			gruposAdmin.add(aG.recuperarGrupo(id));
		}
		return gruposAdmin;
	}
}
