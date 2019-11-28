package Clases;
import java.awt.Image;

public class Estado {

	private String mensaje;
	private Image imagen;
	
	public Estado(String mensaje, Image imagen) {
		this.mensaje = mensaje;
		this.imagen = imagen;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public Image getImagen() {
		return imagen;
	}
	
}
