package pruebas;

import controlador.ControladorAppChat;

public class PruebaControlador {

	public static void main(String[] args) {
		System.out.println(ControladorAppChat.getUnicaInstancia().toString());
		System.out.println(ControladorAppChat.getUnicaInstancia().toString());
		new Prueba2();

	}

}
