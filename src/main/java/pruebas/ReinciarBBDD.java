package pruebas;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class ReinciarBBDD {
	public static void main(String[] args) {
		ServicioPersistencia servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		servPersistencia
	.recuperarEntidades().stream()
		.forEach(e -> servPersistencia.borrarEntidad(e));
	}
}
