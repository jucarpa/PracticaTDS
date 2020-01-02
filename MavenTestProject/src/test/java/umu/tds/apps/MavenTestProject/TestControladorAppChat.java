package umu.tds.apps.MavenTestProject;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import beans.Entidad;
import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

@RunWith(TestRunner.class)
public class TestControladorAppChat {
	//Controlador
	ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	
	//Datos Usuario
	String nombre = "Usuario 1";
	Date fechaNacimiento = Date.from(Instant.now());
	int movil = 111;
	String login = "usuario1";
	String contrasenya = "contrasenya1";
	String email = "usuario1@usuario";
	Usuario usuario;
	
	//Datos Grupo
	String nombreG = "Grupo Prueba";
	ArrayList<Integer> contactosG = new ArrayList<Integer>();
	int idG;
	Grupo g;
	
	//Datos ContactoIndividual
	String nombreCI = "Nombre ContactoIndividual 1";
	String nombreCINuevo = "Nombre Nuevo ContactoIndividual 1";
	int movilCI = 222;
	Usuario usuario2;
	ContactoIndividual ci;
	
	//Datos Mensaje
	String textoM = "Texto de Mensaje";
	int emoticono = 0; 
	Mensaje mensajeGrupo;
	Mensaje mensajeCI;
	@OrderedTest(order=1)
	public void testInit() {
		ServicioPersistencia servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		for(Entidad e : servPersistencia.recuperarEntidades())
			servPersistencia.borrarEntidad(e);
	}
	
	@OrderedTest(order=2)
	public void testRegistrarUsuario() {
		controlador.registrarUsuario(nombre, fechaNacimiento, movil, login, contrasenya, email);
		usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil);
		assertTrue(usuario != null);
	}
	
	@OrderedTest(order=3)
	public void testLoginUsuario() {
		int sol = controlador.loginUsuario(login, contrasenya);
		assertTrue(sol != -1);
	}
	
	@OrderedTest(order=4)
	public void testRegistrarGrupo() {
		controlador.registrarGrupo(nombreG, contactosG, movil);
		g = (Grupo) CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getContactos().get(0);
		idG = g.getId();
		assertTrue(nombreG.equals(g.getNombre()));
		assertTrue(g.getId() != 0);
	}
	
	@OrderedTest(order=5)
	public void testRegistrarContactoIndividual() {
		usuario2 = controlador.registrarUsuario("Usuario 2", Date.from(Instant.now()),
				movilCI, "ContactoIndividual1", "ContactoIndividual1", "ContactoIndividual1@ContactoIndividual");
		controlador.registrarContactoIndividual(nombreCI, 222, movil);
		ci = (ContactoIndividual) CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getContactos().get(1);
		assertTrue(nombreCI.equals(ci.getNombre()));
	}
	
	@OrderedTest(order=6)
	public void testExisteContacto() {
		assertTrue(controlador.existeContacto(movilCI, movil));
		assertFalse(controlador.existeContacto(0, movil));
	}
	
	@OrderedTest(order=7)
	public void testRegistrarMensajeG() {
		g = (Grupo) CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getContactos().get(0);
		controlador.registrarMensajeG(textoM, emoticono, g.getId(), movil);
		mensajeGrupo = controlador.getUsuario(movil).getGrupoPorNombre(nombreG).getMensajes().get(0);
		assertFalse(mensajeGrupo == null);
	}
	
	@OrderedTest(order=8)
	public void testRegistrarMensajeCI() {
		controlador.registrarMensajeCI(textoM, 0, movilCI, movil);
		mensajeCI = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getCIPorNumero(movilCI).getMensajes().get(0);
		assertFalse(mensajeCI == null);
	}
	
	@OrderedTest(order=9)
	public void testModificarContactoIndividual() {
		ci = (ContactoIndividual) CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getContactos().get(1);
		ci.setNombre(nombreCINuevo);
		controlador.modificarContactoIndividual(ci);
		ci = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getCIPorNumero(movilCI);
		assertTrue(ci.getNombre().equals(nombreCINuevo));
	}
	
	@OrderedTest(order=10)
	public void testModificarGrupo() {
		ci = controlador.getContactoIndividual(movilCI, movil);
		g = controlador.getGrupo(nombreG, movil);
		List<ContactoIndividual> contactosGrupo = g.getContactos();
		g.addContacto(ci);
		controlador.modificarGrupo(g, contactosGrupo);
		g = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getGrupoPorNombre(nombreG);
		assertEquals(1, g.getContactos().size());
	}
	
	
	@OrderedTest(order=11)
	public void testExisteUsuario() {
		assertTrue(controlador.existeUsuario(login));
	}
	
	@OrderedTest(order=12)
	public void testGetUsuario() {
		usuario = controlador.getUsuario(movil);
		assertFalse(usuario == null);
	}
	
	@OrderedTest(order=13)
	public void testGetContactoIndividual() {
		ci = controlador.getContactoIndividual(movilCI, movil);
		assertFalse(ci == null);
	}
	
	
	@OrderedTest(order=14)
	public void testEliminarGrupo() {
		g = controlador.getGrupo(nombreG, movil);
		controlador.eliminarGrupo(g, movil);
		g = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getGrupoPorNombre(nombreG);
		assertTrue(g == null);
	}
	
	@OrderedTest(order=15)
	public void testEliminarContactoIndividual() {
		ci = controlador.getContactoIndividual(movilCI, movil);
		controlador.eliminarContactoIndividual(ci, movil);
		ci = CatalogoUsuarios.getUnicaInstancia().getUsuario(movil).getCIPorNumero(movilCI);
		assertTrue(ci == null);
	}
}