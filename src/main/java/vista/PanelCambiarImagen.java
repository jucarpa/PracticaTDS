package vista;

import java.io.File;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorAppChat;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class PanelCambiarImagen extends JPanel{

	private JPanel contentPane;
	public PanelCambiarImagen() {
		
		//Creamos el objeto JFileChooser
		JFileChooser fc=new JFileChooser();
		 
		//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion=fc.showOpenDialog(contentPane);

		//Le indicamos el filtro		
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png"); 
		fc.setFileFilter(filtro);
		//Si el usuario, pincha en aceptar
		if(seleccion==JFileChooser.APPROVE_OPTION){
		 
		    //Seleccionamos el fichero
		    File fichero=fc.getSelectedFile();
		 
		    //Ecribe la ruta del fichero seleccionado en el campo de texto
		    String url = "";
			url = fichero.toString();
		    ControladorAppChat.getUnicaInstancia().setImagen(url);
		}
	}	
}
