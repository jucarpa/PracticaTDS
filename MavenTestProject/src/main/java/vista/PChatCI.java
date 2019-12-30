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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import java.awt.Graphics;

import javax.swing.border.LineBorder;

import controlador.ActualizarBBDD;
import controlador.ControladorAppChat;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import manejadores.ManejadorChatCI;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class PChatCI extends JPanel {
	private JTextField textField;
	private JPanel panel;
	private int movilContacto ;
	private int movilUA;
	private int nMensajes;
	private Image chatI;
	private ImageIcon chat;
	private int on;
	private PEmoticonos ventanaEmoticonos;
	/**
	 * Create the panel.
	 */
	public PChatCI(ContactoIndividual c, int movilUA) {
		ActualizarBBDD.getUnicaInstancia().addPanelChatCI(this);
		movilContacto = c.getMovil();
		this.movilUA = movilUA;
		setOpaque(false);
		setBackground(new Color(0, 0, 0));
		setMaximumSize(new Dimension(355, 300));
		setMinimumSize(new Dimension(355, 300));
		setPreferredSize(new Dimension(355, 406));
		setLayout(null);
		
		chat = new ImageIcon(VMain.class.getResource("/imagenes/imageneFondo.png"));
		Image i = chat.getImage();
		chatI = i.getScaledInstance(353, 660, Image.SCALE_SMOOTH);
		chat = new ImageIcon(chatI);
		ImageIcon emoji= new ImageIcon(VMain.class.getResource("/imagenes/ImagenEmojiDef.png"));
		i = emoji.getImage();
		emoji= new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		ventanaEmoticonos = new PEmoticonos(this);
		ventanaEmoticonos.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ventanaEmoticonos.setBounds(0, 174, 133, 200);
		add(ventanaEmoticonos);
		
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
		
		JPanel panelAux = new JPanel();
		panelAux.setBounds(0, 0, 353, 403);         
		
		//Inicialización del Chat
		nMensajes = ManejadorChatCI.getUnicaInstancia().initChat(movilContacto, movilUA, panel);
		
		
		//Oyente
		btnEnviar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ManejadorChatCI.getUnicaInstancia().addBubbleText(textField.getText(), movilContacto, movilUA, panel);
				nMensajes += 1;
				textField.setText("");
			}});
		
		textField.addKeyListener(new KeyListener() {	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ManejadorChatCI.getUnicaInstancia().addBubbleText(textField.getText(), movilContacto, movilUA, panel);
					nMensajes += 1;
					textField.setText("");
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		ventanaEmoticonos.setVisible(false);
		on = 0;
		btnEmoji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(on == 0) {
					on = 1;
					ventanaEmoticonos.setVisible(true);
					
				} else {
					on = 0;
					ventanaEmoticonos.setVisible(false);
				}
				
			}
		});
		
	}
	//Actualización de BBDD
	public void update() {
		ContactoIndividual aux = ControladorAppChat.getUnicaInstancia().getContactoIndividual(movilContacto, movilUA);
		nMensajes = ManejadorChatCI.getUnicaInstancia().update(nMensajes, aux.getMensajes(), panel);
	}
	
	public void removeUpdate() {
		ActualizarBBDD.getUnicaInstancia().deletePanelChatCI(this);
	}
	
	public void sendEmoji(int emoji) {
		ManejadorChatCI.getUnicaInstancia().addBubbleText(emoji, movilContacto, movilUA, panel);
		ventanaEmoticonos.setVisible(false);
		on = 0;
		nMensajes += 1;
	}
	
	public int getmovilCI() {
		return movilContacto;
	}
}
