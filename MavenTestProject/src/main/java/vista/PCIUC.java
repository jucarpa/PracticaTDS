package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import modelo.Mensaje;
import tds.BubbleText;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.ComponentOrientation;
import javax.swing.border.LineBorder;

import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;

import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;

public class PCIUC extends JPanel {

	/**
	 * Create the panel.
	 */
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	private ContactoIndividual c;
	private JLabel lblImagen;
	private JLabel lblNombre ;
	private JLabel lblFecha;
	private JLabel lblTexto;
	private int movilUA;
	private Component horizontalStrut;
	private int pos;
	public PCIUC(int movilContacto, int movilUsuario, PListaContactos ventana) {
		c = controlador.getContactoIndividual(movilContacto, movilUsuario);
		
		System.out.println(c.getUsuario().getNombre());
		movilUA = movilUsuario;
		pos = ActualizarBBDD.getUnicaInstancia().addPanelCIUC(this);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setPreferredSize(new Dimension(260, 50));
		setIgnoreRepaint(true);
		setSize(new Dimension(260, 50));
		setMinimumSize(new Dimension(260, 50));
		setMaximumSize(new Dimension(260, 50));
		setBackground(Color.WHITE);
		setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(0, 0, 260, 50);
		add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		lblImagen = new JLabel("");
		panel_2.add(lblImagen);
		JPanel panel = new JPanel();
		panel_2.add(panel);
		panel.setMinimumSize(new Dimension(200, 50));
		panel.setMaximumSize(new Dimension(200, 50));
		panel.setSize(new Dimension(200, 50));
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		lblNombre = new JLabel(c.getNombre());
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		lblFecha = new JLabel("");
		lblFecha.setForeground(Color.LIGHT_GRAY);
		lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblFecha);
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
						
		lblTexto = new JLabel("");
		lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTexto);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setOpaque(false);
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(0, 0, 260, 50);
		add(btnNewButton);
		ImageIcon imagen = c.getUsuario().getImagen();
		System.out.println(c.getUsuario().getImagenUrl());
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		lblImagen.setIcon(imagen);
		try {
			Mensaje ultMensaje = c.getMensajes().get(c.getMensajes().size() - 1);			
			lblFecha.setText(ultMensaje.getHora().
					format(DateTimeFormatter.ofPattern("HH:mm")));
			if(!ultMensaje.getTexto().equals(""))
			lblTexto.setText(ultMensaje.getTexto());
			else
				lblTexto.setIcon(new ImageIcon(BubbleText.getEmoji(ultMensaje.getEmoticon()).getImage().getScaledInstance(15,15, Image.SCALE_SMOOTH)));
		} catch (Exception e) {}

		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContactoSeleccionado(c, 1);
			}
		});
	}
	
	public void update() {
		c = ControladorAppChat.getUnicaInstancia().getContactoIndividual(c.getMovil(), movilUA);
		lblNombre.setText(c.getNombre());
		ImageIcon iCI = c.getUsuario().getImagen();
		Image i = iCI.getImage();
		iCI = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		lblImagen.setIcon(iCI);
		try {
			Mensaje ultMensaje = c.getMensajes().get(c.getMensajes().size() - 1);			
			lblFecha.setText(ultMensaje.getHora().
					format(DateTimeFormatter.ofPattern("HH:mm")));
			if(!ultMensaje.getTexto().equals(""))
				lblTexto.setText(ultMensaje.getTexto());
				else
					lblTexto.setIcon(new ImageIcon(BubbleText.getEmoji(ultMensaje.getEmoticon()).getImage().getScaledInstance(15,15, Image.SCALE_SMOOTH)));
		} catch (Exception e) {}
	}
	
	public void removeUpdate() {
		ActualizarBBDD.getUnicaInstancia().deletePanelCIUC(this);
	}
	
	public ContactoIndividual getContacto() {
		return c;
	}
}
