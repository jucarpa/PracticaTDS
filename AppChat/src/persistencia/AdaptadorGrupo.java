package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupo implements IAdaptadorGrupoDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupo unicaInstancia;

	public static AdaptadorGrupo getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorGrupo();
		else
			return unicaInstancia;
	}

	private AdaptadorGrupo() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarGrupo(Grupo grupo) {
		Entidad eGrupo;
		boolean existe = true;
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;
		AdaptadorMensaje aM = AdaptadorMensaje.getUnicaInstancia();
		for (Mensaje m : grupo.getMensajes()) {
			aM.registrarMensaje(m);
		}

		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		aU.registrarUsuario(grupo.getAdmin());

		AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
		for (ContactoIndividual c : grupo.getContactos()) {
			aCI.registrarContactoIndividual(c);
		}

		eGrupo = new Entidad();
		eGrupo.setNombre("Grupo");
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", grupo.getNombre()),
				new Propiedad("admin", String.valueOf(grupo.getAdmin().getIdUsuario())),
				new Propiedad("mensajes", obtenerIdMensajes(grupo.getMensajes())),
				new Propiedad("contactos", obtenerIdContactos(grupo.getContactos())))));
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		grupo.setId(eGrupo.getId());

	}

	public void borrarGrupo(Grupo grupo) {
		Entidad eGrupo;
		AdaptadorMensaje aM = AdaptadorMensaje.getUnicaInstancia();
		for (Mensaje m : grupo.getMensajes()) {
			aM.borrarMensaje(m);
		}

		AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
		eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		servPersistencia.borrarEntidad(eGrupo);
	}

	public void modificarGrupo(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "nombre");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "nombre", grupo.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "admin");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "admin", String.valueOf(grupo.getAdmin().getIdUsuario()));
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "mensajes", obtenerIdMensajes(grupo.getMensajes()));
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "contactos");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "contactos", obtenerIdContactos(grupo.getContactos()));
	}

	public Grupo recuperarGrupo(int codigo) {
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Grupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		Entidad eGrupo = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre");
		Grupo grupo = new Grupo(nombre);

		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);
		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		int codAdmin = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eGrupo, "admin"));
		grupo.setId(codigo);
		Usuario admin = aU.recuperarUsuario(codAdmin);
		grupo.setAdmin(admin);

		List<ContactoIndividual> contactos = obtenerContactosDesdeCodigo(
				servPersistencia.recuperarPropiedadEntidad(eGrupo, "contactos"));
		grupo.setContactos(contactos);

		List<Mensaje> mensajes = obtenerMensajesDesdeCodigo(
				servPersistencia.recuperarPropiedadEntidad(eGrupo, "mensajes"));
		grupo.setMensajes(mensajes);

		return grupo;
	}

	public List<Grupo> recuperarTodosGrupos() {
		List<Grupo> grupos = new LinkedList<Grupo>();
		List<Entidad> eGrupos = servPersistencia.recuperarEntidades("Grupo");
		for (Entidad eG : eGrupos) {
			grupos.add(recuperarGrupo(eG.getId()));
		}
		return grupos;
	}

	public String obtenerIdMensajes(List<Mensaje> mensajes) {
		String sol = "";
		for (Mensaje m : mensajes) {
			sol += m.getId() + " ";
		}
		return sol.trim();
	}

	public String obtenerIdContactos(List<ContactoIndividual> contactos) {
		String sol = "";
		for (ContactoIndividual c : contactos) {
			sol += c.getId() + " ";
		}
		return sol.trim();
	}

	public List<Mensaje> obtenerMensajesDesdeCodigo(String mensajesCod) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(mensajesCod, " ");
		AdaptadorMensaje aM = AdaptadorMensaje.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			mensajes.add(aM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return mensajes;
	}

	public List<ContactoIndividual> obtenerContactosDesdeCodigo(String contactosCod) {
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(contactosCod, " ");
		AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(aCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

}
