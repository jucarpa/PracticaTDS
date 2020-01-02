package pruebas;

import beans.Entidad;
import beans.Propiedad;
import controlador.ControladorAppChat;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class Prueba2 {
	public static void main(String[] args) {
		ServicioPersistencia servPresistencia = FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
		for(Entidad e : servPresistencia.recuperarEntidades()) {
			servPresistencia.borrarEntidad(e);
		}
	}
}
