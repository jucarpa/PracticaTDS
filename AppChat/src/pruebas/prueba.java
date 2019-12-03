package pruebas;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class prueba {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prueba window = new prueba();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public prueba() {
		/*
		 * ServicioPersistencia servPersistencia =
		 * FactoriaServicioPersistencia.getInstance(). getServicioPersistencia();
		 * for(Entidad e: servPersistencia.recuperarEntidades())
		 * servPersistencia.borrarEntidad(e);
		 */
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setMinimumSize(new Dimension(20, 20));
		lblNewLabel.setMaximumSize(new Dimension(20, 20));
		lblNewLabel.setSize(new Dimension(20, 20));
		frame.getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		ImageIcon imagen = new ImageIcon(prueba.class.getResource("/imagenes/ImagenUsuarioDef.png"));
		Image image = imagen.getImage();
		imagen = new ImageIcon(image.getScaledInstance(128, 128, Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(imagen);
		System.out.println(imagen.getIconHeight() + "," + imagen.getIconWidth()); // 256,256
		lblNewLabel.repaint();
	}

}
