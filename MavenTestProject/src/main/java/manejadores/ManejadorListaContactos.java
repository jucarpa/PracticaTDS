package manejadores;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import vista.PCIUC;
import vista.PGUC;
import vista.PListaContactos;

public class ManejadorListaContactos {
	private PListaContactos listaContactos;
	private int movilUA;
	private JPanel panel;
	private Map<Integer, PCIUC> panelesCI = new HashMap<Integer, PCIUC>();
	private Map<Integer, PGUC> panelesG = new HashMap<Integer, PGUC>();
	private int nContactos;
	private List<Contacto> contactosOrdenadosOld = new LinkedList<Contacto>();
	public ManejadorListaContactos(PListaContactos lC, int movilUA, JPanel panel) {
		listaContactos = lC;
		this.movilUA = movilUA;
		this.panel = panel;
		
		List <Contacto> cAOrdenar = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA).getContactos();
		List<Contacto> cOrdenados = OrdenarContactos.getUnicaInstancia().Bubble(cAOrdenar);
		contactosOrdenadosOld = cOrdenados;
		for(Contacto c : cOrdenados) {
			if(c.getClass() == ContactoIndividual.class) {
				ContactoIndividual ci = (ContactoIndividual) c;
			PCIUC aux = new PCIUC(ci.getMovil(),movilUA, listaContactos);
			panel.add(aux);
			panelesCI.put(ci.getId(), aux);
			System.out.println(aux);
			}
			else {
				Grupo g = (Grupo) c;
				PGUC aux = new PGUC(g.getNombre(), movilUA, listaContactos);
				panel.add(aux);
				panelesG.put(g.getId(), aux);
			}
			
		}
		
		nContactos = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA).getContactos().size();
	}
	
	public void addPanelContacto(Contacto c, int i) {
		if(i == 1) {
			ContactoIndividual aux = (ContactoIndividual) c;
			PCIUC pCIUC = new PCIUC(aux.getMovil(),movilUA, listaContactos);
			panelesCI.put(aux.getId(), pCIUC);
			panel.add(pCIUC);
			panel.repaint();
			panel.validate();
			
		} else {
			Grupo g = (Grupo) c;
			PGUC aux = new PGUC(g.getNombre(), movilUA, listaContactos);
			panelesG.put(g.getId(), aux);
			panel.add(aux);
			panel.repaint();
			panel.validate();
		}
		nContactos += 1;
	}
	
	public void modificarContacto(Contacto c, int i) {
		if(i == 1) {
			panelesCI.get(c.getId()).update();
		} else 
			panelesG.get(c.getId()).update();
	}
	
	public void removeCI(ContactoIndividual ci) {
		System.out.println(panelesCI.get(ci.getId()));
		panelesCI.get(ci.getId()).removeUpdate();
		panelesCI.get(ci.getId()).setVisible(false);
		panelesCI.remove(ci.getId());
		nContactos -= 1;
	}
	
	public void removeG(Grupo g) {
		panelesG.get(g.getId()).setVisible(false);
		panelesG.get(g.getId()).removeUpdate();
		panelesG.remove(g.getId());
		nContactos -= 1;
	}
	
	public void update() {
		List <Contacto> cAOrdenar = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA).getContactos();
		List<Contacto> cOrdenados = OrdenarContactos.getUnicaInstancia().Bubble(cAOrdenar);
		panel.removeAll();
		for(Contacto c : cOrdenados) {
			if(c.getClass() == Grupo.class) {
				addPanelContacto(c, 2);
			} else {addPanelContacto(c, 1);
			}
		}
	}
}
