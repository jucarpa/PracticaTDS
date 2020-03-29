package modelo;

@SuppressWarnings("serial")
public class ContactoIndividual extends Contacto {
	private int movil;
	private Usuario usuario;
	
	//Constructores
	public ContactoIndividual(String nombre, int movil) {
		super(nombre);
		this.movil = movil;
	}
	
	public ContactoIndividual(String nombre, int movil, Usuario usuario) {
		this(nombre, movil);
		this.usuario = usuario;
	}
	
	//Getters/Setters
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
	
	//Devuelve true si u es el Usuario del CI
	public boolean esUsuario(Usuario u) {
		return usuario.equals(u);
	}
	
	public boolean esMovil(int movil) {
		return this.movil == movil;
	}
	
	public boolean esNombre(String nombre) {
		return this.nombre.equals(nombre);
	}
	
	public String getImagenUsuario() {
		return usuario.getImagen();
	}
	
	public void setImagenUsuario(String imagen) {
		String oldImagen = usuario.getImagen();
		usuario.setImagen(imagen);
		oyentesContacto.firePropertyChange("contactoImagen", oldImagen, imagen);
	}

	/*
	 * APARTADO OPCIONAL
	 */
	
	public boolean tieneEstado() {
		return usuario.tieneEstado();
	}
	
	public Estado getEstadoUsuario() {
		return usuario.getEstado();
	}
}
