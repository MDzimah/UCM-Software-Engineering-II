package integracion.pase;

import java.util.Collection;

import negocio.obra.TObra;

public interface DAOPase {
	public int create(TObra tObra);
	public int delete(int id);
	public TObra read(int id);
	public Collection<TObra> readAll();
	public int update(TObra tObra);
}
