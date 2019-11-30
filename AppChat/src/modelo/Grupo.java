package modelo;

import java.util.ArrayList;

public class Grupo extends Contacto{
	
	private ArrayList<ContactoIndividual> contactos = new ArrayList<ContactoIndividual>();
	private Usuario admin;
	
	
	public Grupo(String nombre) {
		super(nombre);
	}
	public Grupo(String nombre, Usuario admin) {
		super(nombre);
		this.admin = admin;
		contactos = new ArrayList<ContactoIndividual>();
	}
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos, Usuario admin, int id) {
		super(nombre);
		this.contactos = contactos;
		this.admin = admin;
		this.id = id;
	}
	
	
	public ArrayList<ContactoIndividual> getContactos() {
		return contactos;
	}
	public void setContactos(ArrayList<ContactoIndividual> contactos) {
		this.contactos = contactos;
	}
	public Usuario getAdmin() {
		return admin;
	}
	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void addMensaje(Mensaje m) {
		mensajes.add(m);
	}
	
	public void addContacto(ContactoIndividual ci) {
		contactos.add(ci);
	}

	
}