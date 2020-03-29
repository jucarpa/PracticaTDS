package modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mensaje {

	private String texto;
	private LocalDateTime hora;
	private int emoticon;
	private int id;
	private Usuario emisor;
	private Contacto receptor;

	public Mensaje(String texto, LocalDateTime hora, int emoticon) {
		super();
		this.texto = texto;
		this.hora = hora;
		this.emoticon = emoticon;
	}
	
	public Mensaje(String texto, LocalDateTime hora, int emoticon, Usuario emisor, Contacto receptor) {
		this(texto, hora, emoticon);
		this.emisor = emisor;
		this.receptor = receptor;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public int getEmoticon() {
		return emoticon;
	}

	public void setEmoticon(int emoticon) {
		this.emoticon = emoticon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean esEmisor(Usuario user) {
		return user.equals(emisor);
	}
	
	public boolean esDeAnyo() {
		return hora.getYear() == (LocalDate.now().getYear());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
