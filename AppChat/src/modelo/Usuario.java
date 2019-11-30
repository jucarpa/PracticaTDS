package modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private LocalDate fechaNacimiento;
	private int movil;
	private String usuario;
	private String contraseña;
	private Image imagen;
	private boolean premium;

	private Estado estado;
	private List<Contacto> contactos;
	private HashMap<String, Grupo> gruposPorNombre;
	private HashMap<String, ContactoIndividual> contactosIndividualesPorNombre;
	private List<Grupo> gruposAdmin;
		
	
	public Usuario(String nombre, LocalDate fechaNacimiento, int movil, String usuario,
			String contraseña, Image imagen, boolean premium) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.premium = premium;
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<String, ContactoIndividual>();
	}
	public Usuario(String nombre, LocalDate fechaNacimiento, int movil, String usuario, String contraseña) {
		super();
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		
		contactos = new ArrayList<Contacto>();
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<String, ContactoIndividual>();
		gruposAdmin = new ArrayList<Grupo>();
		}
	public Usuario(int idUsuario, String nombre, LocalDate fechaNacimiento, int movil, String usuario,
			String contraseña, Image imagen, boolean premium, Estado estado) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.premium = premium;
		this.estado = estado;
		
		contactos = new ArrayList<Contacto>();
		gruposAdmin = new ArrayList<Grupo>();
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<String, ContactoIndividual>();
	}
	public Usuario(int idUsuario, String nombre, LocalDate fechaNacimiento, int movil, String usuario,
			String contraseña, Image imagen, boolean premium, Estado estado,
			List<Contacto> contactos, List<Grupo> gruposAdmin) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.imagen = imagen;
		this.premium = premium;
		this.estado = estado;
		this.contactos = contactos;
		this.gruposAdmin = gruposAdmin;
		
		gruposPorNombre = new HashMap<String, Grupo>();
		contactosIndividualesPorNombre = new HashMap<String, ContactoIndividual>();
		for(Contacto c : contactos) {
			if(c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				gruposPorNombre.put(aux.getNombre(), aux);
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				contactosIndividualesPorNombre.put(aux.getNombre(), aux);
			}
			
		}
	}
	
	
	public Grupo getGrupoPorNombre(String nombreGrupo) {
		return gruposPorNombre.get(nombreGrupo);
	}
	public ContactoIndividual getCIPorNombre(String nombreCI) {
		return contactosIndividualesPorNombre.get(nombreCI);
	}
	
	public ContactoIndividual getCIPorNumero(int movil) {
		for(ContactoIndividual ci : contactosIndividualesPorNombre.values()) {
			if(ci.getMovil() == movil) return ci;
		}
		return null;
	}
	
	public void addContacto(Contacto c) {
		contactos.add(c);
		if(c.getClass() == Grupo.class) gruposPorNombre.put(c.getNombre(),(Grupo) c);
		else contactosIndividualesPorNombre.put(c.getNombre(),(ContactoIndividual)c);
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
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
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
		for(Contacto c : contactos) {
			if(c.getClass() == Grupo.class) {
				Grupo aux = (Grupo) c;
				gruposPorNombre.put(aux.getNombre(), aux);
			} else {
				ContactoIndividual aux = (ContactoIndividual) c;
				contactosIndividualesPorNombre.put(aux.getNombre(), aux);
			}
			
		}
	}
	public List<Grupo> getGruposAdmin() {
		return gruposAdmin;
	}
	public void setGruposAdmin(List<Grupo> gruposAdmin) {
		this.gruposAdmin = gruposAdmin;
	}

	
}