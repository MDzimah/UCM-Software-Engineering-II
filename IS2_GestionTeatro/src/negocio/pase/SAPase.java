package negocio.pase;

import java.util.Collection;

public interface SAPase {
	public int create(TPase tPase);
	public TPase read(int id);
	public int update(TPase tPase);
	public int delete (int id);
	public Collection<TPase> readAll();
}
