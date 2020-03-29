package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Contacto implements Serializable{
	protected String nombre;
	protected List<Mensaje> mensajes = new ArrayList<Mensaje>();
	protected int id;
	protected PropertyChangeSupport oyentesContacto = new PropertyChangeSupport(this);

	public Contacto(String nombre) {
		this.nombre = nombre;
		mensajes = new ArrayList<Mensaje>();
	}

	public Contacto(String nombre, List<Mensaje> mensajes) {
		this.nombre = nombre;
		this.mensajes = mensajes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		String oNombre = this.nombre;
		this.nombre = nombre;
		oyentesContacto.firePropertyChange("nombreContacto", oNombre, nombre);
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes2) {
		this.mensajes = mensajes2;
	}
	
	public void addMensaje(Mensaje m) {
		mensajes.add(m);
		oyentesContacto.firePropertyChange("mensaje", null, m);
	}
	
	public void eliminarMensajes() {
		oyentesContacto.firePropertyChange("mensaje", mensajes, null);
		mensajes = new ArrayList<Mensaje>();
	}
	
	//Crear Mensaje
	public Mensaje sendMensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor) {
		Mensaje sol = new Mensaje(texto, hora, emoticono, emisor, this);
		addMensaje(sol);
		return sol;
	}
	
	public boolean esID(int id) {
		return this.id == id;
	}
	
	public ArrayList<Integer> getMensajesUsuarioDelAnyo(Usuario usuario, ArrayList<Integer> aux){
		ArrayList<Integer> sol = aux;
		mensajes.stream()
		//Obtenemos los mensajes del Usuario
		.filter(m -> m.esEmisor(usuario))
		.filter(m -> m.esDeAnyo())
		.forEach(m -> {
			//Obtenemos el Valor del Mes
			int mes = m.getHora().getMonthValue();
			int mDelMes = aux.get(mes-1);
			//Aumentamos a uno los mensajes
			mDelMes++;
			sol.set(mes - 1, mDelMes);
		});
		return sol;
	}
	
	public int getNumeroMensajes() {
		return mensajes.size();
	}
	
	public double getPorcentajeUsuario(Usuario user) {
		int mUsuario = (int) mensajes.stream()
				.filter(m -> m.esEmisor(user))
				.count();
		
		return  mUsuario / getNumeroMensajes();
	}
	
	public void addContactoChangeListener(PropertyChangeListener pcl) {
		 oyentesContacto.addPropertyChangeListener(pcl);
	}
	public void removeContactoChangeListener(PropertyChangeListener pcl) {
		 oyentesContacto.removePropertyChangeListener(pcl);
	}
	
	//Interfaz IBuscar, la utilizamos desde PanelBuscar
	public List<Mensaje> buscar(IBuscar buscador) {
		return buscador.buscar(mensajes);
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
		Contacto other = (Contacto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
