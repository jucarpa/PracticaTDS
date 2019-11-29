package modelo;

import java.awt.Image;

public class Estado {
	private String mensaje;
	private Image imagen;
	private int id;
	
	public Estado(String mensaje, Image imagen) {
		super();
		this.mensaje = mensaje;
		this.imagen = imagen;
	}
	public Estado(String mensaje, Image imagen, int id) {
		super();
		this.mensaje = mensaje;
		this.imagen = imagen;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
}
