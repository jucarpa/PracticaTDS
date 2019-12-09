package modelo;

import java.util.ArrayList;
import java.util.List;

public class Contacto {
	String nombre;
	List<Mensaje> mensajes = new ArrayList<Mensaje>();
	int id;

	public Contacto(String nombre) {
		this.nombre = nombre;
		mensajes = new ArrayList<Mensaje>();
	}

	public Contacto(String nombre, List<Mensaje> mensajes) {
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

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes2) {
		this.mensajes = mensajes2;
	}
}
