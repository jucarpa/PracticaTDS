package persistencia;

import java.util.Hashtable;

public class PoolDAO {
	private static PoolDAO instance = null;
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public static PoolDAO getUnicaInstancia() {
		if (instance == null)
			instance = new PoolDAO();
		return instance;
	}

	public void addObjeto(int id, Object object) {
		pool.put(id, object);
	}

	public Object getObjeto(int id) {
		return pool.get(id);
	}

	public boolean contiene(int id) {
		return pool.containsKey(id);
	}
	
	public void update() {
		instance = new PoolDAO();
	}
}
