package negocio.factura;

import java.util.Collection;

import exceptions.*;

public interface SAFactura {
	public boolean anyadirPaseAVenta(TLineaFactura newTLf, Collection<TLineaFactura> carrito) throws BBDDReadException;
	public boolean quitarPaseDeVenta(TLineaFactura tLfAQuitar, Collection<TLineaFactura> carrito) throws BBDDReadException;
	public int crearFactura(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException;
	public TFactura read(int idFactura) throws BBDDReadException;
	public Collection<TFactura> allFacturas() throws BBDDReadException;
	public Collection<TFactura> allFacturasPorCliente(int idCliente) throws BBDDReadException ;
}
