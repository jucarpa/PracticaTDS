package vista;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.LineBorder;
import controlador.ControladorAppChat;
import modelo.Estado;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PanelEstado extends JPanel {
	
	private PanelVistaPrincipal ventana;
	private JPanel panel, panelEstado;
	private JButton button_cancelar;
	private JLabel lblComentarioEstado;
	private JLabel lblImagenEstado;
	private JPanel panel_1;
	private JButton btnCrearEstado;
	private JButton volverTodosEstados;
	
	public PanelEstado(PanelVistaPrincipal v) {
		ventana = v;
		
		paint();
		
		addEstados();
		
		button_cancelar.addActionListener( e -> {
			ventana.cancelar();
		});
		
		btnCrearEstado.addActionListener(e -> {
			PanelVisorEstado sol =new PanelVisorEstado(this);
			sol.setVisible(true);
		});
		
		volverTodosEstados.addActionListener(e -> {
			panel.setVisible(true);
			panel.repaint();
			button_cancelar.setVisible(true);
			btnCrearEstado.setVisible(true);
			
			volverTodosEstados.setVisible(false);
			panelEstado.setVisible(false);
			panelEstado.repaint();
		});
	}
	
	private void paint() {
		Dimension d = new Dimension(310,450);
		setMinimumSize(d);
		setSize(d);
		setMaximumSize(d);
		setLayout(null);
		
		volverTodosEstados = new JButton("<");
		volverTodosEstados.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		volverTodosEstados.setBackground(Color.WHITE);
		volverTodosEstados.setFont(new Font("Tahoma", Font.BOLD, 22));
		volverTodosEstados.setBounds(12, 387, 50, 50);
		add(volverTodosEstados);
		
		panelEstado = new JPanel();
		panelEstado.setBounds(12, 13, 286, 424);
		add(panelEstado);
		panelEstado.setLayout(null);
		
		lblComentarioEstado = new JLabel("Comentario Estado");
		lblComentarioEstado.setHorizontalTextPosition(SwingConstants.CENTER);
		lblComentarioEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblComentarioEstado.setBackground(new Color(240, 255, 255));
		lblComentarioEstado.setBounds(0, 321, 286, 52);
		panelEstado.add(lblComentarioEstado);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 255, 255));
		panel_1.setBounds(0, 314, 286, 70);
		panelEstado.add(panel_1);
		
		lblImagenEstado = new JLabel();
		lblImagenEstado.setBounds(0, 0, 286, 424);
		panelEstado.add(lblImagenEstado);
		
		panelEstado.setVisible(false);
		panelEstado.repaint();
		
		button_cancelar = new JButton("<");
		button_cancelar.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		button_cancelar.setBackground(Color.WHITE);
		button_cancelar.setFont(new Font("Tahoma", Font.BOLD, 22));
		button_cancelar.setBounds(12, 387, 50, 50);
		add(button_cancelar);
		
		btnCrearEstado = new JButton("Crear Estado");
		btnCrearEstado.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnCrearEstado.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		btnCrearEstado.setBounds(63, 387, 235, 50);
		add(btnCrearEstado);
		
		panel = new JPanel();
		panel.setBounds(12, 13, 286, 424);
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(new Rectangle(12, 13, 286, 424));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(scroll);
	}
	
	private void addEstados() {
		ControladorAppChat.getUnicaInstancia().getCIconEstado().stream()
		.forEach(ci -> {
			PanelEstadoContacto aux = new PanelEstadoContacto(ci, this);
			panel.add(aux);
		});
	}
	
	public void mostrarEstado(Estado estado) {
		//Dejamos de mostrar la lista de mensajes
		panel.setVisible(false);
		panel.repaint();
		button_cancelar.setVisible(false);
		btnCrearEstado.setVisible(false);
		
		volverTodosEstados.setVisible(true);
		panelEstado.setVisible(true);
		panelEstado.repaint();
		
		//Mostramos Imagen
		String urlImagen = estado.getImagenUrl();
		ImageIcon imIco = new ImageIcon(urlImagen);
		Image im = imIco.getImage();
		imIco = new ImageIcon(im.getScaledInstance(286, 424, Image.SCALE_SMOOTH));
		lblImagenEstado.setIcon(imIco);
		
		
		//Mostramos Comentario
		lblComentarioEstado.setText(estado.getMensaje());
		
		
	}
	
}
