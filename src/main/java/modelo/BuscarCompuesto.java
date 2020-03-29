package modelo;

import java.util.ArrayList;
import java.util.List;


//Patron Compuesto de Composite
public class BuscarCompuesto implements IBuscar {

	private List<IBuscar> busquedas;
	
	public BuscarCompuesto() {
		busquedas = new ArrayList<IBuscar>();
	}
	
	public void add(IBuscar... busq) {
		for(IBuscar b : busq)
			busquedas.add(b);
	}

	public void remove(IBuscar busqueda) {
			busquedas.remove(busqueda);
	}

	
	//Se le envia una lista de mensajes, pasa por cada hijo y la lista que 
	//devuelve el hijo es la que se envia al siguiente hijo
	//De esta forma por cada hijo que pasa "filtra" la lista de mensajes original
	public List<Mensaje> buscar(List<Mensaje> mensajes) {
		if(!busquedas.isEmpty()) {
			List<Mensaje> sol = mensajes;
			for(IBuscar b : busquedas) {
				sol = b.buscar(sol);
			}
			return sol;
		}
		return new ArrayList<Mensaje>();
	}

}
