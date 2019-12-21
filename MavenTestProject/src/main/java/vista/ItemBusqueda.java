package vista;

import javax.swing.JPanel;

import modelo.Mensaje;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;

public class ItemBusqueda extends JPanel {

	/**
	 * Create the panel.
	 */
	public ItemBusqueda(Mensaje m) {
		setMinimumSize(new Dimension(10, 50));
		setMaximumSize(new Dimension(32767, 50));
		setBounds(new Rectangle(0, 0, 0, 50));
		setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblTexto = new JLabel("Texto");
		lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblTexto);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblHora = new JLabel("Hora");
		lblHora.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblHora);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JLabel lblEmisor = new JLabel("Emisor");
		lblEmisor.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblEmisor);
		
		lblTexto.setText(m.getTexto());
		lblHora.setText(m.getHora().toString());
		lblEmisor.setText(m.getEmisor().getNombre());
		
		JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//add(scrollPane);
		
	}

}
