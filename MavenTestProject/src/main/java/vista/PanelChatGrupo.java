package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tds.BubbleText;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.border.LineBorder;

import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;

public class PanelChatGrupo extends JPanel {
	private JTextField textField;
	private ObservableList<Mensaje> mensajes ;
	private ListView<String> listViewMensajes;
	private Contacto c;
	private Mensaje ultMensaje = null;
	private JPanel panel;
	private Grupo contacto ;
	private int movilUA;
	private int nMensajes;
	/**
	 * Create the panel.
	 */
	public PanelChatGrupo(Grupo c, int movilUA) {
		
		ActualizarBBDD.getUnicaInstancia().addPanelChatG(this);
		
		this.contacto = c;
		this.movilUA = movilUA;
		setOpaque(false);
		setBackground(new Color(0, 0, 0));
		setMaximumSize(new Dimension(355, 300));
		setMinimumSize(new Dimension(355, 300));
		setPreferredSize(new Dimension(355, 406));
		setLayout(null);
		
		ImageIcon chat = new ImageIcon(VentanaMain.class.getResource("/imagenes/imageneFondo.png"));
		Image i = chat.getImage();
		chat = new ImageIcon(i.getScaledInstance(353, 660, Image.SCALE_SMOOTH));
		ImageIcon emoji= new ImageIcon(VentanaMain.class.getResource("/imagenes/ImagenEmojiDef.png"));
		i = emoji.getImage();
		emoji= new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setMinimumSize(new Dimension(350, 30));
		panel_1.setPreferredSize(new Dimension(350, 30));
		panel_1.setBounds(0, 373, 353, 33);
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton btnEmoji = new JButton("");
		btnEmoji.setMaximumSize(new Dimension(30, 30));
		btnEmoji.setMinimumSize(new Dimension(30, 30));
		btnEmoji.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEmoji.setBackground(new Color(152, 251, 152));
		btnEmoji.setPreferredSize(new Dimension(30, 30));
		panel_1.add(btnEmoji);
		btnEmoji.setIcon(emoji);
		
		textField = new JTextField();
		textField.setMaximumSize(new Dimension(2147483647, 30));
		textField.setMinimumSize(new Dimension(6, 30));
		textField.setPreferredSize(new Dimension(6, 30));
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEnviar.setBackground(Color.WHITE);
		btnEnviar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEnviar.setMinimumSize(new Dimension(75, 30));
		btnEnviar.setMaximumSize(new Dimension(75, 30));
		btnEnviar.setPreferredSize(new Dimension(75, 30));
		panel_1.add(btnEnviar);
		
		panel = new JPanel();
		//add(panel);
		panel.setAutoscrolls(true);
		panel.setOpaque(false);
		panel.setBounds(2, 0, 325, 370);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		BoxLayout bL = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(bL);
		
		JScrollPane scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setOpaque(false);
		scrollPane.setBounds(0, 0, 351, 81);
		scrollPane.setSize(new Dimension(351, 375));
		scrollPane.setPreferredSize(new Dimension(351, 375));
		scrollPane.setMinimumSize(new Dimension(351, 375));
		scrollPane.setMaximumSize(new Dimension(351, 375));
		add(scrollPane);
		
		JLabel label = new JLabel("");
		label.setOpaque(true);
		label.setBounds(0, 0, 353, 403);
		add(label);
		label.setIcon(chat);
		
		
		
		
		btnEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("")) {
					Mensaje aux = null;
					aux = ControladorAppChat.getUnicaInstancia().registrarMensajeG(textField.getText(),
							0, c.getId(), movilUA);
					BubbleText mensaje = new BubbleText(panel, textField.getText(), Color.GREEN,
							"", BubbleText.SENT);
					panel.add(mensaje);
					ultMensaje = aux;
					textField.setText("");
				}}});
		
		nMensajes = c.getMensajes().size();
		Grupo g = (Grupo) c;
		Usuario actual = ControladorAppChat.getUnicaInstancia().getUsuario(movilUA);
		for(Mensaje m : g.getMensajes()) {
			BubbleText mensaje;
			ultMensaje = m;
			if(m.getEmisor().getIdUsuario() == actual.getIdUsuario()) {
				 mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN, "", BubbleText.SENT);
			} else {
				ContactoIndividual aux = ControladorAppChat.getUnicaInstancia().
					getUsuario(movilUA).getCIPorNumero(m.getEmisor().getMovil());
					if(aux != null) {
						mensaje = new BubbleText(panel, m.getTexto(),
					Color.GREEN, aux.getNombre(), BubbleText.RECEIVED);
					}else
						mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN, String.valueOf(m.getEmisor().getMovil()), BubbleText.RECEIVED);
					
			}
			panel.add(mensaje);
		}
	}
	
	public void update() {
		Grupo aux = ControladorAppChat.getUnicaInstancia().getGrupo(contacto.getNombre(), movilUA);
		if(aux.getMensajes().size() != nMensajes) {
		boolean nuevo = false;
		for(Mensaje m : aux.getMensajes()) {
			if(nuevo) {
				BubbleText mensaje = null;
				ContactoIndividual auxCI = ControladorAppChat.getUnicaInstancia().
						getUsuario(movilUA).getCIPorNumero(m.getEmisor().getMovil());
						if(auxCI != null) {
							mensaje = new BubbleText(panel, m.getTexto(),
						Color.GREEN, auxCI.getNombre(), BubbleText.RECEIVED);
						}else
							mensaje = new BubbleText(panel, m.getTexto(),
							Color.GREEN, String.valueOf(m.getEmisor().getMovil()), BubbleText.RECEIVED);
			panel.add(mensaje);
			ultMensaje = m;
			nMensajes++;
			}
			if(m.getId() == ultMensaje.getId())
				nuevo = true;
		}
		}
	}
}