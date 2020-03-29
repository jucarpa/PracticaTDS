package modelo;

import java.util.ArrayList;
import java.util.List;

public class DescuentoCompuesto implements IDescuento{

	private List<IDescuento> listaDescuentos;

	public DescuentoCompuesto() {
		listaDescuentos = new ArrayList<>();
	}
	@Override
	public void add(IDescuento... descuentos) {
		for (IDescuento d : descuentos)
			listaDescuentos.add(d);
	}
	
	@Override
	public void remove(IDescuento descuento) {
		listaDescuentos.remove(descuento);
	}

	@Override
	public double calcDescuento() {
		return listaDescuentos.stream()
				.mapToDouble(IDescuento::calcDescuento)
				.sum();
	}

}
