package modelo;

import java.time.LocalDateTime;

//Descuento desde la primera vez que enviaron un mensaje;
public class DescuentoVeterano implements IDescuento{

	private LocalDateTime primerMensaje;
	
	public DescuentoVeterano(LocalDateTime primerMEnsaje) {
		this.primerMensaje = primerMEnsaje;
	}
	
	@Override
	public void add(IDescuento... descuentos) {
		//No es Compuesto
		
	}

	@Override
	public void remove(IDescuento descuento) {
		// No es Compuesto
		
	}

	//ponemos un Límite
	@Override
	public double calcDescuento() {
		double descuento = 4.99;
		
		double tiempo = (LocalDateTime.now().getDayOfYear() - primerMensaje.getDayOfYear()) * 0.05;
		if(tiempo > descuento)
				return descuento;
		return tiempo;
	}

}
