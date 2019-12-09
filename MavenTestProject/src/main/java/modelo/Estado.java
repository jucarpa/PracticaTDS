package modelo;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Estado {
	private String mensaje;
	private ImageIcon imagen;
	private int id;
	private String imagenUrl;
	
	public Estado() {
		mensaje ="";
		imagenUrl="";
	}
	
	public Estado(String mensaje, String imagenUrl) {
		super();
		this.mensaje = mensaje;
		this.imagenUrl = imagenUrl;
		try {
			imagen = new ImageIcon(Estado.class.getResource(this.imagenUrl));
			} catch(NullPointerException e) {}
	}

	public Estado(String mensaje, String imagenUrl, int id) {
		super();
		this.mensaje = mensaje;
		this.imagenUrl = imagenUrl;
		try {
		imagen = new ImageIcon(Estado.class.getResource(this.imagenUrl));
		} catch(NullPointerException e) {}
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

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(String imagenUrl) {
		this.imagenUrl = imagenUrl;
		imagen = new ImageIcon(Estado.class.getResource(this.imagenUrl));
	}
	
	public String getImagenUrl() {
		return imagenUrl;
	}
}
