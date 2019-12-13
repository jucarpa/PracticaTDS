package pruebas;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import controlador.ManejadorPDF;
import modelo.CatalogoUsuarios;
import modelo.Usuario;

public class PruebasPDF {

	public static void main(String[] args) {
		for (Usuario u: CatalogoUsuarios.getUnicaInstancia().getUsuarios()) {
			try {
				ManejadorPDF.getUnicaInstancia().printContactos(u.getMovil());
			} catch (DocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
