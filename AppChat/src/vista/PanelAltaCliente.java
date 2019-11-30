package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorAppChat;

@SuppressWarnings("serial")
public class PanelAltaCliente extends JPanel {
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo;
	private JPanel datosCliente;
	private JLabel lNombre, lfechaNacimiento, lmovil, lUsuario, lcontrasena, lalerta;
	private JTextField nombre, fechaNacimiento, movil, usuario, contrasena;
	private JButton btnRegistrar, btnCancelar;
	private VentanaMain ventana;
	
	
	public PanelAltaCliente(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}

	private void crearPantalla() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		rotulo=new JLabel("Alta Usuario",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size,60);
		rotulo.setFont(fuenteGrande);
		add(rotulo,BorderLayout.NORTH);

		datosCliente=new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		lNombre=new JLabel("Nombre:",JLabel.RIGHT); 
		fixedSize(lNombre,170,24);
		nombre=new JTextField(); 
		fixedSize(nombre,300,24); 

		lfechaNacimiento=new JLabel("FechaNacimento:",JLabel.RIGHT); 
		fixedSize(lfechaNacimiento,170,24);
		fechaNacimiento=new JTextField(); 
		fixedSize(fechaNacimiento,300,24);
		
		lmovil=new JLabel("Movil:",JLabel.RIGHT); 
		fixedSize(lmovil,170,24);
		movil=new JTextField(); 
		fixedSize(movil,300,24);
		
		lUsuario=new JLabel("Usuario:",JLabel.RIGHT); 
		fixedSize(lUsuario,170,24);
		usuario=new JTextField(); 
		fixedSize(usuario,300,24); 
		
		lcontrasena=new JLabel("Contraseña:",JLabel.RIGHT);	
		fixedSize(lcontrasena,170,24);
		contrasena=new JTextField(); 
		fixedSize(contrasena,300,24); 
	
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		lalerta=new JLabel("Todos los campos son obligatorios",JLabel.CENTER);
		lalerta.setForeground(Color.RED); fixedSize(lalerta,Constantes.x_size,30);
		lalerta.setVisible(false);
		
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,75)));
		datosCliente.add(lNombre); datosCliente.add(nombre);
		datosCliente.add(lfechaNacimiento); datosCliente.add(fechaNacimiento);
		datosCliente.add(lmovil); datosCliente.add(movil);
		datosCliente.add(lUsuario); datosCliente.add(usuario);
		datosCliente.add(lcontrasena); datosCliente.add(contrasena);
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,75)));
		datosCliente.add(Box.createRigidArea(new Dimension(170,20)));
		datosCliente.add(btnRegistrar);
		datosCliente.add(Box.createRigidArea(new Dimension(90,20)));
		datosCliente.add(btnCancelar);
		datosCliente.add(lalerta);
		
		add(datosCliente,BorderLayout.CENTER);
		
		//Manejadores
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auxNombre = nombre.getText().trim();
				String auxFechaNacimineto = fechaNacimiento.getText().trim();
				String auxMovil = movil.getText().trim();
				String auxLogin=usuario.getText().trim();	
				String auxContraseña =contrasena.getText().trim();
				
				if (auxNombre.isEmpty()||auxFechaNacimineto.isEmpty() ||auxMovil.isEmpty() ||
						auxLogin.isEmpty() ||auxContraseña.isEmpty()) lalerta.setVisible(true);
				else { lalerta.setVisible(false);
					   ControladorAppChat.getUnicaInstancia().registrarUsuario(auxNombre, LocalDate.parse(auxFechaNacimineto), Integer.parseInt(auxMovil), auxLogin, auxContraseña);
					   JOptionPane.showMessageDialog(ventana,
								"Usuario dado de alta correctamente",
								"Registrar Usuario",JOptionPane.PLAIN_MESSAGE);
					   usuario.setText(""); contrasena.setText(""); lalerta.setVisible(false); 
				}
			}	
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventana.cambioPanelLogin(); 
			}
		});
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
