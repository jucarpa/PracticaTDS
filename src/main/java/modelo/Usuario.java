package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Usuario implements Serializable{
	private int id;
	private String nombre;
	private Date fechaNacimiento;
	private int movil;
	private String email;
	private String usuario;
	private String password;
	private String imagen;
	private boolean premium;
	private String saludo;
	private List<Contacto> contactos;
	private int saldo;
	
	//PARTE OPCIONAL
	private Estado estado = null;
	
	//Clase utilidad para los atributos que varían a lo largo del tiempo
	private PropertyChangeSupport oyentesUsuario = new PropertyChangeSupport(this);
	
	//Constructores
	public Usuario(String nombre, Date fechaNacimiento, int movil, String usuario, String password,
			String email) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.usuario = usuario;
		this.password = password;
		this.email = email;
		premium = false;
		contactos = new ArrayList<Contacto>();
		imagen = "D:\\Proyeccto TDS Clone\\src\\main\\java\\imagenes\\ImagenUsuarioDef.png";
		saludo = "Hola AppChat!";
	}
	
	public Usuario(String nombre, Date fechaNacimiento, int movil, String usuario, String password,
			String imagen, boolean premium, String email) {
		
		this(nombre, fechaNacimiento, movil, usuario, password, email);
		this.imagen = imagen;
		this.premium = premium;
	}
	
	public Usuario(int id, String nombre, Date fechaNacimiento, int movil, String email, String usuario,
			String password, String urlImagen, boolean premium, List<Contacto> contactos, List<Grupo> gruposAdmin) {
		
		this(nombre, fechaNacimiento, movil, usuario, password, urlImagen, premium, email);
		this.id = id;
		this.contactos = contactos;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Estado getEstado() {
		return estado;
	}
	
	//Getters/Setters
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
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getMovil() {
		return movil;
	}

	public void setMovil(int movil) {
		this.movil = movil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		String oldIm = this.imagen;
		this.imagen = imagen;
		oyentesUsuario.firePropertyChange("imagen", oldIm, imagen);
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		String oldSaludo = this.saludo;
		this.saludo = saludo;
		oyentesUsuario.firePropertyChange("saludo", oldSaludo, saludo);
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	//Obtener Contacto por ID
	public Contacto getContacto(int id) {
		return contactos.stream().filter(c -> c.esID(id))
		.findFirst().orElse(null);
	}
	
	//Crear ContactoIndividual para no romper los patrones GRASP
	public ContactoIndividual crearCI(String nombre, int movil, Usuario usuario) {
		ContactoIndividual sol = new ContactoIndividual(nombre, movil, usuario);
		addContacto(sol);
		return sol;
	}
	
	//Crear Grupo para no romper los patrones GRASP
	public Grupo crearGrupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		Grupo sol = new Grupo(nombre, contactos, this);
		addContacto(sol);
		return sol;
	}
	
	public boolean removeContacto(Contacto aux) {
		oyentesUsuario.firePropertyChange("contacto", aux, null);
		return contactos.remove(aux);
	}
	
	public boolean addContacto(Contacto aux) {
		oyentesUsuario.firePropertyChange("contacto", null, aux);
		return contactos.add(aux);
	}
	
	
	//Devuelve True si el Usuario tiene un Contacto
	//Individidual del Usuario u
	public boolean tieneContacto(Usuario u) {
		return contactos.stream()
		.filter(c -> c instanceof ContactoIndividual)
		.map( c -> (ContactoIndividual) c)
		.filter(ci -> ci.esUsuario(u))
		.findAny().isPresent();
	}
	
	public ContactoIndividual getContacto(Usuario u) {
		return contactos.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map( c -> (ContactoIndividual) c)
				.filter(ci -> ci.esUsuario(u))
				.findFirst().orElse(null);
	}
	
	public boolean isPassword(String pass) {
		return password.equals(pass);
	}
	
	
	//Devuelve true si existe Un Contacto son ese numero
	public boolean existeContacto(int movil) {
		return null != contactos.stream()
		.filter(c -> c instanceof ContactoIndividual)
		.map(c -> (ContactoIndividual) c)
		.filter(c -> c.esMovil(movil))
		.findFirst().orElse(null);
	}
	
	
	public boolean esMovil(int movil) {
		return this.movil == movil;
	}
	
	public ContactoIndividual getContacto(String nombre) {
		return contactos.stream()
		.filter(c -> c instanceof ContactoIndividual)
		.map(c -> (ContactoIndividual) c)
		.filter(c -> c.esNombre(nombre))
		.findFirst().orElse(null);
	}
	
	public boolean existeContacto(String nombre) {
		return null != getContacto(nombre);
	}
	
	public int getSaldo() {
		return saldo;
	}
	
	public int anyadirSaldo(int saldo) {
		this.saldo += saldo;
		return saldo;
	}
	
	public boolean pagar(double precio) {
		double descuento = calcularDescuento();
		precio -= descuento;
		
		if(saldo >= precio) {
			saldo -= precio;
			setPremium(true);
			return true;
		}
		return false;
	}
	
	public double calcularDescuento() {
		IDescuento descuentos = new DescuentoCompuesto();
		LocalDateTime primerMensaje = getPrimerMensaje();
		if(primerMensaje != null)
			descuentos.add(new DescuentoVeterano(primerMensaje));
		if(esMenor())
			descuentos.add(new DescuentoMenos18());
		return descuentos.calcDescuento();
		
	}
	
	private LocalDateTime getPrimerMensaje() {
		return contactos.stream()
		.flatMap(c -> c.getMensajes().stream())
		.filter(m -> m.getEmisor().equals(this))
		.map(m -> m.getHora())
		.sorted((h1,h2) -> h1.compareTo(h2))
		.findFirst().orElse(null);
	}
	
	private boolean esMenor() {
		double tiempo = Date.from(Instant.now()).getTime() - fechaNacimiento.getTime();
		double años = tiempo / 1000 / 60 / 60 / 24 / 365;
		return años >= 18.00 ?  true : false;
	}
	
	public List<ContactoIndividual> getContactosIndividuales() {
		return contactos.stream()
			.filter(c -> c instanceof ContactoIndividual)
			.map(c -> (ContactoIndividual) c)
			.collect(Collectors.toList());
	}
	
	public List<Grupo> getGrupos() {
		return contactos.stream()
				.filter(c -> c instanceof Grupo)
				.map(c -> (Grupo) c)
				.collect(Collectors.toList());
	}
	
	public ArrayList<Integer> getNumeroMensajesDelAnyo(){
		ArrayList<Integer> sol = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0));
		
		for(Contacto c : contactos)
			sol = c.getMensajesUsuarioDelAnyo(this, sol);
		
		return sol;
	}
	
	public HashMap<Grupo, Double> getGruposTopMensajes() {
		HashMap<Grupo,Double> sol = new HashMap<Grupo,Double>();
		List<Grupo> gruposAux = getGrupos().stream()
		.sorted((g1,g2) -> {
			return g1.getNumeroMensajes() - g2.getNumeroMensajes();
		}).collect(Collectors.toList());
		
		int nGrupos = 6;
		if(gruposAux.size() < nGrupos)
			nGrupos = gruposAux.size();
		for(int i = 0; i < nGrupos; i++) {
			double aux = gruposAux.get(i).getPorcentajeUsuario(this);
			sol.put(gruposAux.get(i), aux);
		}
		return sol;
	}
	
	//Añadir y Eliminar Oyentes
	public void addUsuarioChangeListener(PropertyChangeListener pcl) {
		 oyentesUsuario.addPropertyChangeListener(pcl);
	}
	
	public void removeUsuarioChangeListener(PropertyChangeListener pcl) {
		 oyentesUsuario.removePropertyChangeListener(pcl);
	}
	
	
	
	
	//Equals
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
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	//Devuelve el usuario segun su nombre
	public Usuario getUsuario(String nombre) {
		return getContacto(nombre).getUsuario();	
	}
	
	/*
	 * APARTADO OPCIONAL
	 */
	
	public List<Estado> getEstadosContactos(){
		return getContactosIndividuales().stream()
				.filter(c -> c.tieneEstado())
				.map(c -> c.getEstadoUsuario())
				.collect(Collectors.toList());
	}
	
	public boolean tieneEstado() {
		return estado != null;
	}
	
	public List<ContactoIndividual> getCIconEstado(){
		return getContactosIndividuales().stream()
				.filter(c -> c.tieneEstado())
				.collect(Collectors.toList());
	}
	
	public Estado crearEstado(String urlImagen, String comentario) {
		Estado sol = new Estado(comentario, urlImagen);
		estado = sol;
		oyentesUsuario.firePropertyChange("estado", null, sol);
		return estado;
	}
}
