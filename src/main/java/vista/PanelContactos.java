package vista;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlador.ControladorAppChat;
import modelo.Contacto;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.Rectangle;
import java.awt.Component;

@SuppressWarnings("serial")
public class PanelContactos extends JPanel implements  PropertyChangeListener{
	
	private JLabel rotulo;
	private JPanel contactos;
	private PanelVistaPrincipal ventana;
	
	public PanelContactos(PanelVistaPrincipal v) {
		ventana = v;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		setSize(315,480);
		setMaximumSize(new Dimension(315,480));
		
		crearPanel();
		
		anyadirContactos();
		
		//Anyadimos el panel como listener para eliminar/crear nuevo Contacto
		ControladorAppChat.getUnicaInstancia().addUsuarioChangeListener(this);
	}
	
	private void crearPanel() {
		setLayout(null);
		rotulo = new JLabel("Últimos Contactos", JLabel.CENTER);
		rotulo.setBounds(0, 7, 315, 30);
		fixedSize(rotulo, 315, 30);
		rotulo.setFont(new Font("Arial",Font.BOLD,16));
		add(rotulo);
		
		contactos = new JPanel();
		contactos.setAlignmentX(Component.LEFT_ALIGNMENT);
		contactos.setBounds(new Rectangle(0, 0, 250, 250));
		fixedSize(contactos, 315, 410);
		JScrollPane scroll = new JScrollPane(contactos,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(new Rectangle(0, 38, 315, 442));
		contactos.setLayout(new BoxLayout(contactos, BoxLayout.Y_AXIS));
		fixedSize(scroll, 315, 410);
		add(scroll);
		
	}
	
	private void anyadirContactos() {
		List<Contacto> contactosUsuario = ControladorAppChat.getUnicaInstancia().getContactos();
		
		contactosUsuario.stream()
		.forEach(c -> {
			anyadirContacto(c);
		});
	}
	
	private void anyadirContacto(Contacto c) {
		JPanel aux = new PanelContacto(c, ventana);
		contactos.add(aux);
		contactos.revalidate();
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String evento = e.getPropertyName();
		if(evento.equals("contacto")) {
			Contacto nContacto = (Contacto) e.getNewValue();
			if(nContacto != null) {
				anyadirContacto(nContacto);
			}
		}
		
	}
	

}
