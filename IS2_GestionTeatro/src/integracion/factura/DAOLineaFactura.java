package integracion.factura;

import negocio.factura.TLineaFactura;

public interface DAOLineaFactura {
	public int create(TLineaFactura tLineaFac);
	public int delete(int id);
	public TLineaFactura read(int id);
	public int update(TLineaFactura tLineaFac);
}
