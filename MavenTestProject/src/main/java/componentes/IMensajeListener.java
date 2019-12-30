package componentes;

import java.util.EventListener;
import java.util.EventObject;

public interface IMensajeListener extends EventListener{
	public void mensajesNuevos(EventObject e);
}
