package modelo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//Buscar Por Fecha inicial
public class BuscarFechaIni implements IBuscar {
	LocalDateTime ini;
	
	//Restamos un dia para que el dia inicial sea fIni
	public BuscarFechaIni(Date fIni) {
		ini = fIni.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(1);
	}

	public void add(IBuscar... busquedas) {
	}

	public void remove(IBuscar busqueda) {
	}

	public List<Mensaje> buscar(List<Mensaje> mensajes) {
		return mensajes.stream()
				.filter(m -> m.getHora().isAfter(ini))
				.collect(Collectors.toList());
				
				
	}

}
