package componentes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import componentes.parserwpp.modeloParse.Plataforma;
import controlador.ControladorAppChat;
import controlador.ControladorCargadorMensaje;
import vista.Constantes;

public class EncendidoEvent extends EventObject {

	/**
	 * 
	 */
	protected boolean oldEncendido, newEncendido;
	protected int movilUA, movilCI;
	protected File fichero;
	public EncendidoEvent(Object fuente, boolean anterior, boolean nuevo, int movilUA, int movilCI) {
		super(fuente);
		newEncendido=nuevo;
		oldEncendido=anterior;
		this.movilUA = movilUA;
		this.movilCI = movilCI;
		if(nuevo == true)
			eventoCargarMensaje();
	}
	public boolean getNewEncendido(){ 
		return newEncendido;
	}
	public boolean getOldEncendido(){ 
		return oldEncendido;
	}
	
	private void eventoCargarMensaje() {
		JPanel contentPane = new JPanel();
		JFileChooser fc=new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt"); 
		fc.setFileFilter(filtro);
		int seleccion=fc.showOpenDialog(contentPane);
		fichero = null;
		if(seleccion==JFileChooser.APPROVE_OPTION){
		fichero=fc.getSelectedFile();
		}
		if(fichero != null) {
		JFrame jFrame= new JFrame();
		jFrame.setTitle("Selección de Plataforma");
	    jFrame.setSize(200, 200);
	    jFrame.setLayout(new BorderLayout(0, 0));
	    jFrame.setVisible(true);
	    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jFrame.setResizable(false);
	    JPanel panel = new JPanel();
		jFrame.add(panel, BorderLayout.CENTER);
		
		JRadioButton rdbtnIos = new JRadioButton("IOS");
		rdbtnIos.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		rdbtnIos.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(rdbtnIos);
		
		JRadioButton rdbtnAndroid = new JRadioButton("ANDROID");
		rdbtnAndroid.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		rdbtnAndroid.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(rdbtnAndroid);
		
		JPanel panel_1 = new JPanel();
		jFrame.add(panel_1, BorderLayout.SOUTH);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.add(new JLabel("Seleccione una Plataforma"), BorderLayout.NORTH);
		
		JButton btnAceptar = new JButton("ACEPTAR");
		panel_1.add(btnAceptar);
		btnAceptar.setEnabled(false);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Plataforma p = null;
				if(rdbtnIos.isSelected()) {
					p = Plataforma.IOS;
					ControladorAppChat.getUnicaInstancia().cargarMensajes(fichero.toString(), p, movilUA, movilCI);
				}
				else if(rdbtnAndroid.isSelected()) {
					p = Plataforma.ANDROID;
					ControladorAppChat.getUnicaInstancia().cargarMensajes(fichero.toString(), p, movilUA, movilCI);
				}
				jFrame.setVisible(false);
			}
		});
		
		rdbtnIos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnIos.isSelected()) {
					rdbtnAndroid.setSelected(false);
					btnAceptar.setEnabled(true);
				} else {
					btnAceptar.setEnabled(false);
				}
				
			}
		});
		
		rdbtnAndroid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnAndroid.isSelected()) {
					rdbtnIos.setSelected(false);
					btnAceptar.setEnabled(true);
				} else {
					btnAceptar.setEnabled(false);
				}
				
			}
		});
		}
		
	}
}
