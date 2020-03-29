package vista;

import java.awt.Image;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controlador.ControladorAppChat;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelVisorEstado extends JFrame {

	private JPanel panelEstado;
	private JLabel lblImagenEstado;
	private JTextField textField;
	private JPanel contentPane;
	public PanelVisorEstado(PanelEstado ventana) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 286, 424);
		setTitle("Crear Estado");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		String url = elegirArchivo();
		if(url.equals(""))
			return;
		
		visualizarImagen(url);
	}
	
	private String elegirArchivo() {
		//Creamos el objeto JFileChooser
		JFileChooser fc=new JFileChooser();				 
		//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion=fc.showOpenDialog(new JPanel());
		//Le indicamos el filtro		
		//FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png"); 
		//fc.setFileFilter(filtro);
		//Si el usuario, pincha en aceptar
		if(seleccion==JFileChooser.APPROVE_OPTION){
			//Seleccionamos el fichero
			File fichero=fc.getSelectedFile();
						 
			//Ecribe la ruta del fichero seleccionado en el campo de texto
			return fichero.toString();
		}
		return "";
	}
	
	private void visualizarImagen(String imagen) {
		contentPane.setLayout(null);
		panelEstado = new JPanel();
		panelEstado.setBounds(0, 0, 286, 424);
		contentPane.add(panelEstado);
		panelEstado.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 255, 255));
		panel.setBounds(0, 223, 274, 150);
		panelEstado.add(panel);
		panel.setLayout(null);
		
		JLabel lblEscribeTuEstado = new JLabel("Escribe tu estado");
		lblEscribeTuEstado.setBounds(12, 13, 98, 16);
		panel.add(lblEscribeTuEstado);
		
		textField = new JTextField();
		textField.setBounds(12, 37, 238, 100);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(153, 9, 97, 25);
		panel.add(btnAceptar);
		
		btnAceptar.addActionListener(e -> {
			String comentario = textField.getText().trim();
			
			ControladorAppChat.getUnicaInstancia().crearEstado(imagen, comentario);
			
			contentPane.setVisible(false);
			dispose();
			
		});
		
		lblImagenEstado = new JLabel();
		lblImagenEstado.setBounds(0, 0, 274, 373);
		panelEstado.add(lblImagenEstado);
		
		ImageIcon imIco = new ImageIcon(imagen);
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(286, 424, Image.SCALE_SMOOTH));
		lblImagenEstado.setIcon(imIco);
	}
}
