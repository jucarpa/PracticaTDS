package componentes;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import componentes.parserwpp.modeloParse.MensajeWhatsApp;

public class MensajeEvent extends EventObject{
	protected List<MensajeWhatsApp> mensajesNuevos = new ArrayList<MensajeWhatsApp>();
	protected int movilUA, movilCI;
	
	public MensajeEvent(Object arg, List<MensajeWhatsApp> mensajesNuevos, int movilUA, int movilCI) {
		super(arg);
		this.mensajesNuevos = mensajesNuevos;
		this.movilUA = movilUA;
		this.movilCI = movilCI;
	}
	
	public List<MensajeWhatsApp> getMensajes(){
		return mensajesNuevos;
	}
	
	public int getMovilUA() {
		return movilUA;
	}
	
	public int getMovilCI() {
		return movilCI;
	}
}
