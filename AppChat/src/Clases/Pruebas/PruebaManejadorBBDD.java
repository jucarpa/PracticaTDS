package Clases.Pruebas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import Clases.*;
import Clases.Manejadores.ManejadorBBDD;
import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class PruebaManejadorBBDD {
	public static void main(String[] args) {
		System.out.println("Inicializamos el servidor de Persistencia");
		ServicioPersistencia servPersistencia =
				FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		
		/*System.out.println("Eliminamos posibles Entidades anteriores");
		for(Entidad e:servPersistencia.recuperarEntidades()) {
			servPersistencia.borrarEntidad(e);
		}*/
		System.out.println("DONE\n---------------------------------------------------------------------");
		
		System.out.println("Inicializamos ManejadorBBDD");
		ManejadorBBDD manejador = new ManejadorBBDD();
		
		System.out.println("Creamos Entidad Usuario Juan");
		int juan = manejador.crearUsuarioBBDD("Juan", LocalDate.now(), 626496396, "juanito", "1234");
		System.out.println("[" + juan + "]");
		
		System.out.println("Creamos Entidad Usuario Maria");
		int maria = manejador.crearUsuarioBBDD("Maria", LocalDate.of(2010, 2, 20), 600794656, "MariaDeLaO", "0000");
		System.out.println("[" + maria + "]");
		
		System.out.println("Obtenemos los Objetos Usuario para entrar Password");
		Map<String,Usuario> usuarios = manejador.getUsuariosInit();
		for(Usuario u : usuarios.values()) {
			System.out.println("Usuario: " + u.getUsuario());
			System.out.println("Contraseña: " + u.getContraseña());
			System.out.println("Id: " + u.getIdUsuario());
		}
		
		System.out.println("Vamos a ver que tiene cada usuario");
		for(Usuario user : usuarios.values()) {
			Usuario u = manejador.getUsuario(user.getIdUsuario());
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
