package pruebas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class PruebasImagen extends JFrame {

	private JPanel contentPane;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebasImagen frame = new PruebasImagen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PruebasImagen() {
		PruebasImagen prueba = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		label = new JLabel("");
		getContentPane().add(label);
		
		ImageIcon image= new ImageIcon("file:/D:/UNIVERSIDAD/CURSO%2019-20/TDS/PRACTICA%20FINAL%20GIT/MavenTestProject/src/main/java/imagenes/ImagenUsuarioDef.png");
		label.setIcon(image);
		
		JButton btnBuscarimagen = new JButton("BuscarImagen");
		contentPane.add(btnBuscarimagen, BorderLayout.NORTH);
		
		btnBuscarimagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PruebaBusquedaImagen(prueba);
				
			}
		});		
	}
	
	public void cambiaIMagen(String url) {
		System.out.println("LLEGA");
	ImageIcon aux = new ImageIcon(url);
	label.setIcon(aux);
	}

}
