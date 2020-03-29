package modelo;

import java.util.List;
import java.util.stream.Collectors;


//Buscar por texto dentro del cuerpo del mensaje
//Diferencia entre mayusculas y minusculas
//No es necesario que sea el mismo cuerpo, 
//devuelve los mensajes que contienen el texto
public class BuscarTexto implements IBuscar {
	String texto;
	
	public BuscarTexto(String t) {
		texto = t;
	}
	
	public void add(IBuscar... busquedas) {
	}

	public void remove(IBuscar busqueda) {
	}

	public List<Mensaje> buscar(List<Mensaje> mensajes) {
		return mensajes.stream()
			.filter(m -> m.getTexto().contains(texto))
			.collect(Collectors.toList());
	}

}
