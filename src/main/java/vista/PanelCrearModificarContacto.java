package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorAppChat;
import modelo.ContactoIndividual;

import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelCrearModificarContacto extends JPanel {
	
	private JLabel rotulo;
	
	private JPanel datosContacto;
	private JLabel lNombre, lNumeroTelefono;
	private JTextField tNombre, tNumeroTelefono;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private Component horizontalStrut;
	
	private JLabel lAlerta1, lAlerta2,lAlerta3, lAlerta4;
	
	private ControladorAppChat controlador;
	private PanelVistaPrincipal ventana;
	
	//SII == 0, es un Contacto a crear;
	private int modificar;
	private ContactoIndividual contacto;
	
	public PanelCrearModificarContacto(PanelVistaPrincipal v) {
		Dimension d = new Dimension(315, 480);
		setSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		
		ventana = v;
		controlador = ControladorAppChat.getUnicaInstancia();
		modificar = 0;
		crearPanel();
	}
	
	public PanelCrearModificarContacto(PanelVistaPrincipal v, ContactoIndividual c) {
		this(v);
		contacto = c;
		modificar = 1;
		initModificar();
		
	}
	
	private void crearPanel() {
		setLayout(new BorderLayout(0, 0));
		
		rotulo = new JLabel("Registrar Contacto", JLabel.CENTER);			
		fixedSize(rotulo, 315, 60);
		rotulo.setFont(new Font("Arial",Font.BOLD,32));
		add(rotulo, BorderLayout.NORTH);
		
		datosContacto = new JPanel();
		add(datosContacto, BorderLayout.CENTER);
		fixedSize(datosContacto, 315, 420);
		
		lNombre = new JLabel("Nombre");
		fixedSize(lNombre, 150, 24);
		lNumeroTelefono = new JLabel("Numero de Telefono");
		fixedSize(lNumeroTelefono, 150, 24);
		tNombre = new JTextField();
		fixedSize(tNombre, 150, 24);
		tNumeroTelefono = new JTextField();
		fixedSize(tNumeroTelefono, 150, 24);
		datosContacto.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setSize(new Dimension(315, 40));
		rigidArea_1.setPreferredSize(new Dimension(315, 40));
		rigidArea_1.setMinimumSize(new Dimension(315, 40));
		rigidArea_1.setMaximumSize(new Dimension(315, 40));
		datosContacto.add(rigidArea_1);
		
		datosContacto.add(lNombre); datosContacto.add(tNombre);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setSize(new Dimension(315, 40));
		rigidArea.setPreferredSize(new Dimension(315, 40));
		rigidArea.setMinimumSize(new Dimension(315, 40));
		rigidArea.setMaximumSize(new Dimension(315, 40));
		datosContacto.add(rigidArea);
		datosContacto.add(lNumeroTelefono); datosContacto.add(tNumeroTelefono);
 
		rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_2.setSize(new Dimension(315, 150));
		rigidArea_2.setPreferredSize(new Dimension(315, 150));
		rigidArea_2.setMinimumSize(new Dimension(315, 150));
		rigidArea_2.setMaximumSize(new Dimension(315, 150));
		datosContacto.add(rigidArea_2);
 
		btnAceptar = new JButton("Aceptar");
		datosContacto.add(btnAceptar);
 
		horizontalStrut = Box.createHorizontalStrut(20);
		datosContacto.add(horizontalStrut);
 
		btnCancelar = new JButton("Cancelar");
		datosContacto.add(btnCancelar);
		
		lAlerta1 = new JLabel("Los Campos son Obligatorios");
		lAlerta1.setForeground(Color.RED);
		fixedSize(lAlerta1, 315, 30);
		lAlerta1.setVisible(false);
		
		lAlerta2 = new JLabel("Contacto con el mismo numero");
		lAlerta2.setForeground(Color.RED);
		fixedSize(lAlerta2, 315, 30);
		lAlerta2.setVisible(false);
		
		lAlerta3 = new JLabel("Contacto con el mismo nombre");
		lAlerta3.setForeground(Color.RED);
		fixedSize(lAlerta3, 315, 30);
		lAlerta3.setVisible(false);
		
		lAlerta4 = new JLabel("No existe Ningun Usuario con ese numero de telefono");
		lAlerta4.setForeground(Color.RED);
		fixedSize(lAlerta4, 315, 30);
		lAlerta4.setVisible(false);
		
		
		datosContacto.add(lAlerta1); datosContacto.add(lAlerta2);
		datosContacto.add(lAlerta3); datosContacto.add(lAlerta4);
		
		btnAceptar.addActionListener(e -> {
			if(check()) {
				String nombre = tNombre.getText().trim();
				int movil = Integer.valueOf(tNumeroTelefono.getText().trim());
				if(modificar == 0) {
					ContactoIndividual ci = controlador.crearCI(nombre, movil);
					ventana.contactoSeleccionado(ci);
				}else {
					ContactoIndividual ci = controlador.modificarCI(nombre, movil,contacto);
					ventana.contactoSeleccionado(ci);
				}
			}
				
		});
		
		btnCancelar.addActionListener(e -> {
			ventana.cancelar();
		});
		
	}
	
	//Chequea si se puede crear el ContactoIndividual
	private boolean check() {
		lAlerta1.setVisible(false);lAlerta2.setVisible(false);
		lAlerta3.setVisible(false);lAlerta4.setVisible(false);
		
		
		String nombre = tNombre.getText().trim();
		String movilS = tNumeroTelefono.getText().trim();
		int movil = Integer.valueOf(movilS);
		
		if(nombre.isEmpty() || movilS.isEmpty()) {
			lAlerta1.setVisible(true);
			return false;
		}
		if(!controlador.existeUsuario(movil)) {
			lAlerta4.setVisible(true);
			return false;
		}
		if(controlador.existeContacto(nombre) && modificar == 0) {
			lAlerta3.setVisible(true);
			return false;
		}
		if(controlador.existeContacto(movil)&& modificar == 0) {
			lAlerta2.setVisible(true);
			return false;
		}
		return true;
		
	}
	
	
	//Metodo que escribe los datos del Contacto
	private void initModificar() {
		rotulo.setText("Modificar Contacto");
		String nombre = contacto.getNombre();
		int numero = contacto.getMovil();
		tNombre.setText(nombre);
		tNumeroTelefono.setText(numero + "");
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

}