package controlador;

import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Estado;
import modelo.Grupo;
import modelo.IBuscar;
import modelo.Mensaje;
import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import modelo.Usuario;
import parser.SimpleTextParser;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorEstadoDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorAppChat {
	private static ControladorAppChat unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorCI;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorEstadoDAO adaptadorEstado;
	
	private Usuario usuario;
	private Contacto cActual;
	
	public static ControladorAppChat getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}
	
	private ControladorAppChat() {
		inicializarAdaptadores();
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorCI = factoria.getCIDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorEstado = factoria.getEstadoDAO();
	}
	
	
	/*-----------------------------------------------------------------------------------LOGIN Y REGISTRO-----------------------------------------------------------------------------------*/
	
	//Boolean existeUsuario
	public boolean existeUsuario(String login) {
		return CatalogoUsuarios.getUnicaInstancia().existeUsuario(login);
	}
	
	//Creamos el Usuario
		public Usuario crearUsuario(String nombre, Date fechaNacimiento
				, int movil,String login,  String pass, String email) {
			
			Usuario sol = CatalogoUsuarios.getUnicaInstancia()
			.crearUsuario(nombre, fechaNacimiento, movil, login, pass, email);
			adaptadorUsuario.registrarUsuario(sol);
			
			usuario = sol;
			
			return sol;
		}
	//Login Usuario
		public boolean loginUsuario(String login, String pass) {
			Usuario aux = CatalogoUsuarios.getUnicaInstancia()
						.loginUsuario(login, pass);
			if(aux == null)
				return false;
			usuario = aux;
			return true;
		}
	/*----------------------------------------------FIN LOGIN Y REGISTRO--------------------------------------------------------------------------------------*/
		

	/*----------------------------------------------PanelVistaPrincipal------------------------------------------------------------------------------*/
	public void addUsuarioChangeListener(PropertyChangeListener pcl) {
		usuario.addUsuarioChangeListener(pcl);
	}
	
	public String getSaludo() {
		return usuario.getSaludo();	
	}
	
	public String getNombre() {
		return usuario.getNombre();
	}
	
	public String getImagen() {
		return usuario.getImagen();
	}
	
	public boolean esPremium() {
		return usuario.isPremium();
	}
	
	public void generarPDF() throws DocumentException, MalformedURLException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("ContactosUsuario_" + usuario.getMovil() + ".pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Paragraph Paragraph = new Paragraph("Contactos de : " + usuario.getNombre() + 
				"\n######################################################\n", font);
		if(usuario.getnumeroContactosIndividuales()!= 0) {
		Paragraph ci = new Paragraph("Contactos Individuales\n");
		document.add(Paragraph);
		document.add(ci);
		
		Paragraph = new Paragraph("-----------------------------------------------------------------------");
		document.add(Paragraph);
		for(ContactoIndividual c : usuario.getContactosIndividuales()) {
			Paragraph = new Paragraph("Nombre: " + c.getNombre() + "\n");
			ci = new Paragraph("Nº Telefono: " + c.getMovil()+ "\n");
			document.add(Paragraph);
			document.add(ci);
			Paragraph = new Paragraph("-----------------------------------------------------------------------\n");
			document.add(Paragraph);
		}
		
		Paragraph = new Paragraph("######################################################\n", font);}
		if(usuario.getnumeroGrupos()!= 0) {
		Paragraph ci = new Paragraph("Grupos\n");
		Paragraph = new Paragraph("-----------------------------------------------------------------------");
		document.add(Paragraph);
		
		document.add(Paragraph);
		document.add(ci);
		for(Grupo g : usuario.getGrupos()) {
			Paragraph = new Paragraph("Nombre : " + g.getNombre()+ "\n");
			document.add(Paragraph);
			if(g.getContactos().size() != 0) {
				Paragraph = new Paragraph("**Contactos del Grupo: \n");
				document.add(Paragraph);
				for(ContactoIndividual c : g.getContactos()) {
					Paragraph = new Paragraph("**Nombre: " + c.getNombre()+ "\n");
					document.add(Paragraph);
					Paragraph = new Paragraph("**Nº Telfeono: " + c.getMovil()+ "\n");
				}
			}
			Paragraph = new Paragraph("-----------------------------------------------------------------------");
			document.add(Paragraph);
		}
		}
		Paragraph = new Paragraph("######################################################\n", font);
		document.add(Paragraph);
		document.close();
		
	}
	
	
	/*CONTACTO ACTUAL*/
	public String getImagenContacto(){
		return ((ContactoIndividual) cActual).getImagenUsuario();
	}
	
	public String getNombreContacto() {
		return cActual.getNombre();
	}
	
	public int getNumeroContacto() {
		return ((ContactoIndividual) cActual).getMovil();
	}
	
	public String getNombreAdminContacto() {
		return ((Grupo) cActual).getNombreAdmin();
	}
	
	public boolean esAdmin() {
	//Saber si el usuario es el administrados de un Grupo
			return ((Grupo) cActual ).esAdmin(usuario);
	}

	public void eliminarMensajes() {
		//Eliminamos los mensajes de un Contacto
		cActual.eliminarMensajes();
		if(cActual instanceof ContactoIndividual)
			adaptadorCI.modificarContactoIndividual((ContactoIndividual) cActual);
		else
			adaptadorGrupo.modificarGrupo((Grupo) cActual);
	}
	
	public void eliminarContacto() {
	//Metodo que llama desde PanelVistaPrincipal
		if(cActual instanceof Grupo) {eliminarGrupo((Grupo) cActual);
		} else {eliminarContactoIndividual((ContactoIndividual) cActual);}
		cActual = null;
	}
		
	public boolean eliminarContactoIndividual(ContactoIndividual ci) {
	//Eliminamos el ContactoIndividual
		adaptadorCI.borrarContactoIndividual(ci);
		usuario.removeContacto(ci);
		adaptadorUsuario.modificarUsuario(usuario);
		return true;
		}
	
	public boolean eliminarGrupo(Grupo g) {
	//Eliminamos el Grupo y en todos sus Usuarios registrados
		g.getContactos().stream()
		.map(c -> c.getUsuario())
		.forEach(u -> {
			u.removeContacto(g);
			adaptadorUsuario.modificarUsuario(u);
		});
		
		usuario.removeContacto(g);
		adaptadorUsuario.modificarUsuario(usuario);
		
		adaptadorGrupo.borrarGrupo(g);
		
		return true;
	}

	public int getSaldo() {
		return usuario.getSaldo();
	}
	
	public boolean pagar() {
		boolean pagado = usuario.pagar(40.00); 
		if(pagado)
			adaptadorUsuario.modificarUsuario(usuario);
		return pagado;
	}
	
	public double getPrecio() {
		return 40 - usuario.calcularDescuento();
	}

	public int addSaldo(int saldo) {
		return usuario.anyadirSaldo(saldo);
	}
	
	public void parse(String fichero, String formatDate, Plataforma p) {
		//Método que llama desde PanelVistaPrincipal, para tratar respuesta
		//De componente parse, que de un fichero dado y una plataforma, parsea 
		//mensajes para poder introducirlos e un chat
		
		List<MensajeWhatsApp> mensajesWpp = new LinkedList<MensajeWhatsApp>();
		try {
			mensajesWpp = SimpleTextParser.parse(fichero, formatDate, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Vemos si es ContactoIndividual o Grupo el contacto
			mensajesWpp.stream().forEach(m -> {
				Usuario usuario = null;
				String texto = "";
				int emoticono = 0;
				LocalDateTime fecha = null;
				
				//SII está escrito por el usuario
				if(m.getAutor().equals(this.usuario.getNombre())) {
					usuario = this.usuario;
				} else {
					//SII NO buscamos al usuario
					usuario = this.usuario.getUsuario(m.getAutor());
				}
				
				//Comprobamos si es emoticono o texto
				try {
					emoticono = Integer.valueOf(m.getTexto());
				} catch(NumberFormatException e) {
					texto = m.getTexto();
				}
				
				//obtenemos fecha
				fecha = m.getFecha();
				
				//Creamos el mensaje
				Mensaje mensaje = cActual.sendMensaje(texto, fecha, emoticono, usuario);
				
				//Registramos mensaje
				adaptadorMensaje.registrarMensaje(mensaje);
				
				//SII el contacto es un ContactoIndividual
				if(cActual instanceof ContactoIndividual)
					adaptadorCI.modificarContactoIndividual((ContactoIndividual) cActual);
				 else
					adaptadorGrupo.modificarGrupo((Grupo) cActual);
			});
	}

	public boolean isGrupo() {
		return cActual instanceof Grupo;
	}
	/*---------------------------------------------------FIN PanelVistaPrincipal---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------CREARMODIFICARCONTACTO---------------------------------------------------------------------------------------------------------------------*/
	
	public ContactoIndividual crearCI(String nombre, int numTelefono) {
		
		//Creamos un ContactoIndividual modificando tanto el usuarioActual 
		//y creando el ContactoIndividual
		
		Usuario aux = CatalogoUsuarios.getUnicaInstancia()
					.getUsuario(numTelefono);
		ContactoIndividual sol = usuario.crearCI(nombre, numTelefono, aux);
		
		adaptadorCI.registrarContactoIndividual(sol);
		adaptadorUsuario.modificarUsuario(usuario);
		
		cActual = sol;
		return sol;
	}
	
	public Grupo crearGrupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		//Creamos un Grupo y modificamos a todos los UsuariosRegistrados
		Grupo sol = usuario.crearGrupo(nombre, contactos);
		adaptadorGrupo.registrarGrupo(sol);
		adaptadorUsuario.modificarUsuario(usuario);
		
		contactos.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.addContacto(sol);
				adaptadorUsuario.modificarUsuario(u);
			});
		
		cActual = sol;
		return sol;
	}

	public ContactoIndividual modificarCI(String nombre,int movil) {
		//Modificar un ContactoIndividual
		cActual.setNombre(nombre);
		((ContactoIndividual)cActual).setMovil(movil);
		adaptadorCI.modificarContactoIndividual((ContactoIndividual)cActual);
		return ((ContactoIndividual)cActual);
	}
		
	public Grupo modificarGrupo(String nombre, ArrayList<ContactoIndividual> contactos) { 
		//Recorre todos los Usuarios del grupo y elimina el contacto de Grupo
		//Recorre todos los Usuarios nuevos del grupo y añade el contacto de Grupo
		//Modifica el Grupo
		List<ContactoIndividual> contactosGrupo = ((Grupo)cActual).getContactos();
		contactosGrupo.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.removeContacto((Grupo)cActual);
				adaptadorUsuario.modificarUsuario(u);
			});
		contactos.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.addContacto((Grupo)cActual);
				adaptadorUsuario.modificarUsuario(u);
			});
		
		((Grupo)cActual).setContactos(contactos);
		((Grupo)cActual).setNombre(nombre);
		adaptadorGrupo.modificarGrupo((Grupo)cActual);
		
		return ((Grupo)cActual);
	}
		
	public List<ContactoIndividual> getContactosGrupoActual() {
		return ((Grupo) cActual).getContactos();
	}
	
	public boolean existeContacto(int movil) {
		//Devuelve true si existe un Contacto con ese movil
		return usuario.existeContacto(movil);
	}
		
	public boolean existeContacto(String nombre) {
		//Devuelve true si existe un Contacto con ese nombre
		return usuario.existeContacto(nombre);
	}
	
	public boolean existeUsuario(int movil) {
		return CatalogoUsuarios.getUnicaInstancia().existeUsuario(movil);
	}
	
	public List<Contacto> getContactos() {
		return usuario.getContactos();
	}
	
	public ContactoIndividual getContacto(String nombre) {
		//Devuelve el ContactoIndividual con ese nombre
		return usuario.getContacto(nombre);
	}
	/*---------------------------------------------------FIN CREARMODIFICARCONTACTO---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------CONTROLADORACTUALIZARUSUARIO---------------------------------------------------------------------------------------------------------------------*/
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/*---------------------------------------------------FIN CONTROLADORACTUALIZARUSUARIO---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL CAMBIAR SALUDO---------------------------------------------------------------------------------------------------------------------*/
	public void setSaludo(String saludo) {
		//Cambiamos el Saludo del Usuario
		usuario.setSaludo(saludo);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	/*---------------------------------------------------FIN PANEL CAMBIAR SALUDO---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL CAMBIAR IMAGEN---------------------------------------------------------------------------------------------------------------------*/
	public void setImagen(String imagen) {
		//Cambiamos la imagen del Usuario
		usuario.setImagen(imagen);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	/*---------------------------------------------------FIN PANEL CAMBIAR IMAGEN---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL BUSCAR---------------------------------------------------------------------------------------------------------------------*/
	public boolean esContacto() {
		return ((Grupo) cActual).isContacto(usuario);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Mensaje> buscar(IBuscar buscador) {
		return cActual.buscar(buscador);
	}
	/*---------------------------------------------------FIN PANEL BUSCAR---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL CHAT---------------------------------------------------------------------------------------------------------------------*/
	public Contacto getContactoActual() {
		return cActual;
	}
	
	public boolean esEmisor(Usuario u) {
		return usuario.equals(u);
	}
	
	public Mensaje crearMensajeCI(String texto, int emoticono, ContactoIndividual ci) {
		//Creamos el Mensaje del ContactoIndividual
		//SII el usuario del ContactoIndividual no tiene guardado al
		//Usuario, creamos el Contacto Individual
		//Después anyadimos otro Mensaje
		LocalDateTime hora = LocalDateTime.now();
		Mensaje sol = ci.sendMensaje(texto, hora, emoticono, usuario);
		
		adaptadorMensaje.registrarMensaje(sol);
		adaptadorCI.modificarContactoIndividual(ci);
		
		Usuario usuarioCI = ci.getUsuario();
		ContactoIndividual aux = null;
		if(usuarioCI.tieneContacto(usuario)) {
			aux = usuarioCI.getContacto(usuario);
		} else {
			int movil = usuario.getMovil();
			String nombre = movil + "";
			aux = usuarioCI.crearCI(nombre, movil, usuario);
			adaptadorCI.registrarContactoIndividual(aux);
			adaptadorUsuario.modificarUsuario(usuarioCI);
		}
		
		Mensaje sol2 = aux.sendMensaje(texto, hora, emoticono, usuario);
		adaptadorMensaje.registrarMensaje(sol2);
		adaptadorCI.modificarContactoIndividual(aux);
		
		return sol;
	}	

	public Mensaje crearMensajeG(String texto, int emoticono, Grupo g) {
		//Creamos el Mensaje y lo registramos
		//Modificamos también el Grupo
		LocalDateTime hora = LocalDateTime.now();
		Mensaje sol = g.sendMensaje(texto, hora, emoticono, usuario);
		
		adaptadorMensaje.registrarMensaje(sol);
		adaptadorGrupo.modificarGrupo(g);
		
		return sol;
	}	
	
	public void removeUsuarioChangeListener(PropertyChangeListener pcl) {
		usuario.removeUsuarioChangeListener(pcl);
	}
	
	public String getNombreContacto(Usuario contacto) {
		//Devuelve el nombre del Contacto
		usuario.getContacto(contacto);
		return contacto.getNombre();
	}
	/*---------------------------------------------------FIN PANEL CHAT---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL ESTADO---------------------------------------------------------------------------------------------------------------------*/
	public List<ContactoIndividual> getCIconEstado(){
		return usuario.getCIconEstado();
	}
	/*---------------------------------------------------FIN PANEL ESTADO---------------------------------------------------------------------------------------------------------------------*/
	
	/*---------------------------------------------------PANEL INFORMACION---------------------------------------------------------------------------------------------------------------------*/
	public ArrayList<Integer> getNumeroMensajesDelAnyo() {
		return usuario.getNumeroMensajesDelAnyo();
	}
	
	public HashMap<Grupo, Double> getGruposTopMensajes() {
		return usuario.getGruposTopMensajes();
	}
	/*---------------------------------------------------FIN PANEL INFORMACION---------------------------------------------------------------------------------------------------------------------*/
	
	
	/*---------------------------------------------------PANEL VISOR ESTADO---------------------------------------------------------------------------------------------------------------------*/
	public void crearEstado(String urlImagen, String comentario) {
		Estado sol = usuario.crearEstado(urlImagen, comentario);
		adaptadorEstado.registrarEstado(sol);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	/*---------------------------------------------------FIN PANEL VISOR ESTADO---------------------------------------------------------------------------------------------------------------------*/

	/*---------------------------------------------------PANEL CONTACTO---------------------------------------------------------------------------------------------------------------------*/
	
	public void setContacto(Contacto c) {
		cActual = c;
	}
	/*---------------------------------------------------FIN PANEL CONTACTO---------------------------------------------------------------------------------------------------------------------*/
	
	
}
