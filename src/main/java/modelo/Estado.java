package modelo;

public class Estado {
	private String mensaje;
	private String imagenUrl;
	private int id;
	
	public Estado() {
		mensaje ="";
		imagenUrl="";
	}
	
	public Estado(String mensaje, String imagenUrl) {
		this.mensaje = mensaje;
		this.imagenUrl = imagenUrl;
	}

	public Estado(String mensaje, String imagenUrl, int id) {
		this(mensaje, imagenUrl);
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getImagenUrl() {
		return imagenUrl;
	}
	
	public void setMensajeUtl(String imagen) {
		imagenUrl = imagen;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
