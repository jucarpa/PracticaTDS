package persistencia;

import java.util.List;

import modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual);

	public void borrarContactoIndividual(ContactoIndividual contactoIndividual);

	public void modificarContactoIndividual(ContactoIndividual contactoIndividual);

	public ContactoIndividual recuperarContactoIndividual(int codigo);

	public List<ContactoIndividual> recuperarTodosContactoIndividuales();
}
