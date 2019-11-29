package Class;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
	private ArrayList<Contacto> contactos;
	private HashMap<String, Grupo> gruposPorNombre;
	private HashMap<String, ContactoIndividual> contactosIndividualesPorNombre;
	private ArrayList<Grupo> gruposAdmin;
		
	
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
			ArrayList<Contacto> contactos, ArrayList<Grupo> gruposAdmin) {
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
	public ArrayList<Contacto> getContactos() {
		return contactos;
		
	}

	public void setContactos(ArrayList<Contacto> contactos) {
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
	public ArrayList<Grupo> getGruposAdmin() {
		return gruposAdmin;
	}
	public void setGruposAdmin(ArrayList<Grupo> gruposAdmin) {
		this.gruposAdmin = gruposAdmin;
	}

	
}