package integracion.facturas;

import java.util.Collection;

import negocio.facturas.TLineaFactura;

public interface DAOLineaFactura {
	public int create(TLineaFactura tLineaFac);
	public int delete(int id);
	public TLineaFactura read(int id);
	public Collection<TLineaFactura> readAll();
	public int update(TLineaFactura tLineaFac);
}
