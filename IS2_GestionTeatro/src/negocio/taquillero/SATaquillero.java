package negocio.taquillero;

import java.util.Collection;

public interface SATaquillero {
	public int create(TTaquillero emp);
	public TTaquillero read(int id);
	public int update(TTaquillero emp);
	public int delete (int id);
	public Collection<TTaquillero> readAll();
	
	public Collection<TTaquillero> readActive();
}
