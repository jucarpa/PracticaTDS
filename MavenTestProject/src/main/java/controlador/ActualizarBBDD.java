package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
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
import persistencia.PoolDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import vista.PCIUC;
import vista.PChatCI;
import vista.PChatG;
import vista.PGUC;
import vista.PListaContactos;
import vista.PVistaPrincipal;


public class ActualizarBBDD {

	ControladorAppChat controlador;
	private static ActualizarBBDD unicaInstancia;
	private List<PChatCI> panelesCI = new LinkedList<PChatCI>();
	private List<PChatG> panelesG = new LinkedList<PChatG>();
	private HashMap<Integer, PCIUC> panelesCIUC = new HashMap<Integer, PCIUC>();
	private HashMap<Integer, PGUC> panelesGUC = new HashMap<Integer, PGUC>();
	private List<PListaContactos> panelContactos = new LinkedList<PListaContactos>();
	private List<PVistaPrincipal> pVP = new LinkedList<PVistaPrincipal>();
	
	
	
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
		    	PoolDAO.getUnicaInstancia().update();
		    	CatalogoUsuarios.getUnicaInstancia().update();
		    	update();
		     }
		});
		timer.setRepeats(true);
		timer.start();
		
	}
	
	public void addPanelChatCI(PChatCI pC) {
		panelesCI.add(pC);
	}
	
	public void deletePanelChatCI(PChatCI pC) {
		panelesCI.remove(pC);
	}
	
	public void addPanelChatG(PChatG pC) {
		panelesG.add(pC);
	}
	
	public void deletePanelChatG(PChatG pC) {
		panelesG.remove(pC);
	}
	
	public int addPanelCIUC(PCIUC pC) {
		panelesCIUC.put(pC.getContacto().getMovil(), pC);
		return panelesCIUC.size() - 1;
	}
	
	public void deletePanelCIUC(PCIUC pC) {
		panelesCIUC.remove(pC.getContacto().getMovil());
	}
	
	public void addPanelGUC(PGUC pC) {
		panelesGUC.put(pC.getId(), pC);
	}
	
	public void deletePanelGUC(PGUC pC) {
		panelesGUC.remove(pC.getId());
	}
	
	public void addPanelUltimosContactos(PListaContactos pU) {
		panelContactos.add(pU);
	}
	
	public void addPanelPVP(PVistaPrincipal pVp) {
		pVP.add(pVp);
	}
	
	private void update() {
		for(PChatCI pC : panelesCI) {
			pC.update();
		}
		
		for(PChatG pC : panelesG) {
			pC.update();
		}
		
		for(PCIUC pC : panelesCIUC.values()) {
			pC.update();
		}
		
		for(PGUC pC : panelesGUC.values()) {
			pC.update();
		}
		for (PListaContactos pC :panelContactos) {
			pC.update();
		}
		
		for(PVistaPrincipal pVp : pVP)
			pVp.update();
			
	}
	
}
