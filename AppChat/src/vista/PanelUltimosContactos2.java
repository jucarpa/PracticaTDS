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
import modelo.Usuario;

import java.awt.Dimension;

public class PanelUltimosContactos2 extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelUltimosContactos2() {
		setSize(new Dimension(256, 256));
		setMaximumSize(new Dimension(256, 256));
		setMinimumSize(new Dimension(256, 256));
		setBackground(new Color(51, 204, 102));
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblHolaaaa = new JLabel("Ultimas Conversaciones");
		lblHolaaaa.setBackground(new Color(102, 204, 0));
		lblHolaaaa.setFont(new Font("Nirmala UI", Font.BOLD, 15));
		lblHolaaaa.setHorizontalAlignment(SwingConstants.CENTER);
		lblHolaaaa.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblHolaaaa, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(102, 204, 51));
		add(scrollPane, BorderLayout.CENTER);
	}

	public void update() {
		Usuario u = ControladorAppChat.getUnicaInstancia().getUsuarioActual();
		List<Contacto> contactos = u.getContactosPorTiempo();
		for(Contacto c : contactos) {
			PanelCIUltimosContactos aux = new PanelCIUltimosContactos(c);
			add(aux, BorderLayout.CENTER);
		}
		
	}
}
