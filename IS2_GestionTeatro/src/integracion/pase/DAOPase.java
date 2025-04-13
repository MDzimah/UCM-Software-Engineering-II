package integracion.pase;

import java.util.Collection;

import negocio.pase.TPase;

public interface DAOPase {
	public int create(TPase tObra);
	public int delete(int id);
	public TPase read(int id);
	public Collection<TPase> readAll();
	public int update(TPase tObra);
}
