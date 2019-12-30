package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;
import manejadores.ManejadorListaContactos;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.JScrollBar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import javax.swing.border.BevelBorder;
import javax.swing.JTable;

public class PListaContactos extends JPanel {
	private PVistaPrincipal ventana;
	ManejadorListaContactos manejador;
	private JPanel panel;
	
	public PListaContactos(int movilUA, PVistaPrincipal pVP) {
		ventana = pVP;
		ActualizarBBDD.getUnicaInstancia().addPanelUltimosContactos(this);
		setPreferredSize(new Dimension(260, 260));
		setSize(new Dimension(260, 260));
		setMaximumSize(new Dimension(260, 260));
		setMinimumSize(new Dimension(260, 260));
		setBackground(new Color(204, 255, 153));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.setBackground(new Color(204, 255, 153));
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setSize(new Dimension(260, 0));
		scrollPane.setPreferredSize(new Dimension(260, 2));
		scrollPane.setMinimumSize(new Dimension(260, 6));
		scrollPane.setMaximumSize(new Dimension(260, 32767));
		add(scrollPane);
		
		manejador = new ManejadorListaContactos(this, movilUA, panel);
	}
	
	public void setContactoSeleccionado(Contacto c, int i) {
		System.out.println(i);
		ventana.setContactoSeleccionado(c, i);
	}
	
	public void addPanelContacto(Contacto c, int i) {
		manejador.addPanelContacto(c, i);
	}
	
	public void modificarContacto(Contacto c,int i) {
		manejador.modificarContacto(c, i);
	}
	
	public void removeCI(ContactoIndividual ci) {
		manejador.removeCI(ci);
	}
	
	public void removeG(Grupo g) {
		manejador.removeG(g);
	}
	
	public void update() {
		manejador.update();
	}
	
	public void pGUCEliminado(int id) {
		ventana.pGUCEliminado(id);
	}
}
