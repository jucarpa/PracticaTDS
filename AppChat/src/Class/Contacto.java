package Class;

import java.util.ArrayList;

public class Contacto {
	String nombre;
	 ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
	 int id;
	 
	 
	 
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		mensajes = new ArrayList<Mensaje>();
	}
	public Contacto(String nombre, ArrayList<Mensaje> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(ArrayList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
}
