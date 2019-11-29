package Adaptadores;

import java.util.HashMap;

public class PoolDAO {
	private static PoolDAO instance = null;
	private HashMap<Integer,Object> pool;
	
	private PoolDAO(){
		pool = new HashMap<Integer,Object>();
	}
	public static PoolDAO getUnicaInstancia(){
		if(instance == null) instance = new PoolDAO();
		return instance;
	}
	public void addObjeto(int id, Object object) {
		pool.put(id, object);
	}
	public Object getObjeto(int id) {
		return pool.get(id);
	}
	public boolean contiene(int id){
		return pool.containsKey(id);
	}
}
