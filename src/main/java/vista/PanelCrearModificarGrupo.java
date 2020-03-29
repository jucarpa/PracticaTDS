package vista;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import controlador.ControladorAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PanelCrearModificarGrupo extends JPanel {
	
	private PanelVistaPrincipal ventana;
	private JTextField textNombreGrupo;
	private JTable tablaIzq, tablaDer;
	private JButton btnDerecha, btnIzquierda, btnAceptar, btnCancelar;
	
	private int modificar;
	private Grupo grupo;
	
	public PanelCrearModificarGrupo(PanelVistaPrincipal v) {
		ventana = v;
		modificar = 0;
		
		Dimension d = new Dimension(315,480);
		setSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setLayout(null);
		
		crearPanel();
		iniciar();
	}
	
	public PanelCrearModificarGrupo(PanelVistaPrincipal v, Grupo g) {
		this(v);
		grupo = g;
		modificar = 1;
		initModificado();
	}
	
	private void crearPanel() {

		JPanel panelDerecho = new JPanel();
		panelDerecho.setBackground(Color.WHITE);
		panelDerecho.setBounds(178, 13, 125, 244);
		add(panelDerecho);
		panelDerecho.setLayout(new BorderLayout(0, 0));
		
		JLabel lblContactosSeleccionados = new JLabel("Contactos Seleccionados");
		lblContactosSeleccionados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblContactosSeleccionados.setHorizontalAlignment(SwingConstants.CENTER);
		panelDerecho.add(lblContactosSeleccionados, BorderLayout.NORTH);
		
		tablaDer= new JTable();
		tablaDer.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column"
			}
		));
		panelDerecho.add(tablaDer, BorderLayout.CENTER);
		
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.setBounds(12, 13, 125, 244);
		add(panelIzquierdo);
		panelIzquierdo.setLayout(new BorderLayout(0, 0));
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setHorizontalAlignment(SwingConstants.CENTER);
		panelIzquierdo.add(lblContactos, BorderLayout.NORTH);
		
		tablaIzq= new JTable();
		tablaIzq.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column"
			}
		));
		panelIzquierdo.add(tablaIzq, BorderLayout.CENTER);
		
		btnDerecha = new JButton("->");
		btnDerecha.setBounds(106, 270, 97, 25);
		add(btnDerecha);
		
		btnIzquierda = new JButton("<-");
		btnIzquierda.setBounds(106, 302, 97, 25);
		add(btnIzquierda);
		
		JLabel lblNombreDelGrupo = new JLabel("Nombre del Grupo:");
		lblNombreDelGrupo.setBounds(12, 340, 114, 16);
		add(lblNombreDelGrupo);
		
		textNombreGrupo = new JTextField();
		textNombreGrupo .setBounds(53, 369, 229, 22);
		add(textNombreGrupo);
		textNombreGrupo.setColumns(10);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(106, 404, 97, 25);
		add(btnAceptar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(206, 442, 97, 25);
		add(btnCancelar);
		
	}
	
	//Iniciamos las Tablas
	//Creamos los Listeners para lod botones
	private void iniciar() {
		
		//Obtenemos los Contactos
		List<Contacto> contactos = ControladorAppChat.getUnicaInstancia().getContactos();
		DefaultTableModel modelIzq = (DefaultTableModel) tablaIzq.getModel();
		//Para cada contacto que sea un ContactoIndividual
		//Se añade a la tabla de la Izquierda
		contactos.stream()
			.filter(c-> c instanceof ContactoIndividual)
			.forEach(c -> modelIzq.addRow(new Object[]{c.getNombre()}));
		
		DefaultTableModel modelDer = (DefaultTableModel) tablaDer.getModel();
		//Botón Para mover un Contacto de la tabla de la Izquierda a la derecha
		btnDerecha.addActionListener(e -> {
			int y = tablaIzq.getSelectedRow();
			String aux =(String) modelIzq.getValueAt(y, 0);
			modelIzq.removeRow(y);
			
			modelDer.addRow(new Object[]{aux});
		});
		
		//Botón Para movoer un Contacto de la tabla de la Derecha a la Izquierda
		btnIzquierda.addActionListener(e -> {
			int y = tablaDer.getSelectedRow();
			String aux = (String) modelDer.getValueAt(y, 0);
			modelDer.removeRow(y);
			
			modelIzq.addRow(new Object[]{aux});
		});
		
		//Recorremos la tabla de la derecha y Obtenemos todos los Contactos
		btnAceptar.addActionListener(e -> {
			accionAceptar();
		});
		
		btnCancelar.addActionListener(e -> {
			ventana.cancelar();
		});
	}
	
	//Rellenamos la tabla de la derecha con los contactos del Grupo
	//Eliminamos de la derecha los que están en la izquierda;
	private void initModificado() {
		DefaultTableModel modelDer = (DefaultTableModel) tablaDer.getModel();
		List<ContactoIndividual> contactosG = grupo.getContactos();
		contactosG.stream()
		.forEach(c -> modelDer.addRow(new Object[] {c.getNombre()}));
		
		DefaultTableModel modelIzq = (DefaultTableModel) tablaIzq.getModel();
		for(int i = 0; i < modelIzq.getRowCount(); i++) {
			int pos = i;
			String aux = (String) modelDer.getValueAt(pos, 0);
			contactosG.stream()
			.filter(c -> c.esNombre(aux))
			.forEach(c -> modelIzq.removeRow(pos));
		}
		textNombreGrupo.setText(grupo.getNombre());
	}
	
	
	//Metodo para el Botono aceptar
	//Recorremos la tabla de la derecha, obtenemos los contactos
	// y registramos el Grupo
	private void accionAceptar() {
		ArrayList<ContactoIndividual> sol = new ArrayList<ContactoIndividual>();
		DefaultTableModel modelDer = (DefaultTableModel) tablaDer.getModel();
		
		for(int i = 0; i < modelDer.getRowCount(); i++) {
			String nombre = (String) modelDer.getValueAt(i, 0);
			ContactoIndividual aux = ControladorAppChat.getUnicaInstancia().getContacto(nombre);
			sol.add(aux);
		}
		String nombreGrupo = textNombreGrupo.getText().trim();
		
		if(sol.isEmpty() || nombreGrupo.isEmpty()) {
			JOptionPane.showMessageDialog( this, "Nombre de Grupo y Miembros obligatorio",
					"Registrar Grupo",JOptionPane.INFORMATION_MESSAGE);
		} else {
			if(modificar == 0) {
				Grupo g = ControladorAppChat.getUnicaInstancia().crearGrupo(nombreGrupo, sol);
				ventana.contactoSeleccionado(g);
			}
			else {
				Grupo g = ControladorAppChat.getUnicaInstancia().modificarGrupo(nombreGrupo, sol, grupo);
				ventana.contactoSeleccionado(g);
			
			}
		}
	}
}
