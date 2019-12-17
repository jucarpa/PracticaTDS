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
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.ComponentOrientation;
import javax.swing.border.LineBorder;

import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;

import javax.swing.JButton;

public class PGUC extends JPanel{

	/**
	 * Create the panel.
	 */
	private ControladorAppChat controlador = ControladorAppChat.getUnicaInstancia();
	private Grupo c;
	private JLabel lblImagen;
	private JLabel lblNombre ;
	private JLabel lblFecha;
	private JLabel lblTexto;
	private int movilUA;
	public PGUC(String nombreGrupo, int movilUsuario, PListaContactos ventana) {
		ActualizarBBDD.getUnicaInstancia().addPanelGUC(this);
		c = controlador.getGrupo(nombreGrupo, movilUsuario);
		movilUA = movilUsuario;
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
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.setOpaque(false);
		lblNombre = new JLabel(c.getNombre());
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
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
		
		
		ImageIcon iU = new ImageIcon(VMain.class.getResource("/imagenes/ImagenGrupoDef.png"));
		Image i = iU.getImage();
		iU = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		lblImagen.setIcon(iU);	
		try {
			Mensaje ultMensaje = c.getMensajes().get(c.getMensajes().size() - 1);			
			lblFecha.setText(ultMensaje.getHora().
					format(DateTimeFormatter.ofPattern("HH:mm")));
			lblTexto.setText(ultMensaje.getTexto());
		} catch (Exception e) {}

		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setContactoSeleccionado(c, 2);
			}
		});

	}
	
	public void update() {
		c = ControladorAppChat.getUnicaInstancia().getGrupoPorId(c.getId(), movilUA);
		lblNombre.setText(c.getNombre());
		try {
			Mensaje ultMensaje = c.getMensajes().get(c.getMensajes().size());			
			lblFecha.setText(ultMensaje.getHora().toString());
			lblTexto.setText(ultMensaje.getTexto());
		} catch (Exception e) {}
		
	}
	
	public void removeUpdate() {
		ActualizarBBDD.getUnicaInstancia().deletePanelGUC(this);
	}
}
