package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import modelo.ContactoIndividual;
import modelo.Estado;
import modelo.Usuario;
import javax.swing.JLabel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelEstadoContacto extends JPanel {
	
	private ContactoIndividual contacto;
	private JLabel imagen, nombre;
	private Estado estado;
	private JButton btnNewButton;
	
	public PanelEstadoContacto(ContactoIndividual contacto, PanelEstado ventana) {
		this.contacto = contacto;
		
		paint();
		infoUsuario();
		
		btnNewButton.addActionListener(e -> {
			ventana.mostrarEstado(estado);
		});
	}

	private void paint() {
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Dimension d = new Dimension(315, 50);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setLayout(null);
		
		imagen = new JLabel("");
		imagen.setBounds(0, 0, 56, 50);
		add(imagen);
		
		
		nombre = new JLabel();
		nombre.setBounds(56, 13, 247, 24);
		add(nombre);
		
		btnNewButton = new JButton("");
		btnNewButton.setOpaque(false);
		btnNewButton.setBounds(0, 0, 315, 50);
		add(btnNewButton);
		

	}
	
	private void infoUsuario() {
		Usuario usuarioContacto = contacto.getUsuario();

		//Obtenemos Imagen Usuario
		String urlImagen = usuarioContacto.getImagen();
		ImageIcon imIco = new ImageIcon(urlImagen);
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		imagen.setIcon(imIco);
		
		//Obtenemmos Nombre Usuario
		nombre.setText(contacto.getNombre());
		
		//Obtenemos Estado
		estado = contacto.getEstadoUsuario();
	}
}
