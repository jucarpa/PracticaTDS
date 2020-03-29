package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@SuppressWarnings("serial")
public class Grupo extends Contacto {

	private List<ContactoIndividual> contactos;
	private Usuario admin;
	
	//Constructores
	public Grupo(String nombre) {
		super(nombre);
		contactos = new ArrayList<ContactoIndividual>();
	}
	
	public Grupo(String nombre, Usuario admin) {
		this(nombre);
		this.admin = admin;
	}
	
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos, Usuario admin) {
		this(nombre, admin);
		this.contactos = contactos;
	}
	
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos, Usuario admin, int id) {
		this(nombre, contactos, admin);
		this.id = id;
	}
	
	//Getters/Setters
	public List<ContactoIndividual> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoIndividual> contactos) {
		this.contactos = contactos;
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public void addContacto(ContactoIndividual ci) {
		contactos.add(ci);
		oyentesContacto.firePropertyChange("contactoG", null, ci);
	}
	
	public void deleteContacto(ContactoIndividual ci) {
		contactos.remove(ci);
		oyentesContacto.firePropertyChange("contactoG", ci, null);
	}
	public boolean esAdmin(Usuario u) {
		return admin.equals(u);
	}
	
	
	//Devuelve los Usuarios de los Contactos
	public List<Usuario> getUsuarios() {
		return contactos.stream()
				.map(u -> u.getUsuario())
				.collect(Collectors.toList());
	}
	
	public boolean isContacto(Usuario u) {
		for(ContactoIndividual c : contactos) {
			if(c.getUsuario().equals(u))
				return true;
		}
		return false;
	}

}