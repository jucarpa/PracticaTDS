package modelo;

import java.util.List;
import java.util.stream.Collectors;


//Busca Mensajes en el que envia el Mensaje es el Usuario
public class BuscarUsuario implements IBuscar {

	private Usuario usuario;
	
	public BuscarUsuario(Usuario u) {
		usuario = u;
	}
	
	public void add(IBuscar... busquedas) {

	}

	public void remove(IBuscar busqueda) {
	}

	public List<Mensaje> buscar(List<Mensaje> mensajes) {
		return mensajes.stream()
				.filter(m -> m.getEmisor().equals(usuario))
				.collect(Collectors.toList());
	}

}
