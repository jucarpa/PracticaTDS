package pruebas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import beans.Entidad;
import controlador.ControladorAppChat;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import persistencia.AdaptadorContactoIndividual;
import persistencia.AdaptadorEstado;
import persistencia.AdaptadorGrupo;
import persistencia.AdaptadorMensaje;
import persistencia.AdaptadorUsuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class ActualizarCatalogoUsuarios {

	private CatalogoUsuarios catalogo;
	ControladorAppChat controlador;
	private AdaptadorUsuario adaptadorUsuarios = AdaptadorUsuario.getUnicaInstancia();
	private AdaptadorContactoIndividual adaptadorCI =AdaptadorContactoIndividual.getUnicaInstancia();
	private AdaptadorGrupo adaptadorGrupo = AdaptadorGrupo.getUnicaInstancia();
	private AdaptadorEstado adaptadorEstado = AdaptadorEstado.getUnicaInstancia();
	private AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();
	
	public ActualizarCatalogoUsuarios(ControladorAppChat controlador) {
		this.catalogo = controlador.getCatalogo();
		this.controlador = controlador;
		
		Timer timer = new Timer (1000, new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        //actualizarCatalogo();
		    	System.out.println("Hola");
		     }
		});
		timer.setRepeats(true);
		timer.start();
		
	}
	
	public void actualizarCatalogo() {
		int numeroUsuarios = catalogo.getUsuarios().size();
		List<Usuario> eUsuarios = adaptadorUsuarios.recuperarTodosUsuarios();
		int numeroUsuariosServ = eUsuarios.size();
		if(numeroUsuariosServ > numeroUsuarios) {
			for(Usuario usuario : eUsuarios) {
				if(catalogo.getUsuario(usuario.getMovil()) == null) {
					controlador.usuariosUpdated(usuario);
				} else {
					List<Contacto> contactosUpdated = new LinkedList<Contacto>();
					Usuario usuarioCatalogo = catalogo.getUsuario(usuario.getMovil()); 
						if(usuario.getContactos().size() > usuarioCatalogo.getContactos().size()) {
							controlador.contactosUpdated(usuario);
						} else {
						for(ContactoIndividual ci : usuarioCatalogo.getContactosIndividuales()) {
							ContactoIndividual ciServ = adaptadorCI.recuperarContactoIndividual(ci.getId());
							if(ci.getMensajes().size() != ciServ.getMensajes().size()) {
								controlador.mensajesCIUpdated(ciServ, usuario);
							}
						}
						for(Grupo g : usuarioCatalogo.getGrupos()) {
							Grupo gServ = adaptadorGrupo.recuperarGrupo(g.getId());
							if(g.getMensajes().size() != g.getMensajes().size()) {
								controlador.mensajesGUpdated(gServ, usuario);
							}
							if(g.getContactos().size() != gServ.getContactos().size()) {
								controlador.contactosGrupoUpdated(gServ, usuario);
							}
							
							if(!g.getNombre().equals(gServ.getNombre())) {
								controlador.nombreGrupoUpdated(gServ, usuario);
							}
						}
						}
				}
			}
		}
	}
}
