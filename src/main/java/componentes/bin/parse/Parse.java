package componentes.bin.parse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import componentes.bin.parse.modelo.Plataforma;

@SuppressWarnings("serial")
public class Parse implements Serializable{
	
	
	//Vector con oyentes
	@SuppressWarnings("rawtypes")
	private Vector parseListeners = new Vector();
	private File fichero = null; //Fichero Donde se Encuentra Texto a Parsear
	
	public Parse() {
	}
	
	//Anyadir Oyentes
	@SuppressWarnings("unchecked")
	public synchronized void addParseListener(IParseListener listener){
		parseListeners.addElement(listener);
	}
	
	//Eliminar Oyentes
	public synchronized void removeParseListener(IParseListener listener){
		parseListeners.removeElement(listener);
	}
	
	//Notificar oyentes
	@SuppressWarnings({ "rawtypes" })
	private void notificarParse(ParseEvent evento){
		Vector lista;
		synchronized(this){
			lista=(Vector) parseListeners.clone();
		}
		for(int i=0; i<lista.size(); i++){
			IParseListener listener=(IParseListener) lista.elementAt(i);
			listener.enteradoParse(evento);
		}
	}
	
	
	//Dibujamos cuadro de seleccion de fichero y Plataforma
	public void paint() {
		fichero = null;
		JPanel contentPane = new JPanel();
		JFileChooser fc=new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt"); 
		fc.setFileFilter(filtro);
		int seleccion=fc.showOpenDialog(contentPane);
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
			btnAceptar.addActionListener(e -> {
				Plataforma p = null;
				String formatDate = "";
				if(rdbtnIos.isSelected()) {
					p = Plataforma.IOS;
					formatDate = "d/M/yy H:mm:ss";
				}
				else if(rdbtnAndroid.isSelected()) {
					p = Plataforma.ANDROID;
					formatDate = "d/M/yy H:mm";
				}
				
				ParseEvent evento = new ParseEvent(this, fichero.toString(), formatDate ,p); 
				notificarParse(evento);
				
				jFrame.setVisible(false);
			});
		
			rdbtnIos.addActionListener(e -> {
				if(rdbtnIos.isSelected()) {
					rdbtnAndroid.setSelected(false);
					btnAceptar.setEnabled(true);
				} else {
					btnAceptar.setEnabled(false);
				}
			});
		
			rdbtnAndroid.addActionListener(e -> {
				if(rdbtnAndroid.isSelected()) {
					rdbtnIos.setSelected(false);
					btnAceptar.setEnabled(true);
				} else {
					btnAceptar.setEnabled(false);
				}
			});
		}
	}
}
