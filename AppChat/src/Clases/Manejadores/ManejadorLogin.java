package Clases.Manejadores;

import java.util.Map;

import Clases.Usuario;

public class ManejadorLogin {
	Map<String, Usuario> usuariosLogin;
	ManejadorBBDD mBBDD;
	
	public ManejadorLogin() {
		mBBDD = new ManejadorBBDD();
		usuariosLogin = mBBDD.getUsuariosInit();
	}
	
	public int getUsuarioLogin(String usuario, String contraseña) {
		if(!usuariosLogin.containsKey(usuario)) return -1;
		Usuario u =usuariosLogin.get(usuario); 
		if(!u.getContraseña().equals(contraseña)) return -2;
		return u.getIdUsuario();
	}
	
}
