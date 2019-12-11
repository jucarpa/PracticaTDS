package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import beans.Entidad;
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
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import vista.PanelCIUC;
import vista.PanelChatCI;
import vista.PanelChatGrupo;
import vista.PanelGUC;


public class ActualizarBBDD {

	private CatalogoUsuarios catalogo;
	ControladorAppChat controlador;
	private AdaptadorUsuario adaptadorUsuarios = AdaptadorUsuario.getUnicaInstancia();
	private AdaptadorContactoIndividual adaptadorCI =AdaptadorContactoIndividual.getUnicaInstancia();
	private AdaptadorGrupo adaptadorGrupo = AdaptadorGrupo.getUnicaInstancia();
	private AdaptadorEstado adaptadorEstado = AdaptadorEstado.getUnicaInstancia();
	private AdaptadorMensaje adaptadorMensaje = AdaptadorMensaje.getUnicaInstancia();
	
	private static ActualizarBBDD unicaInstancia;
	private List<PanelChatCI> panelesCI = new LinkedList<PanelChatCI>();
	private List<PanelChatGrupo> panelesG = new LinkedList<PanelChatGrupo>();
	private List<PanelCIUC> panelesCIUC = new LinkedList<PanelCIUC>();
	private List<PanelGUC> panelesGUC = new LinkedList<PanelGUC>();
	
	
	
	public static ActualizarBBDD getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ActualizarBBDD();
		return unicaInstancia;
	}
	private ActualizarBBDD() {
		Timer timer = new Timer (10000, new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {	
		    	CatalogoUsuarios.getUnicaInstancia().update();
		    	ControladorAppChat.getUnicaInstancia().update();
		    	update();
		     }
		});
		timer.setRepeats(true);
		timer.start();
		
	}
	
	public void addPanelChatCI(PanelChatCI pC) {
		panelesCI.add(pC);
	}
	public void addPanelChatG(PanelChatGrupo pC) {
		panelesG.add(pC);
	}
	
	public void addPanelCIUC(PanelCIUC pC) {
		panelesCIUC.add(pC);
	}
	
	public void addPanelGUC(PanelGUC pC) {
		panelesGUC.add(pC);
	}
	
	private void update() {
		for(PanelChatCI pC : panelesCI) {
			pC.update();
		}
		
		for(PanelChatGrupo pC : panelesG) {
			pC.update();
		}
		
		for(PanelCIUC pC : panelesCIUC) {
			pC.update();
		}
		
		for(PanelGUC pC : panelesGUC) {
			pC.update();
		}
	}
	
}
