package Clases.Pruebas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Clases.CatalogoUsuarios;
import Clases.Contacto;
import Clases.ContactoIndividual;
import Clases.Grupo;
import Clases.Mensaje;
import Clases.Usuario;
import Clases.Manejadores.ManejadorCrear;
import Clases.Manejadores.ManejadorInit;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class PruebaManejadorInit {
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
	
	
	System.out.println("Juan añade a Maria como contacto");
	ContactoIndividual ci = mc.crearContactoIndividual("Mi mary", 222222222, juan);
	ids.add(ci.getId());
	
	System.out.println("Juan crea un grupo");
	Grupo g = mc.crearGrupo("Pa ti mi Cola", juan);
	ids.add(g.getId());
	
	System.out.println("Añadimos a Maria al Grupo");
	g.addContacto(ci);
	
	System.out.println("Maria envia un mensaje por el grupo");
	Mensaje m = mc.crearMensaje("qn eres??", LocalTime.now(), 0, g, maria);
	ids.add(m.getId());
	
	System.out.println("Ahora vamos a ver los datos que hay desde el programa");
	System.out.println("Creamos el manejador de Inicialiación");
	
	System.out.println("Vamos a ver los datos desde el servidor de persistencia");
	Entidad aux;
	for(int i = 0; i < ids.size(); i++) {
		aux = servPersistencia.recuperarEntidad(ids.get(i));
		for(Propiedad prop : aux.getPropiedades()) {
			System.out.println(prop.getNombre() + ": " + prop.getValor());
		}
		System.out.println("id:" + aux.getId());
		System.out.println("--------------------------------------------------------");
	}
	
	ManejadorInit mi = new ManejadorInit();
	
	Collection<Usuario> usuarios = mi.getUsuarios();
	
	
	
	
	System.out.println("Vamos a ver que tiene cada usuario");
	for(Usuario u : usuarios) {
		System.out.println("Nombre: " + u.getNombre());
		System.out.println("Fecha de nacimiento: " + u.getFechaNacimiento().toString());
		System.out.println("Movil: " + u.getMovil());
		System.out.println("Usuario: " + u.getUsuario());
		System.out.println("Contraseña: " + u.getContraseña());
		//System.out.println("Imagen: " + u.getImagen().toString());
		System.out.println("Premium: " + u.getPremium());
		
		System.out.println("Numero de Contactos: " + u.getContactos().size());
		System.out.println("Contactos: ");
		for(Contacto c : u.getContactos()) {
			if(c.getClass() == ContactoIndividual.class) {
				ContactoIndividual cci = (ContactoIndividual) c;
				System.out.println("**Tipo: Contactoindividual");
				System.out.println("**Nombre: " + cci.getNombre());
				System.out.println("**Numero de Telefono: " + cci.getMovil() );
			} else {
				Grupo gg = (Grupo) c;
				System.out.println("**Tipo: Grupo");
				System.out.println("**Nombre: " + gg.getNombre());
				System.out.println("Numero de Integrantes: " + gg.getContactos().size());
				System.out.println("**Integrantes Del Grupo: ");
				for(ContactoIndividual cg : gg.getContactos()) {
					System.out.println("****Nombre: " + cg.getNombre());
					System.out.println("****Movil: " + cg.getMovil());
				}
				
				System.out.println("**Admin: " + gg.getAdmin().getNombre());
			}
			
			System.out.println("Mensajes: ");
			for(Mensaje me : c.getMensajes()) {
				System.out.println("**Texto: " + me.getTexto());
				System.out.println("**Para:" + me.getReceptor().getNombre());
			}
		}
		System.out.println("Grupos en los que es admin");
		for(Grupo ga : u.getGruposAdmin()) {
			System.out.println("**Nombre: " + ga.getNombre());
		}
		
		System.out.println("ID: " + u.getIdUsuario());
		
		System.out.println("-----------------------------------------");
	}
	
	
	
}
}