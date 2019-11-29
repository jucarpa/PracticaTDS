package persistencia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensaje implements IAdaptadorMensajeDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensaje unicaInstancia;
	
	public static AdaptadorMensaje getUnicaInstancia() {
		if (unicaInstancia == null)
		return new AdaptadorMensaje();
		else
		return unicaInstancia;
	}
	
	private AdaptadorMensaje() {
		servPersistencia =
		FactoriaServicioPersistencia.getInstance().
		getServicioPersistencia();
	}
	
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje;
		boolean existe = true;
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		} catch(NullPointerException e) {
			existe = false;
		}
		if(existe) return;
		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		aU.registrarUsuario(mensaje.getEmisor());
		if(mensaje.getReceptor().getClass() == Grupo.class) {
			AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
			aG.registrarGrupo((Grupo) mensaje.getReceptor());
		} else {
			AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
			aCI.registrarContactoIndividual((ContactoIndividual) mensaje.getReceptor());
		}
		
		
		eMensaje = new Entidad();
		eMensaje.setNombre("Mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("hora", mensaje.getHora().toString()),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticon())),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getIdUsuario())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getId())))));
	}
	public void borrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		servPersistencia.borrarEntidad(eMensaje);
		
	}
	public void modificarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "texto");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "texto", mensaje.getTexto());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "hora");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "hora", mensaje.getHora().toString());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emoticono");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emoticono", String.valueOf(mensaje.getEmoticon()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emisor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emisor", String.valueOf(mensaje.getEmisor().getIdUsuario()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "receptor");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "receptor", String.valueOf(mensaje.getReceptor().getId()));
	}
	public Mensaje recuperarMensaje(int codigo) {
		if(PoolDAO.getUnicaInstancia().contiene(codigo)) 
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		LocalTime hora = LocalTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora"));
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		
		Mensaje mensaje = new Mensaje(texto, hora, emoticono);
		PoolDAO.getUnicaInstancia().addObjeto(codigo, mensaje);
		
		AdaptadorUsuario aU = AdaptadorUsuario.getUnicaInstancia();
		Usuario usuario = aU.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor")));
		mensaje.setEmisor(usuario);
		
		int idReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		
		if(servPersistencia.recuperarEntidad(idReceptor).getNombre().equals("Grupo")) {
			AdaptadorGrupo aG = AdaptadorGrupo.getUnicaInstancia();
			Grupo receptor = aG.recuperarGrupo(idReceptor);
			mensaje.setReceptor(receptor);
		} else {
			AdaptadorContactoIndividual aCI = AdaptadorContactoIndividual.getUnicaInstancia();
			ContactoIndividual receptor = aCI.recuperarContactoIndividual(idReceptor);
			mensaje.setReceptor(receptor);
		
		}
		return mensaje;
	}
	public List<Mensaje> recuperarTodosMensajes(){
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> eMensaje = servPersistencia.recuperarEntidades("Mensaje");
		for(Entidad e : eMensaje) {
			mensajes.add(recuperarMensaje(e.getId()));
		}
		return mensajes;
	}
}
