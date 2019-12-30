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
			System.out.println(e.getNombre());
			if(e.getNombre().equals("Usuario")) {
					servPresistencia.eliminarPropiedadEntidad(e, "gruposAdmin");
					servPresistencia.anadirPropiedadEntidad(e, "gruposAdmin", "");
					for(Propiedad p : e.getPropiedades()) {
						System.out.println(p.getNombre() +": " + p.getValor());
					}
			}
		}
	}
}
