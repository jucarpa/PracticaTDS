package modelo;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


//Buscar Por última Fecha
public class BuscarFechaFin implements IBuscar {
	LocalDateTime fin;
	
	//Anyadimos un dia para que fFin cuente ultimo dia
	public BuscarFechaFin(Date fFin) {
		fin = fFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(1);
	}

	public void add(IBuscar... busquedas) {
	}

	public void remove(IBuscar busqueda) {
	}

	public List<Mensaje> buscar(List<Mensaje> mensajes) {
		return mensajes.stream()
				.filter(m -> m.getHora().isBefore(fin))
				.collect(Collectors.toList());
	}

}