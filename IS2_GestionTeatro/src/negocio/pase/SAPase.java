package negocio.pase;

import java.util.Collection;

public interface SAPase {
	public int create(TPase tPase);
	public TPase read(int id);
	public int update(TPase tPase);
	public int delete (int id);
	public Collection<TPase> readAll();
	public int comprar(int idPase, int cantidad); //Devuelve el stock comprado del pase con idPase. Si cantidad > stock, entonces da todo lo que tenga (lo dijo Vicky)
	public void restaurarStock(int idPase, int cantidad);
}
