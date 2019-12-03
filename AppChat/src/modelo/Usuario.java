package modelo;

import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import pruebas.prueba;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private LocalDate fechaNacimiento;
	private int movil;
	private String email = "";
	private String usuario;
	private String contraseña;
	private ImageIcon imagen;
	private boolean premium;
	private String saludo = "Hola AppChat";

	private Estado estado;
	private List<Contacto> contactos;
	private HashMap<String, Grupo> gruposPorNombre;
	private HashMap<Integer, ContactoIndividual> contactosIndividualesPorNombre;
	private List<Grupo> gruposAdmin;
	private String urlImagen = "/imagenes/ImagenUsuarioDef.png";
	private List<Contacto> contactosOrdenadorPorTiempo;

	public Usuario(String nombre, LocalDate fechaNacimiento, int movil, String usuario, String contraseña,
			String urlImagen, boolean premium, String email) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.urlImagen = urlImagen;
		imagen = new ImageIcon(Usuario.class.getResource(urlImagen));
		this.premium = premium;
		this.email = email;
		contactos = new ArrayList<Contacto>();
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<Integer, ContactoIndividual>();
		contactosOrdenadorPorTiempo = new ArrayList<Contacto>();
		gruposAdmin = new ArrayList<Grupo>();
		estado = new Estado();
	}

	public Usuario(String nombre, LocalDate fechaNacimiento, int movil, String usuario, String contraseña,
			String email) {
		super();
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.email = email;
		premium = false;
		imagen = new ImageIcon(Usuario.class.getResource(urlImagen));

		contactos = new ArrayList<Contacto>();
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<Integer, ContactoIndividual>();
		gruposAdmin = new ArrayList<Grupo>();
		contactosOrdenadorPorTiempo = new ArrayList<Contacto>();
		estado = new Estado();
	}

	public Usuario(int idUsuario, String nombre, LocalDate fechaNacimiento, int movil, String usuario,
			String contraseña, String urlImagen, boolean premium, Estado estado) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.urlImagen = urlImagen;
		this.imagen = new ImageIcon(Usuario.class.getResource(urlImagen));
		;
		this.premium = premium;
		this.estado = estado;

		contactos = new ArrayList<Contacto>();
		gruposAdmin = new ArrayList<Grupo>();
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<Integer, ContactoIndividual>();
		contactosOrdenadorPorTiempo = new ArrayList<Contacto>();
	}

	public Usuario(int idUsuario, String nombre, LocalDate fechaNacimiento, int movil, String email, String usuario,
			String contraseña, String urlImagen, boolean premium, Estado estado, List<Contacto> contactos,
			List<Grupo> gruposAdmin) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.urlImagen = urlImagen;
		this.imagen = new ImageIcon(Usuario.class.getResource(urlImagen));
		this.premium = premium;
		this.estado = estado;
		this.contactos = contactos;
		this.gruposAdmin = gruposAdmin;

		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<Integer, ContactoIndividual>();
		contactosOrdenadorPorTiempo = new ArrayList<Contacto>();
		update();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	public Grupo getGrupoPorNombre(String nombreGrupo) {
		return gruposPorNombre.get(nombreGrupo);
	}

	public ContactoIndividual getCIPorNombre(String nombreCI) {
		return contactosIndividualesPorNombre.get(nombreCI);
	}

	public ContactoIndividual getCIPorNumero(int movil) {
		for (ContactoIndividual ci : contactosIndividualesPorNombre.values()) {
			if (ci.getMovil() == movil)
				return ci;
		}
		return null;
	}

	public void addContacto(Contacto c) {
		contactos.add(c);
		if (c.getClass() == Grupo.class)
			gruposPorNombre.put(c.getNombre(), (Grupo) c);
		else {
			ContactoIndividual ci = (ContactoIndividual) c;
			
			contactosIndividualesPorNombre.put(ci.getMovil(), ci);
		
		}
	}

	public void addGrupoAdmin(Grupo g) {
		gruposAdmin.add(g);
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getMovil() {
		return movil;
	}

	public void setMovil(int movil) {
		this.movil = movil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Contacto> getContactos() {
		return contactos;

	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
		for (Contacto c : contactos) {
			if (c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				gruposPorNombre.put(aux.getNombre(), aux);
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				contactosIndividualesPorNombre.put(aux.getMovil(), aux);
			}

		}
	}

	public List<Grupo> getGruposAdmin() {
		return gruposAdmin;
	}

	public void setGruposAdmin(List<Grupo> gruposAdmin) {
		this.gruposAdmin = gruposAdmin;
	}

	public String getImagenUrl() {
		return urlImagen;
	}

	public void update() {
		for (Contacto c : contactos) {
			if (c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				gruposPorNombre.put(aux.getNombre(), aux);
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				contactosIndividualesPorNombre.put(aux.getMovil(), aux);
			}

		}
	}

	public List<Contacto> getContactosPorTiempo() {
		return contactosOrdenadorPorTiempo;
	}
}
