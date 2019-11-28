package Clases;
import java.util.ArrayList;

public class Contacto {
	 String nombre;
	 ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
	 
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public ArrayList<Mensaje> getMensajes(){
		return mensajes;
	}
	
	public void addMensaje(Mensaje m) {
		mensajes.add(m);
	}
	
	public void setMensajes(ArrayList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public Contacto(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre;
	}
}
