package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.Grupo;
import modelo.Usuario;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class PMostrarContactos extends JPanel {
	/**
	 * Create the panel.
	 */
	public PMostrarContactos(PVistaPrincipal ventana, int movilUA) {
		setMinimumSize(new Dimension(260, 260));
		setMaximumSize(new Dimension(260, 260));
		//this.ventana = ventana;
		
		setBounds(new Rectangle(0, 0, 260, 260));
		setLayout(new BorderLayout(0, 0));
		JPanel aux = new JPanel();
		aux.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(aux, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		add(scrollPane);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnNewButton.setMinimumSize(new Dimension(20, 25));
		btnNewButton.setMaximumSize(new Dimension(20, 25));
		btnNewButton.setBounds(new Rectangle(0, 0, 20, 0));
		add(btnNewButton, BorderLayout.EAST);
		
		Usuario usu = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA);
		for(Contacto c : usu.getContactos()) {
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.setMinimumSize(new Dimension(10, 30));
			panel.setMaximumSize(new Dimension(32767, 30));
			panel.setBounds(new Rectangle(0, 0, 0, 30));
			aux.add(panel);
			
			JLabel lblNombre = new JLabel("Nombre");
			panel.add(lblNombre);
			
			lblNombre.setText(c.getNombre());
		}
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.cambiarListaUltimosContactos();
				
			}
		});
	}

}
