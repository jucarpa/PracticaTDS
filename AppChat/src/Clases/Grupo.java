package Clases;
import java.util.ArrayList;
import java.util.Collections;

public class Grupo extends Contacto{
	
	private ArrayList<ContactoIndividual> contactos = new ArrayList<ContactoIndividual>();
	private Usuario admin;
	private int idGrupo;
	
	public int getId() {
		return idGrupo;
	}
	
	public void setId(int id) {
		idGrupo = id;
	}
	
	public Usuario getAdmin() {
		return admin;
	}
	
	public ArrayList<ContactoIndividual> getContactos() {
		return contactos;
	}
	
	public void addContacto(ContactoIndividual contacto) {
		contactos.add(contacto);
	}
	
	public void addContactos(ContactoIndividual ... contactos) {
		Collections.addAll(this.contactos, contactos);
	}
	
	public boolean deleteContacto(ContactoIndividual contacto) {
		return contactos.remove(contacto);
	}
	
	public Grupo(String nombre, Usuario admin) {
		super(nombre);
		// TODO Auto-generated constructor stub
		this.admin = admin;
	}
	
	public Grupo(String nombre, Usuario admin, int id) {
		super(nombre);
		this.admin = admin;
		this.idGrupo = id;
	}
	
	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String aux = super.toString();
			aux += contactos.toString();
		
		return aux;
	}
	
	
	
}
