package integracion.compTea;

import java.util.Collection;

import negocio.compTea.TCompTea;

public interface DAOCompTea {
	public int create(TCompTea tCompTea);
	public int delete(int id);
	public TCompTea read(int id);
	public Collection<TCompTea> readAll();
	public int update(TCompTea tCompTea);
}
