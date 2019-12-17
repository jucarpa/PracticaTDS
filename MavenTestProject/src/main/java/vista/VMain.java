package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;

import controlador.ControladorAppChat;
import modelo.Usuario;

@SuppressWarnings("serial")
public class VMain extends JFrame {

	private JPanel contenedorPrincipal;

	private PLoginUsuario pantallaLogin;
	private PRegistrarUsuario pantallaRegistro;
	private PVistaPrincipal pantallaPrincipal;
			
	public VMain() {
		setSize(Constantes.ventana_x_size, Constantes.ventana_y_size);
		setTitle("Aplicacion AppChat");
		contenedorPrincipal = (JPanel) this.getContentPane();

		/* crear pantallas */
		pantallaLogin = new PLoginUsuario(this);
		pantallaRegistro = new PRegistrarUsuario(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Pantalla inicial
		setVisible(true);
		cambioPanelLogin();
	}


	public void cambioPanelLogin() {
		setContentPane(pantallaLogin);
		validate();
	}

	public void cambioPanelRegistro() {
		setContentPane(pantallaRegistro);
		validate();
	}

	public void cambioPanelPrincipal(int movilUsuarioActual) {
		setContentPane(new PVistaPrincipal(this, movilUsuarioActual));
		validate();
	}

}
