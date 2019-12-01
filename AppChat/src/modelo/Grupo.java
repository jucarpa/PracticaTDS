package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grupo extends Contacto{
	
	private ArrayList<ContactoIndividual> contactos ;
	private Map<Integer,List<ContactoIndividual>> contactosPorUsuario ;
	private Usuario admin;
	
	
	public Grupo(String nombre) {
		super(nombre);
		contactos = new ArrayList<ContactoIndividual>();
		contactosPorUsuario = new HashMap<Integer,List<ContactoIndividual>>();
	}
	public Grupo(String nombre, Usuario admin) {
		super(nombre);
		this.admin = admin;
		contactos = new ArrayList<ContactoIndividual>();
		contactosPorUsuario = new HashMap<Integer,List<ContactoIndividual>>();
	}
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos, Usuario admin, int id) {
		super(nombre);
		this.contactos = contactos;
		this.admin = admin;
		this.id = id;
		actualizarContactos();
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
		for(ContactoIndividual c : contactos) {
			Usuario u = c.getUsuario();
			ContactoIndividual aux = u.getCIPorNumero(ci.getMovil());
			if(aux == null) aux = new ContactoIndividual(String.valueOf(ci.getMovil()), ci.getMovil());
			contactosPorUsuario.get(u.getIdUsuario()).add(aux);
		}
		contactos.add(ci);
		
		List<ContactoIndividual> contactosCI = new ArrayList<ContactoIndividual>();
		Usuario u = ci.getUsuario();
		for(ContactoIndividual c : contactos) {
			ContactoIndividual aux = u.getCIPorNumero(c.getMovil());
			if(aux == null) aux = new ContactoIndividual(String.valueOf(c.getMovil()), c.getMovil());
			contactosCI.add(aux);
		}
		contactosPorUsuario.put(u.getIdUsuario(), contactosCI);
		
		
	}
	
	public void actualizarContactos() {
		for (ContactoIndividual c : contactos) {
			List<ContactoIndividual> sol = new ArrayList<ContactoIndividual>();
			Usuario u = c.getUsuario();
			for(ContactoIndividual cI : contactos) {
			ContactoIndividual aux = u.getCIPorNumero(cI.getMovil());
			if(aux == null && u.getMovil() != cI.getMovil())
				aux = new ContactoIndividual(String.valueOf(cI.getMovil()), cI.getMovil());
			sol.add(aux);
			}
			contactosPorUsuario.put(u.getIdUsuario(), sol);
		}
	}
	
}