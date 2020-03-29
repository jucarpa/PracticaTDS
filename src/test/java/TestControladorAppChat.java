import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controlador.ControladorAppChat;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class TestControladorAppChat {
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorCI;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	
	private Usuario aux;
	private ContactoIndividual auxCI;
	private Grupo auxG;
	
	ControladorAppChat controlador;
	
	@Before
	public void incializar() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorCI = factoria.getCIDAO();
		adaptadorGrupo = factoria.getGrupoDAO();

		aux = new Usuario("Prueba1", Date.from(Instant.now()), 0, "Prueba1", "Prueba1", "Prueba1");
		adaptadorUsuario.registrarUsuario(aux);
		
		Usuario aux2 = new Usuario("Prueba2", Date.from(Instant.now()), 123, "Prueba12", "Prueba12", "Prueba12");
		adaptadorUsuario.registrarUsuario(aux2);
		
		auxCI = aux.crearCI("auxCI", 123, aux2);
		adaptadorCI.registrarContactoIndividual(auxCI);
		
		auxG = aux.crearGrupo("auxG", new ArrayList<ContactoIndividual>());
		adaptadorGrupo.registrarGrupo(auxG);
		
		adaptadorUsuario.modificarUsuario(aux);
		
		controlador = ControladorAppChat.getUnicaInstancia();
		controlador.setUsuario(aux);
	}

	@Test
	public void testCrearCI() {
		ContactoIndividual ciAUX = controlador.crearCI("Prueba2", 2);
		
		ContactoIndividual ciAUX2 = adaptadorCI.recuperarContactoIndividual(ciAUX.getId());
		
		assertEquals(ciAUX, ciAUX2);
	}

	@Test
	public void testCrearGrupo() {
		Grupo grupoAUX = controlador.crearGrupo("PruebaGrupo", new ArrayList<ContactoIndividual>());
		
		Grupo grupoAUX2 = adaptadorGrupo.recuperarGrupo(grupoAUX.getId());
		
		assertEquals(grupoAUX, grupoAUX2);
	}

	@Test
	public void testCrearMensajeCI() {
		Mensaje mAUX = controlador.crearMensajeCI("PRUEBA MENSAJE", 0, auxCI);
		
		Mensaje mAUX2 = adaptadorCI.recuperarContactoIndividual(auxCI.getId()).getMensajes().get(0);
		
		auxCI.setMensajes(new ArrayList<Mensaje>());
		adaptadorCI.modificarContactoIndividual(auxCI);
		
		assertEquals(mAUX, mAUX2);
		
	}

	@Test
	public void testCrearMensajeG() {
		Mensaje mAUX = controlador.crearMensajeG("PRUEBA MENSAJE", 0, auxG);
		
		Mensaje mAUX2 = adaptadorGrupo.recuperarGrupo(auxG.getId()).getMensajes().get(0);
		
		auxG.setMensajes(new ArrayList<Mensaje>());
		adaptadorGrupo.modificarGrupo(auxG);
		
		assertEquals(mAUX, mAUX2);
	}

	@Test
	public void testLogin() {
		assertTrue(controlador.login("Prueba1", "Prueba1"));
	}

	@Test
	public void testModificarCI() {
		String nNombre = "Cambio de Nombre";
		controlador.modificarCI(nNombre, 123, auxCI);
		
		String nombre = adaptadorCI.recuperarContactoIndividual(auxCI.getId()).getNombre();
		
		assertEquals(nNombre, nombre);
	}

	@Test
	public void testModificarGrupo() {
		String nNombre = "Cambio de Nombre";
		controlador.modificarGrupo(nNombre,new ArrayList<ContactoIndividual>(), auxG);
		
		String nombre = adaptadorGrupo.recuperarGrupo(auxG.getId()).getNombre();
		
		assertEquals(nNombre, nombre);
	}

}
