package componentes.bin.parse;

import java.util.EventObject;
import componentes.bin.parse.modelo.Plataforma;

@SuppressWarnings("serial")
public class ParseEvent extends EventObject {
	
	protected String fichero, formatDate;
	protected Plataforma p;
	
	public ParseEvent(Object fuente, String fichero,String formatDate, Plataforma p) {
		super(fuente);
		this.fichero = fichero;
		this.formatDate = formatDate;
		this.p = p;
	}
	public String getFichero(){ 
		return fichero;
	}
	
	public String getFormatDate() {
		return formatDate;
	}
	
	public Plataforma getPlataforma(){ 
		return p;
	}
}
