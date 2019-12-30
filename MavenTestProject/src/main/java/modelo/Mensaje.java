package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Mensaje {

	private String texto;
	private LocalDateTime hora;
	private int emoticon;
	private int id;
	private Usuario emisor;
	private Contacto receptor;

	public Mensaje(String texto, LocalDateTime hora, int emoticon, Usuario emisor, Contacto receptor) {
		super();
		this.texto = texto;
		this.hora = hora;
		this.emoticon = emoticon;
		this.emisor = emisor;
		this.receptor = receptor;
	}

	public Mensaje(String texto, LocalDateTime hora, int emoticon) {
		super();
		this.texto = texto;
		this.hora = hora;
		this.emoticon = emoticon;
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

}
