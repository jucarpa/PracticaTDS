package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Estado;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorEstado implements IAdaptadorEstadoDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorEstado unicaInstancia;

	public static AdaptadorEstado getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorEstado();
		return unicaInstancia;
	}

	private AdaptadorEstado() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarEstado(Estado estado) {
		Entidad eEstado;
		boolean existe = true;
		try {
			eEstado = servPersistencia.recuperarEntidad(estado.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if(existe)
			return;
		
		eEstado = new Entidad();
		eEstado.setNombre("Estado");
		eEstado.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("mensaje", estado.getMensaje()),
				new Propiedad("imagen", estado.getImagenUrl()))));
	
		eEstado = servPersistencia.registrarEntidad(eEstado);
		estado.setId(eEstado.getId());
	}

	@Override
	public void borrarEstado(Estado estado) {
		Entidad eEstado = servPersistencia.recuperarEntidad(estado.getId());
		servPersistencia.borrarEntidad(eEstado);
		
	}

	@Override
	public void modificarEstado(Estado estado) {
		Entidad eEstado = servPersistencia.recuperarEntidad(estado.getId());
		servPersistencia.eliminarPropiedadEntidad(eEstado, "mensaje");
		servPersistencia.anadirPropiedadEntidad(eEstado, "mensaje", estado.getMensaje());
		servPersistencia.eliminarPropiedadEntidad(eEstado, "imagen");
		servPersistencia.anadirPropiedadEntidad(eEstado, "imagen", estado.getImagenUrl());
	}

	@Override
	public Estado recuperarEstado(int codigo) {
		Entidad eEstado = servPersistencia.recuperarEntidad(codigo);
		String mensaje = servPersistencia.recuperarPropiedadEntidad(eEstado, "mensaje");
		String fotoUrl = servPersistencia.recuperarPropiedadEntidad(eEstado, "imagen");
		Estado estado = new Estado(mensaje, fotoUrl, codigo);
		return estado;
	}

	@Override
	public List<Estado> recuperarTodosEstados() {
		List<Estado> estados = new LinkedList<Estado>();
		List<Entidad> eEstados = servPersistencia.recuperarEntidades("Estado");
		for(Entidad eE : eEstados) {
			estados.add(recuperarEstado(eE.getId()));
		}
		return estados;
	}
	
	public void update() {
		unicaInstancia = new AdaptadorEstado();
	}
	

}
