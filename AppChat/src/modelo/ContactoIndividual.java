package modelo;

public class ContactoIndividual extends Contacto{
	private int movil;
	private Usuario usuario;
	
	
	public ContactoIndividual(String nombre, int movil, Usuario usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;
	}
	
	
	public ContactoIndividual(String nombre, int movil) {
		super(nombre);
		this.movil = movil;
	}


	public int getMovil() {
		return movil;
	}
	public void setMovil(int movil) {
		this.movil = movil;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
