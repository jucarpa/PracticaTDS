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

import modelo.Usuario;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame {

	private JPanel contenedorPrincipal;

	private PanelLogin pantallaLogin;
	private PanelAltaCliente pantallaRegistro;
	private PanelVistaPrinciaplScene pantallaPrincipal;
			
	public VentanaMain() {
		setSize(Constantes.ventana_x_size, Constantes.ventana_y_size);
		setTitle("Aplicacion AppChat");
		contenedorPrincipal = (JPanel) this.getContentPane();

		/* crear pantallas */
		pantallaLogin = new PanelLogin(this);
		pantallaRegistro = new PanelAltaCliente(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Pantalla inicial
		setVisible(true);
		cambioPanelLogin();
	}

	public static void main(String[] args) {
		new VentanaMain();
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
		setContentPane(new PanelVistaPrinciaplScene(this, movilUsuarioActual));
		validate();
	}

}
