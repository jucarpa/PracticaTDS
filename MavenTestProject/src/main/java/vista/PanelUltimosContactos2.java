package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controlador.ControladorAppChat;
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

public class PanelUltimosContactos2 extends JPanel {
	private PanelVistaPrinciaplScene ventana;
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	public PanelUltimosContactos2(PanelVistaPrinciaplScene venatana, int movilUA) {
		ventana = venatana;
		setPreferredSize(new Dimension(260, 260));
		setSize(new Dimension(260, 260));
		setMaximumSize(new Dimension(260, 260));
		setMinimumSize(new Dimension(260, 260));
		setBackground(new Color(204, 255, 153));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel panel = new JPanel();
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
		
		JLabel lblHolaaaa = new JLabel("Ultimas Conversaciones");
		lblHolaaaa.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(lblHolaaaa);
		lblHolaaaa.setAlignmentY(Component.TOP_ALIGNMENT);
		lblHolaaaa.setBackground(new Color(204, 255, 153));
		lblHolaaaa.setFont(new Font("Nirmala UI", Font.BOLD, 15));
		lblHolaaaa.setHorizontalAlignment(SwingConstants.CENTER);
		lblHolaaaa.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Usuario u = controlador.getUsuario(movilUA);
		List<Contacto> contactos = u.getContactos();
		for (Contacto c : contactos) {
			if(c.getClass() == ContactoIndividual.class) {
				ContactoIndividual ci = (ContactoIndividual) c;
			PanelCIUC aux = new PanelCIUC(ci.getMovil(),movilUA, this);
			panel.add(aux);
			}
			else {
				Grupo g = (Grupo) c;
				PanelGUC aux = new PanelGUC(g.getNombre(), movilUA, this);
				panel.add(aux);
			}
		}
		
	}
	
	public void setContactoSeleccionado(Contacto c) {
		ventana.setContactoSeleccionado(c);
	}
}
