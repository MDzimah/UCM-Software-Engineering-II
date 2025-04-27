package negocio.factura;

import java.util.Collection;

import exceptions.*;

public interface SAFactura {
	public int crearFactura(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException;
	public int anyadirPaseAVenta(TLineaFactura newTLf, Collection<TLineaFactura> carrito) throws BBDDReadException;
	public int quitarPaseDeVenta(TLineaFactura tLfAQuitar, Collection<TLineaFactura> carrito) throws BBDDReadException;
	public TFactura buscarFactura(int id) throws BBDDReadException;
	public Collection<TFactura> facturasActivas() throws BBDDReadException;
	public Collection<TFactura> facturasPorCliente(int idCliente) throws BBDDReadException ;
}
