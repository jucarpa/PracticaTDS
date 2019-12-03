package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import modelo.Mensaje;
import modelo.Contacto;
import modelo.ContactoIndividual;
import pruebas.prueba;
import java.awt.Color;
import java.awt.Dimension;

public class PanelCIUltimosContactos extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelCIUltimosContactos(Contacto c) {
		setPreferredSize(new Dimension(256, 50));
		setIgnoreRepaint(true);
		setSize(new Dimension(256, 50));
		setMinimumSize(new Dimension(256, 50));
		setMaximumSize(new Dimension(256, 50));
		setBackground(new Color(153, 255, 102));
		setLayout(new BorderLayout(0, 0));

		JLabel lblImagen = new JLabel("");
		add(lblImagen, BorderLayout.WEST);
		if (c.getClass() == ContactoIndividual.class) {
			ContactoIndividual aux = (ContactoIndividual) c;
			ImageIcon iCI = aux.getUsuario().getImagen();
			Image i = iCI.getImage();
			iCI = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			lblImagen.setIcon(iCI);
		} else {
			ImageIcon iU = new ImageIcon(prueba.class.getResource("/imagenes/ImagenGrupoDef.png"));
			Image i = iU.getImage();
			iU = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			lblImagen.setIcon(iU);
		}

		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 255, 51));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNombre = new JLabel(c.getNombre());
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNombre, BorderLayout.WEST);
		JLabel lblFecha = new JLabel("");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFecha, BorderLayout.EAST);
		JLabel lblTexto = new JLabel("");
		lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTexto, BorderLayout.SOUTH);
		try {
			Mensaje ultMensaje = c.getMensajes().get(c.getMensajes().size());
			lblFecha.setText(ultMensaje.getHora().toString());
			lblTexto.setText(ultMensaje.getTexto());
		} catch (Exception e) {
		}

	}

}
