package negocio.factura;

import java.util.Collection;

import exceptions.*;

public interface SAFactura {
	public int crearFactura(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException;
	public TFactura read(int idFactura) throws BBDDReadException;
	public int delete(int idFactura) throws BBDDReadException, BBDDWriteException;
	public Collection<TFactura> allFacturas() throws BBDDReadException;
	public Collection<TFactura> allFacturasPorCliente(int idCliente) throws BBDDReadException ;
}
