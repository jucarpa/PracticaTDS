package Clases.Pruebas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Clases.*;
import Clases.Manejadores.ManejadorCrear;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class PruebaManejadorCrear {
public static void main(String[] args) {
	ArrayList<Integer> ids = new ArrayList<Integer>();
	
	
	System.out.println("Inicializamos el servidor de Persistencia");
	ServicioPersistencia servPersistencia =
			FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	
	System.out.println("Eliminamos posibles Entidades anteriores");
	for(Entidad e:servPersistencia.recuperarEntidades()) {
		servPersistencia.borrarEntidad(e);
	}
	System.out.println("DONE");
	
	System.out.println("Iniciamos el MenajadorCrear");
	CatalogoUsuarios cu = new CatalogoUsuarios();
	ManejadorCrear mc = new ManejadorCrear(servPersistencia, cu);
	
	System.out.println("Creamos al Usuario Juan");
	Usuario juan = mc.crearUsuario("Juan", LocalDate.of(2010, 3, 10), 111111111,"juaniko", "1111");
	ids.add(juan.getIdUsuario());
	
	System.out.println("Creamos al Usuario Maria");
	Usuario maria = mc.crearUsuario("Maria", LocalDate.now(), 222222222, "mary", "2222");
	ids.add(maria.getIdUsuario());
	
	
	System.out.println("Juan añade a Maria");
	ContactoIndividual ci = mc.crearContactoIndividual("Mi mary", 222222222, juan);
	ids.add(ci.getId());
	
	System.out.println("Juan crea un grupo");
	Grupo g = mc.crearGrupo("Pa ti mi Cola", juan);
	ids.add(g.getId());
	
	System.out.println("Añadimos a Maria al Grupo");
	
	mc.anyadirContactoGrupo(maria, g);
	
	System.out.println("Maria envia un mensaje por el grupo");
	
	Mensaje m = mc.crearMensaje("qn eres??", LocalTime.now(), 0, g, maria);
	ids.add(m.getId());
	
	System.out.println("Vamos a ver los datos desde el servidor de persistencia");
	Entidad aux;
	for(int i = 0; i < ids.size(); i++) {
		aux = servPersistencia.recuperarEntidad(ids.get(i));
		for(Propiedad prop : aux.getPropiedades()) {
			System.out.println(prop.getNombre() + ":*" + prop.getValor());
		}
		System.out.println("id:" + aux.getId());
		System.out.println("--------------------------------------------------------");
	}

	
	
	
	
	
}
}
