package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controlador.ControladorAppChat;
import modelo.ContactoIndividual;
import modelo.Grupo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PCrearGrupo extends JPanel {
	private JTextField textField;
	private PVistaPrincipal ventana;
	private JTable table;
	private JTable table_1;
	private Map<String, ContactoIndividual>contactosseleccionados =  new HashMap<String, ContactoIndividual>();
	private int movilUA;
	/**
	 * Create the panel.
	 */
	public PCrearGrupo(PVistaPrincipal ventana, int movilUA) {
		this.movilUA = movilUA;
		setPreferredSize(new Dimension(370, 450));
		setSize(new Dimension(370, 450));
		setMinimumSize(new Dimension(370, 450));
		setMaximumSize(new Dimension(370, 450));
		this.ventana = ventana;
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(184, 301, 116, 22);
		add(textField);
		textField.setColumns(10);
		
		JPanel panelContactosAnadidos = new JPanel();
		panelContactosAnadidos.setBounds(184, 13, 150, 275);
		add(panelContactosAnadidos);
		panelContactosAnadidos.setLayout(new BorderLayout(0, 0));
		
		JLabel lblContactosAadidos = new JLabel("Contactos A\u00F1adidos");
		panelContactosAnadidos.add(lblContactosAadidos, BorderLayout.NORTH);
		lblContactosAadidos.setHorizontalAlignment(SwingConstants.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column"
			}
		));
		panelContactosAnadidos.add(table, BorderLayout.CENTER);
		
		JPanel panelContactos = new JPanel();
		panelContactos.setBounds(12, 13, 150, 275);
		add(panelContactos);
		panelContactos.setLayout(new BorderLayout(0, 0));
		
		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setHorizontalAlignment(SwingConstants.CENTER);
		panelContactos.add(lblContactos, BorderLayout.NORTH);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column"
			}
		));
		panelContactos.add(table_1, BorderLayout.CENTER);
		
		JButton button = new JButton("-->");
		button.setBounds(184, 336, 97, 25);
		add(button);
		
		JButton button_1 = new JButton("<--");
		button_1.setBounds(184, 374, 97, 25);
		add(button_1);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(56, 330, 97, 36);
		add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(56, 374, 97, 25);
		add(btnCancelar);
		
		JLabel lblNombreDeGrupo = new JLabel("Nombre De Grupo:");
		lblNombreDeGrupo.setBounds(56, 304, 176, 16);
		add(lblNombreDeGrupo);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if(textField.getText().equals("")) {
			JOptionPane.showMessageDialog(ventana, "El grupo debe tener un nombre", "Registrar Grupo",
					JOptionPane.PLAIN_MESSAGE);
			} else {
				ArrayList<ContactoIndividual> contactos = new ArrayList<ContactoIndividual>(contactosseleccionados.values());
				ArrayList<Integer> contactosCod = new ArrayList<Integer>();
				for(ContactoIndividual c : contactos) {
					contactosCod.add(c.getMovil());
				}
				Grupo c = ControladorAppChat.getUnicaInstancia().registrarGrupo(textField.getText(), contactosCod, movilUA);
				JOptionPane.showMessageDialog(ventana, "Grupo Registrado", "Registrar Grupo",
						JOptionPane.PLAIN_MESSAGE);
				ventana.setContactoSeleccionado(c, 2);
				ventana.addPanelContacto(c, 2);
				
				}
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelContacto(2);
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				int y =table_1.getSelectedRow();
				String aux =(String) model.getValueAt(y, 0);
				model.removeRow(y);
				DefaultTableModel model1 = (DefaultTableModel) table.getModel();
				model1.addRow(new Object[]{aux});
				ContactoIndividual c = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA).getContactoIndividualPorNombre(aux);
				contactosseleccionados.put(aux, c);
				System.out.println(c.getNombre());
				
			}
		});
		
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int y =table.getSelectedRow();
				String aux =(String) model.getValueAt(y, 0);
				model.removeRow(y);
				DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
				model1.addRow(new Object[]{aux});

				contactosseleccionados.remove(aux);
				
			}
		});
	}
	
	public void update() {
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		for(ContactoIndividual c : ControladorAppChat.getUnicaInstancia().getUsuario(movilUA).getContactosIndividuales()) {
			contactos.add(c);
			DefaultTableModel model = (DefaultTableModel) table_1.getModel();
			model.addRow(new Object[]{c.getNombre()});
		}
	}
}
