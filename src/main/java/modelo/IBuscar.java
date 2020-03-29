package modelo;

import java.util.List;


//Patron Estructural de Composite
public interface IBuscar {
	public void add(IBuscar... busquedas);
	public void remove(IBuscar busqueda);
	public List<Mensaje> buscar(List<Mensaje> m);
}
