package negocio.pase;

import java.util.Collection;

public interface SAPase {
	public int create(TPase a);
	public TPase read(int id);
	public int update(TPase a);
	public int delete (int id);
	public Collection<TPase> readAll();
}
