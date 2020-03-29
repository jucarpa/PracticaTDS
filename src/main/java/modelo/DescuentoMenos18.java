package modelo;

public class DescuentoMenos18 implements IDescuento{
	
	private final double descuento = 19.99;
	
	public DescuentoMenos18() {}
	
	@Override
	public void add(IDescuento... descuentos) {
		//No es compuestos
		
	}

	@Override
	public void remove(IDescuento descuento) {
		//No es compuesto
		
	}

	@Override
	public double calcDescuento() {
		return descuento;
	}

}
