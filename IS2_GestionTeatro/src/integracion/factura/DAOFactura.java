package integracion.factura;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.factura.TFactura;

public interface DAOFactura {
	public int create(TFactura tFac) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TFactura read(int id) throws BBDDReadException;
	public Collection<TFactura> readAll() throws BBDDReadException;
	public int update(TFactura tFac) throws BBDDReadException, BBDDWriteException;
}
