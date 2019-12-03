package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grupo extends Contacto {

	private ArrayList<ContactoIndividual> contactos;
	private Map<Integer, List<ContactoIndividual>> contactosPorUsuario;
	private Usuario admin;

	public Grupo(String nombre) {
		super(nombre);
		contactos = new ArrayList<ContactoIndividual>();
		contactosPorUsuario = new HashMap<Integer, List<ContactoIndividual>>();
	}

	public Grupo(String nombre, Usuario admin) {
		super(nombre);
		this.admin = admin;
		contactos = new ArrayList<ContactoIndividual>();
		contactosPorUsuario = new HashMap<Integer, List<ContactoIndividual>>();
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

	public ArrayList<ContactoIndividual> getContactosPorUsuario(int idUsuario) {
		return (ArrayList<ContactoIndividual>) contactosPorUsuario.get(idUsuario);
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