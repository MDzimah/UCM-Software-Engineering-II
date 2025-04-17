package negocio.factura;

import java.util.Collection;

import exceptions.*;

public interface SAFactura {
	public int create(TDatosVenta tDv);
	public TFactura read(int id) throws BBDDReadException;
	public int update(TFactura tFac) throws BBDDReadException, BBDDWriteException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TFactura> readAll() throws BBDDReadException;
}
