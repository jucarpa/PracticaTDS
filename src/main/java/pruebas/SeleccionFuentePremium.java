package pruebas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class SeleccionFuentePremium extends JPanel {


	public SeleccionFuentePremium() {
		
		JLabel lblEstoEsUna = new JLabel("Esto Es Una Prueba De Texto");
		lblEstoEsUna.setForeground(new Color(240, 230, 140));
		lblEstoEsUna.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		add(lblEstoEsUna);

	}

}
