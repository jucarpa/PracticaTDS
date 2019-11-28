package Clases;
import java.time.LocalTime;
import java.util.Date;

public class Mensaje {
	private String texto;
	private LocalTime hora;
	private int emoticon; //implementar libreria
	private int idMnsg;
	
	private Contacto receptor;
	private Usuario emisor;
	public Mensaje(String texto, LocalTime hora, int emoticon, Contacto receptor, Usuario emisor) {
		super();
		this.texto = texto;
		this.hora = hora;
		this.emoticon = emoticon;
		this.receptor = receptor;
		this.emisor = emisor;
	}
	
	public Mensaje(String texto, LocalTime hora, int emoticon, Contacto receptor, Usuario emisor, int idMnsg) {
		this(texto, hora,  emoticon,  receptor, emisor);
		this.idMnsg = idMnsg;
	}
	
	public Mensaje(String texto, LocalTime hora, int emoticono, int idM) {
		this.texto = texto;
		this.hora = hora;
		emoticon = emoticono;
		idMnsg = idM;
	}
	
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}
	public int getId() {
		return idMnsg;
	}
	
	public void setId(int id) {
		idMnsg = id;
	}
	
	public Usuario getEmisor(){
		Usuario aux = new Usuario(emisor.getNombre(), emisor.getFechaNacimiento(), emisor.getMovil(),"", "");
		return aux;
	}
	
	public Contacto getReceptor() {
		return receptor;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public LocalTime getHora() {
		return hora;
	}
	
	public int getEmoticon() {
		return emoticon;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + texto + "," + emisor.getNombre() +"]";
	}
	
	
}
