package umu.tds.apps.MavenTestProject;

import controlador.ActualizarBBDD;
import vista.VentanaMain;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {
		new VentanaMain();
		ActualizarBBDD.getUnicaInstancia();
	}
}
