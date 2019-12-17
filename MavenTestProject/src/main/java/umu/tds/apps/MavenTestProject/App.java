package umu.tds.apps.MavenTestProject;

import controlador.ActualizarBBDD;
import vista.VMain;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {
		new VMain();
		ActualizarBBDD.getUnicaInstancia();
	}
}
