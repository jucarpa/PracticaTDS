package vista;

import javax.swing.JPanel;

public class PanelVistaPrincipal extends JPanel{
	VentanaMain ventana;
	public PanelVistaPrincipal(VentanaMain v) {
		ventana = v;
		crearPantalla();
	}
}
