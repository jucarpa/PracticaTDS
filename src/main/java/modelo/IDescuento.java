package modelo;

public interface IDescuento {
	public void add(IDescuento... descuentos);
	public void remove(IDescuento descuento);
	public double calcDescuento();
}
