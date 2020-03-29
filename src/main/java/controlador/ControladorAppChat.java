package controlador;

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

import componentes.bin.parse.modelo.MensajeWhatsApp;
import componentes.bin.parse.modelo.Plataforma;
import componentes.bin.parse.parser.SimpleTextParser;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Estado;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
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
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
	//Creamos un ContactoIndividual modificando tanto el usuarioActual 
	//y creando el ContactoIndividual
	public ContactoIndividual crearCI(String nombre, int numTelefono) {
		Usuario aux = CatalogoUsuarios.getUnicaInstancia()
					.getUsuario(numTelefono);
		ContactoIndividual sol = usuario.crearCI(nombre, numTelefono, aux);
		
		adaptadorCI.registrarContactoIndividual(sol);
		adaptadorUsuario.modificarUsuario(usuario);
		
		return sol;
	}
	
	//Eliminamos el ContactoIndividual
	public boolean eliminarContactoIndividual(ContactoIndividual ci) {
		adaptadorCI.borrarContactoIndividual(ci);
		usuario.removeContacto(ci);
		adaptadorUsuario.modificarUsuario(usuario);
		return true;
		
	}
	
	//Creamos un Grupo y modificamos a todos los UsuariosRegistrados
	public Grupo crearGrupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		Grupo sol = usuario.crearGrupo(nombre, contactos);
		adaptadorGrupo.registrarGrupo(sol);
		adaptadorUsuario.modificarUsuario(usuario);
		
		contactos.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.addContacto(sol);
				adaptadorUsuario.modificarUsuario(u);
			});
		
		return sol;
	}
	
	//Eliminamos el Grupo y en todos sus Usuarios registrados
	public boolean eliminarGrupo(Grupo g) {
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
	
	//Metodo que llama desde PanelVistaPrincipal
	public void eliminarContacto(Contacto contacto) {
		if(contacto instanceof Grupo) {eliminarGrupo((Grupo) contacto);
		} else {eliminarContactoIndividual((ContactoIndividual) contacto);}
	}
	//Creamos el Mensaje del ContactoIndividual
	//SII el usuario del ContactoIndividual no tiene guardado al
	//Usuario, creamos el Contacto Individual
	//Después anyadimos otro Mensaje
	public Mensaje crearMensajeCI(String texto, int emoticono, ContactoIndividual ci) {
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
	
	
	//Creamos el Mensaje y lo registramos
	//Modificamos también el Grupo
	public Mensaje crearMensajeG(String texto, int emoticono, Grupo g) {
		LocalDateTime hora = LocalDateTime.now();
		Mensaje sol = g.sendMensaje(texto, hora, emoticono, usuario);
		
		adaptadorMensaje.registrarMensaje(sol);
		adaptadorGrupo.modificarGrupo(g);
		
		return sol;
	}
	
	public boolean existeUsuario(String login) {
		return CatalogoUsuarios.getUnicaInstancia().existeUsuario(login);
	}
	
	public boolean login(String login, String pass) {
		Usuario aux = CatalogoUsuarios.getUnicaInstancia()
					.login(login, pass);
		if(aux == null)
			return false;
		usuario = aux;
		return true;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public String getSaludo() {
		return usuario.getSaludo();
	}
	
	public List<Contacto> getContactos() {
		return usuario.getContactos();
	}
	public String getNombre() {
		return usuario.getNombre();
	}
	
	public String getImagen() {
		return usuario.getImagen();
	}
	//Cambiamos el Saludo del Usuario
	public void setSaludo(String saludo) {
		usuario.setSaludo(saludo);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	
	//Cambiamos la imagen del Usuario
	public void setImagen(String imagen) {
		usuario.setImagen(imagen);
		adaptadorUsuario.modificarUsuario(usuario);
		
	}
	
	//Devuelve true si existe un Contacto con ese movil
	public boolean existeContacto(int movil) {
		return usuario.existeContacto(movil);
		
	}
	//Devuelve true si existe un Contacto con ese nombre
	public boolean existeContacto(String nombre) {
		return usuario.existeContacto(nombre);
	}
	
	//Devuelve true si Existe un Usuario con ese movil
	public boolean existeUsuario(int movil) {
		return CatalogoUsuarios.getUnicaInstancia().existeUsuario(movil);
	}
	//Devuelve el nombre del Contacto
	public String getNombreContacto(Usuario contacto) {
		usuario.getContacto(contacto);
		return contacto.getNombre();
	}
	//Devuelve el ContactoIndividual con ese nombre
	public ContactoIndividual getContacto(String nombre) {
		return usuario.getContacto(nombre);
	}
	
	//Modificar un ContactoIndividual
	public ContactoIndividual modificarCI(String nombre,int movil,ContactoIndividual contacto) {
		contacto.setNombre(nombre);
		contacto.setMovil(movil);
		adaptadorCI.modificarContactoIndividual(contacto);
		return contacto;
	}
	
	
	//Recorre todos los Usuarios del grupo y elimina el contacto de Grupo
	//Recorre todos los Usuarios nuevos del grupo y añade el contacto de Grupo
	//Modifica el Grupo
	public Grupo modificarGrupo(String nombre, ArrayList<ContactoIndividual> contactos, Grupo contacto) { 
		List<ContactoIndividual> contactosGrupo = contacto.getContactos();
		contactosGrupo.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.removeContacto(contacto);
				adaptadorUsuario.modificarUsuario(u);
			});
		contactos.stream()
			.map(c -> c.getUsuario())
			.forEach(u -> {
				u.addContacto(contacto);
				adaptadorUsuario.modificarUsuario(u);
			});
		
		contacto.setContactos(contactos);
		contacto.setNombre(nombre);
		adaptadorGrupo.modificarGrupo(contacto);
		
		return contacto;
	}
	
	//Saber si el usuario es el administrados de un Grupo
	public boolean esAdmin(Grupo g) {
		return g.esAdmin(usuario);
	}
	
	public int getSaldo() {
		return usuario.getSaldo();
	}
	
	public int addSaldo(int saldo) {
		return usuario.anyadirSaldo(saldo);
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
	
	public boolean esPremium() {
		return usuario.isPremium();
	}
	public Usuario getUsuario(String nombre) {
		return usuario.getUsuario(nombre);
	}
	
	public void generarPDF() throws DocumentException, MalformedURLException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("ContactosUsuario_" + usuario.getMovil() + ".pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Paragraph Paragraph = new Paragraph("Contactos de : " + usuario.getNombre() + 
				"\n######################################################\n", font);
		if(usuario.getContactosIndividuales().size() != 0) {
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
		if(usuario.getGrupos().size() != 0) {
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
	
	//Eliminamos los mensajes de un Contacto
	public void eliminarMensajes(Contacto contacto) {
		contacto.eliminarMensajes();
		if(contacto instanceof ContactoIndividual)
			adaptadorCI.modificarContactoIndividual((ContactoIndividual) contacto);
		else
			adaptadorGrupo.modificarGrupo((Grupo) contacto);
	}
	
	//Método que llama desde PanelVistaPrincipal, para tratar respuesta
	//De componente parse, que de un fichero dado y una plataforma, parsea 
	//mensajes para poder introducirlos e un chat
	public void parse(String fichero, String formatDate, Plataforma p, Contacto c) {
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
					usuario = getUsuario(m.getAutor());
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
				Mensaje mensaje = c.sendMensaje(texto, fecha, emoticono, usuario);
				
				//Registramos mensaje
				adaptadorMensaje.registrarMensaje(mensaje);
				
				//SII el contacto es un ContactoIndividual
				if(c instanceof ContactoIndividual)
					adaptadorCI.modificarContactoIndividual((ContactoIndividual) c);
				 else
					adaptadorGrupo.modificarGrupo((Grupo) c);
			});
	}
	
	public ArrayList<Integer> getNumeroMensajesDelAnyo() {
		return usuario.getNumeroMensajesDelAnyo();
	}
	
	public HashMap<Grupo, Double> getGruposTopMensajes() {
		return usuario.getGruposTopMensajes();
	}
	
	/*
	 * APARTADO OPCIONAL
	 */
	
	public List<Estado> getEstados() {
		return usuario.getEstadosContactos();
	}
	
	public List<ContactoIndividual> getContactosIndividuales() {
		return usuario.getContactosIndividuales();
	}
	
	public List<ContactoIndividual> getCIconEstado(){
		return usuario.getCIconEstado();
	}
	
	public void crearEstado(String urlImagen, String comentario) {
		Estado sol = usuario.crearEstado(urlImagen, comentario);
		adaptadorEstado.registrarEstado(sol);
		adaptadorUsuario.modificarUsuario(usuario);
	}

}
