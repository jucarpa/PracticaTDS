package Clases.DAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Clases.Contacto;
import Clases.Grupo;
import Clases.Usuario;
import beans.Entidad;
import javafx.scene.image.Image;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class UsuarioDAO {
	ServicioPersistencia servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();

	public static void create(Usuario usuario) {
		
	}

	public void delete(Usuario usuario) {
		
	}
	
	public Usuario recuperarUsuario(int id) {
		if ( !PoolDAO.getInstance().contains(id)){
			Entidad eUsuario = servPersistencia.recuperarEntidad(id);
			return entidadAUsuario(eUsuario);
			}
			else
			return (Usuario) PoolDAO.getInstance().getObject(id);
	}
	
	private Usuario entidadAUsuario(Entidad eUsuario) {
		int id = eUsuario.getId();
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario,"nombre");
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		String movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, "movil");
		String usuarioF = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		String contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		String premium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium");
		
		Usuario usuario = new Usuario(nombre, LocalDate.parse(fechaNacimiento), Integer.valueOf(movil), usuarioF, contraseña, new Image(imagen), (premium.equals("true") ? true : false));
		usuario.setIdUsuario(id);
		PoolDAO.getInstance().addObject(usuario.getIdUsuario(),usuario);
		String contactosS = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos");
		String gruposAdminS = servPersistencia.recuperarPropiedadEntidad(eUsuario, "gruposAdmin");
		if (!contactosS.equals("") && contactosS != null){
			List<Contacto> contactos = obtenerContactos(contactosS);
			usuario.setContactos((ArrayList<Contacto>) contactos);
		}
		if (!gruposAdminS.equals("") && gruposAdminS != null){
			List<Grupo> gruposAdmin = obtenerGruposAdmin(gruposAdminS);
			usuario.setGruposAdmin(gruposAdmin);
		}
		return usuario;
		}

	private List<Grupo> obtenerGruposAdmin(String gruposAdminS) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Contacto> obtenerContactos(String contactosS) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Usuario> getAll() {
		return null;
	}


}
