package integracion.factura;

import exceptions.*;
import negocio.factura.TLineaFactura;

public interface DAOLineaFactura {
	public int create(TLineaFactura tLineaFac) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TLineaFactura read(int id) throws BBDDReadException;
	public int update(TLineaFactura tLineaFac) throws BBDDReadException, BBDDWriteException;
}
