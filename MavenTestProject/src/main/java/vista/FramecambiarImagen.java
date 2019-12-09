package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorAppChat;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class FramecambiarImagen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	public FramecambiarImagen(PanelVistaPrinciaplScene ventana) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//Creamos el objeto JFileChooser
		JFileChooser fc=new JFileChooser();
		 
		//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion=fc.showOpenDialog(contentPane);
		 
		//Si el usuario, pincha en aceptar
		if(seleccion==JFileChooser.APPROVE_OPTION){
		 
		    //Seleccionamos el fichero
		    File fichero=fc.getSelectedFile();
		 
		    //Ecribe la ruta del fichero seleccionado en el campo de texto
		    String url = "";
			try {
				url = fichero.toURI().toURL().toExternalForm();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    ControladorAppChat.getUnicaInstancia().actualizarImagen(url,ventana.getMovilUA());
		}
		
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png");
		 
		//Le indicamos el filtro
		fc.setFileFilter(filtro);
	}
}
