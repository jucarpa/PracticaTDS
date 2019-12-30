package controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import componentes.IEncendidoListener;
import componentes.IMensajeListener;
import componentes.MensajeEvent;
import componentes.parserwpp.modeloParse.MensajeWhatsApp;
import componentes.parserwpp.modeloParse.Plataforma;
import componentes.parserwpp.parserParse.SimpleTextParser;

public class ControladorCargadorMensaje {
	private static ControladorCargadorMensaje unicaInstancia;
	private Vector mensajesListeners;
	public static ControladorCargadorMensaje getUnicaInstancia() {
		if(unicaInstancia == null)
			unicaInstancia = new ControladorCargadorMensaje();
		return unicaInstancia;
	}
	
	private ControladorCargadorMensaje() { 
		mensajesListeners = new Vector();
	}
	
	public void setFichero(String ruta, Plataforma p, int movilUA, int movilCI) {
		
		//CHEKEO DEL FORMATDATEEEE
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy H:mm");
		FileReader input = null;
		String line = "";
		try {
			input = new FileReader(ruta);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader bufRead = new BufferedReader(input);
		try {
			if((line = bufRead.readLine()) != null){
				List<MensajeWhatsApp> mWpp = new ArrayList<MensajeWhatsApp>();
				if(Plataforma.IOS == p)
					mWpp = SimpleTextParser.parse(ruta, "d/M/yy H:mm:ss", p);
				else {
					String fecha = line.substring(0, line.indexOf("-"));
					try {
					LocalDateTime localDate = LocalDateTime.parse(fecha.trim(), formatter);
					mWpp = SimpleTextParser.parse(ruta, "d/M/yy H:mm", p);
					}catch (DateTimeParseException e) {
						mWpp = SimpleTextParser.parse(ruta, "d/M/yyyy, H:mm", p);
					}
				}
			
				MensajeEvent event = new MensajeEvent(this, mWpp, movilUA, movilCI);
				
				notificarMensajesNuevos(event);
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void addMensajeListener(IMensajeListener listener) {
		mensajesListeners.addElement(listener);
	}
	
	public synchronized void removeMensajeListener(IMensajeListener listener) {
		mensajesListeners.removeElement(listener);
	}
	
	private void notificarMensajesNuevos(MensajeEvent event) {
		Vector lista;
		synchronized(this){
			lista=(Vector) mensajesListeners.clone();
		}
		for(int i=0; i<lista.size(); i++){
			IMensajeListener listener=(IMensajeListener)lista.elementAt(i);
			listener.mensajesNuevos(event);
		}
	}
	
}
