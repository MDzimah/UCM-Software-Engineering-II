package integracion.facturas;

import java.util.Collection;

import negocio.facturas.TFacturas;

public interface DAOFacturas {
	public int create(TFacturas tFac);
	public int delete(int id);
	public TFacturas read(int id);
	public Collection<TFacturas> readAll();
	public int update(TFacturas tFac);
}
