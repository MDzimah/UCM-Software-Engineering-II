package integracion.obra;

import java.util.Collection;

import negocio.obra.TObra;

public interface DAOObra {
	public int create(TObra tObra);
	public int delete(int id);
	public TObra read(int id);
	public Collection<TObra> readAll();
	public int update(TObra tObra);
}
