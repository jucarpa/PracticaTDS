package vista;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import modelo.Mensaje;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
@SuppressWarnings("serial")
public class PanelMensajeBuscado extends JPanel {

	public PanelMensajeBuscado(Mensaje mensaje) {
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Dimension d = new Dimension(315, 125);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setLayout(null);
		
		JLabel lblEnviadoPor = new JLabel("Enviado Por:");
		lblEnviadoPor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnviadoPor.setBounds(12, 13, 81, 16);
		add(lblEnviadoPor);
		
		JLabel lblNombreEmisor = new JLabel("NOMBRE EMISOR");
		lblNombreEmisor.setBounds(98, 13, 217, 16);
		add(lblNombreEmisor);
		
		JLabel lblEnviadoElDa = new JLabel("Enviado el día:");
		lblEnviadoElDa.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnviadoElDa.setBounds(12, 34, 99, 16);
		add(lblEnviadoElDa);
		
		JLabel lblDiaEnvio = new JLabel("DIA ENVIO");
		lblDiaEnvio.setBounds(108, 34, 195, 16);
		add(lblDiaEnvio);
		
		JLabel lblTexto = new JLabel("Texto:");
		lblTexto.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTexto.setBounds(12, 50, 56, 16);
		add(lblTexto);
		
		JLabel lblTextoEnviado = new JLabel("TEXTO");
		lblTextoEnviado.setBackground(Color.LIGHT_GRAY);
		lblTextoEnviado.setBounds(12, 63, 291, 49);
		add(lblTextoEnviado);
		
		lblNombreEmisor.setText(mensaje.getEmisor().getNombre());
		lblDiaEnvio.setText(mensaje.getHora().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
		lblTextoEnviado.setText(mensaje.getTexto()); 
	}
}
