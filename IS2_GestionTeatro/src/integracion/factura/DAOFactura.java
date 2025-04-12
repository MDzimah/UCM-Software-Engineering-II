package integracion.factura;

import java.util.Collection;

import negocio.factura.TFactura;

public interface DAOFactura {
	public int create(TFactura tFac);
	public int delete(int id);
	public TFactura read(int id);
	public Collection<TFactura> readAll();
	public int update(TFactura tFac);
}
