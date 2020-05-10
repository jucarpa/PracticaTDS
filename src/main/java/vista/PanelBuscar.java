package vista;

import java.awt.Dimension;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorAppChat;
import modelo.BuscarCompuesto;
import modelo.BuscarFechaFin;
import modelo.BuscarFechaIni;
import modelo.BuscarTexto;
import modelo.BuscarUsuario;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.IBuscar;
import modelo.Mensaje;
import modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class PanelBuscar extends JPanel {
	private JPanel panel;
	private JTextField textField;
	private JTextField textField_1;
	ControladorAppChat controlador;
	private PanelVistaPrincipal ventana;
	public PanelBuscar(PanelVistaPrincipal v) {
		ventana = v;
		controlador = ControladorAppChat.getUnicaInstancia();
		Dimension d = new Dimension(315, 480);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 13, 291, 175);
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lNombeUsuario = new JLabel("Nombre Usuario:");
		panel_2.add(lNombeUsuario);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JLabel lblTexto = new JLabel("Texto:");
		panel_3.add(lblTexto);
		
		textField_1 = new JTextField();
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		JLabel lblFechaini = new JLabel("Fecha Ini:");
		panel_4.add(lblFechaini);
		
		JDateChooser dateChooser = new JDateChooser();
		panel_4.add(dateChooser);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		JLabel label = new JLabel("Fecha Fin:");
		panel_5.add(label);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		panel_5.add(dateChooser_1);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		
		JButton btnCancelar = new JButton("Cancelar");
		panel_6.add(btnCancelar);
		
		JButton btnAceptar = new JButton("Aceptar");
		panel_6.add(btnAceptar);
		
		
		//Si es un ContactoIndividual, no buscamos por Usuario
		if(!controlador.isGrupo())
			panel_2.setVisible(false);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 315, 480);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(0,0,315,480);
		add(scroll);
		
		
		//Volvemos al PanelChat
		btnCancelar.addActionListener(e -> {
			//Volvemos al Chat
			ventana.cancelar();
		});
		
		btnAceptar.addActionListener(e -> {
			String nombreUsuario = textField.getText().trim();
			String texto = textField_1.getText().trim();
			Date ini = dateChooser.getDate();
			Date fin = dateChooser_1.getDate();
			
			panel_1.setVisible(false);
			panel_1.revalidate();
			accionAceptar(nombreUsuario, texto, ini, fin);
		});
	}
	
	//Todos pueden ser null
	private void accionAceptar(String nU, String t, Date ini, Date fin) {
		IBuscar buscar = new BuscarCompuesto();
		if (!nU.isEmpty()) {			
			if(controlador.esContacto())
				buscar.add(new BuscarUsuario(controlador.getUsuario()));
		}
		
		if(!t.isEmpty()) {
			buscar.add(new BuscarTexto(t));
		}
		
		if(ini != null) {
			buscar.add(new BuscarFechaIni(ini));
		}
		if(fin != null)  {
			buscar.add(new BuscarFechaFin(fin));
		}
		
		List<Mensaje> sol = controlador.buscar(buscar);
		sol.stream().forEach(m -> anyadirMensaje(m));
	}
	
	//Anyade el Mensaje
	private void anyadirMensaje(Mensaje m) {
		PanelMensajeBuscado pMB = new PanelMensajeBuscado(m);
		panel.add(pMB);
	}
}
