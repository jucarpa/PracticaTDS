package vista;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame {
		
	private PanelRegistroUsuario pRegistrar;
	private PanelLoginUsuario pLogin;
	
	public static void main(String[] args) {
		new VentanaMain();
	}
	
	public VentanaMain(){
		setMaximumSize(new Dimension(630,480));
		setSize(630,480);
		setMinimumSize(new Dimension(630,480));
		setTitle("Aplicacion AppChat");		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		pRegistrar = new PanelRegistroUsuario(this);
		pLogin = new PanelLoginUsuario(this);
		
		cambioLogin();
		}
	
	//Cambio a vista PanelVistaPrincipal, el Usuario ya se ha registrado/loggeado
	public void usuarioCorrecto() {
		setContentPane(new PanelVistaPrincipal());
		pack();
		setVisible(true);
	}
	
	//Cambio ha vista de Login
	public void cambioLogin() {
		setContentPane(pLogin);
		pack();
		setVisible(true);
	}
	
	//Cambio a vista de registro
	public void cambioRegistro() {
		setContentPane(pRegistrar);
		pack();
		setVisible(true);
	}
	
	
}
