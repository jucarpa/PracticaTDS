package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import modelo.Contacto;
import modelo.Mensaje;

class TestOrdenacion {
	
	@org.junit.jupiter.api.Test
	void testOrdenacion() {
		Contacto c0 = new Contacto("Juan");
		Contacto c1 = new Contacto("Pedro");
		Contacto c2 = new Contacto("Jualian");
		Mensaje m0 = new Mensaje("", LocalTime.of(10, 00), 0);
		Mensaje m1 = new Mensaje("", LocalTime.of(11, 00), 0);
		Mensaje m2 = new Mensaje("", LocalTime.of(12, 00), 0);
		Mensaje m3 = new Mensaje("", LocalTime.of(13, 00), 0);
		
		List<Mensaje> ms0 = new LinkedList<Mensaje>();
		ms0.add(m0);
		//c1 3
		c1.setMensajes(ms0);
		
		List<Mensaje> ms1 = new LinkedList<Mensaje>(ms0);
		ms1.add(m2);
		//c0 pos 2
		c0.setMensajes(ms1);
		List<Mensaje> ms2 = new LinkedList<Mensaje>(ms1);
		ms2.add(m3);
		//c2 pos 1
		c2.setMensajes(ms2);
		List<Contacto> esperado = new LinkedList<Contacto>();
		
		esperado.add(c2);
		esperado.add(c0);
		esperado.add(c1);
		
		
		List<Contacto> resultado = new LinkedList<Contacto>();
		
		resultado.add(c1);
		resultado.add(c0);
		resultado.add(c2);
		resultado = OrdenarContactos.getUnicaInstancia().Bubble(resultado);
		
		for(int i = 0; i < 3; i++) {
			System.out.println(esperado.get(i).getNombre() + " - " + resultado.get(i).getNombre());
			assertEquals(esperado.get(i), resultado.get(i));
		}
	}

}
