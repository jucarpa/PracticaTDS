package Clases;

import java.util.ArrayList;

public class ContactoIndividual extends Contacto{
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + usuario.getNombre() + "," + movil + "]";
	}

	private int movil;
	private Usuario usuario;
	private int idCI;
	
	public int getId() {
		return idCI;
	}
	
	public void setId(int id) {
		idCI = id;
	}
	
	public int getMovil() {
		return movil;
	}
	
	public void setMovil(int movil) {
		this.movil = movil;
	}
	
	public ContactoIndividual(String nombre, int movil, int id) {
		super(nombre);
		// TODO Auto-generated constructor stub
		this.movil = movil;
		this.idCI = id;
	}
	
	public ContactoIndividual(String nombre, int movil, Usuario usuario) {
		super(nombre);
		// TODO Auto-generated constructor stub
		this.movil = movil;
		this.usuario = usuario;
	}
	
	public ContactoIndividual(String nombre, int movil, Usuario usuario, int idCI) {
		this(nombre, movil, usuario);
		this.idCI = idCI;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario u) {
		usuario = u;
	}
	

}
