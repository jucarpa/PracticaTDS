package persistencia;

import java.util.List;

import modelo.Estado;

public interface IAdaptadorEstadoDAO {
	public void registrarEstado(Estado estado);

	public void borrarEstado(Estado estado);

	public void modificarEstado(Estado estado);

	public Estado recuperarEstado(int codigo);

	public List<Estado> recuperarTodosEstados();

}
