package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoIndividual implements IAdaptadorContactoIndividualDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividual unicaInstancia;
	
	public static AdaptadorContactoIndividual getUnicaInstancia() {
		if (unicaInstancia == null)
		return new AdaptadorContactoIndividual();
		else
		return unicaInstancia;
	}
	
	private AdaptadorContactoIndividual() {
		servPersistencia =
		FactoriaServicioPersistencia.getInstance().
		getServicioPersistencia();
	}
	
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {
		Entidad eCI;
		boolean existe = true;
		try {
			eCI = servPersistencia.recuperarEntidad(contactoIndividual.getId());
		} catch(NullPointerException e) {
			existe =false;
		}
		if(existe) return;
		AdaptadorMensaje aM= AdaptadorMensaje.getUnicaInstancia();
		for(Mensaje m : contactoIndividual.getMensajes()) {
			aM.registrarMensaje(m);
		}
		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		aU.registrarUsuario(contactoIndividual.getUsuario());
		eCI = new Entidad();
		eCI.setNombre("ContactoIndividual");
		eCI.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", contactoIndividual.getNombre()),
				new Propiedad("movil", String.valueOf(contactoIndividual.getMovil())),
				new Propiedad("usuario", String.valueOf(contactoIndividual.getUsuario().getIdUsuario())),
				new Propiedad("mensajes", obtenerIdMensajes(contactoIndividual.getMensajes())))));
	
		eCI = servPersistencia.registrarEntidad(eCI);
		contactoIndividual.setId(eCI.getId());
	}	
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {
		Entidad eCI;
		AdaptadorMensaje aM = AdaptadorMensaje.getUnicaInstancia();
		for(Mensaje m : contactoIndividual.getMensajes()) {
			aM.borrarMensaje(m);
		}
		eCI = servPersistencia.recuperarEntidad(contactoIndividual.getId());
		servPersistencia.borrarEntidad(eCI);
	}
	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {
		Entidad eCI = servPersistencia.recuperarEntidad(contactoIndividual.getId());
		servPersistencia.eliminarPropiedadEntidad(eCI, "nombre");
		servPersistencia.anadirPropiedadEntidad(eCI, "nombre", contactoIndividual.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eCI, "movil");
		servPersistencia.anadirPropiedadEntidad(eCI, "movil", String.valueOf(contactoIndividual.getMovil()));
		servPersistencia.eliminarPropiedadEntidad(eCI, "usuario");
		servPersistencia.anadirPropiedadEntidad(eCI, "usuario", String.valueOf(contactoIndividual.getUsuario().getIdUsuario()));
		servPersistencia.eliminarPropiedadEntidad(eCI, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eCI, "mensajes", obtenerIdMensajes(contactoIndividual.getMensajes()));
	}
	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		if(PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		Entidad eCI = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eCI, "nombre");
		int movil = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eCI, "movil"));
		int codUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eCI, "usuario"));
		ContactoIndividual sol = new ContactoIndividual(nombre, movil);
		PoolDAO.getUnicaInstancia().addObjeto(codigo, sol);
		
		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		Usuario usuario = aU.recuperarUsuario(codUsuario);
		sol.setUsuario(usuario);
		
		List<Mensaje> mensajes = obtenerMensajesDesdeCodigo(
				servPersistencia.recuperarPropiedadEntidad(eCI,"mensajes"));
		sol.setMensajes((ArrayList<Mensaje>) mensajes);
		return sol;
	}
	public List<ContactoIndividual> recuperarTodosContactoIndividuales(){
		List<ContactoIndividual> cI = new LinkedList<ContactoIndividual>();
		List<Entidad> eCI = servPersistencia.recuperarEntidades("ContactoIndividual");
		for(Entidad e : eCI) {
			cI.add(recuperarContactoIndividual(e.getId()));
		}
		return cI;
	}
	
	public String obtenerIdMensajes(List<Mensaje> mensajes) {
		String sol = "";
		for(Mensaje m : mensajes) {
			sol += m.getId() + " ";
		}
		return sol.trim();
	}
	public List<Mensaje> obtenerMensajesDesdeCodigo(String mensajesCod){
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(mensajesCod, " ");
		AdaptadorMensaje aM = AdaptadorMensaje.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			mensajes.add(aM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return mensajes;
	}

}

