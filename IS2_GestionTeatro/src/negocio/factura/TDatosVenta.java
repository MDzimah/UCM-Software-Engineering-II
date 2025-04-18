package negocio.factura;

import java.util.Collection;

public class TDatosVenta {
	
	private int idCliente;
	private int idTaquillero;
	
	private Collection<TLineaFactura> carrito;
	
	public TDatosVenta(int idCliente, int idTaquillero, Collection<TLineaFactura> carrito) {
		this.idCliente = idCliente;
		this.idTaquillero = idTaquillero;
		this.carrito = carrito;
	}
	
	
	/*--GETTERS--*/

	public int getIdCliente() { return this.idCliente; }
	
	public int getIdTaquillero() {	return this.idTaquillero; }
	
	public Collection<TLineaFactura> getCarrito() { Collections.unmodifiableCollection(carrito); }
	
	
	/*--SETTERS--*/
	
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
	
	public void setIdTaquillero(int idTaquillero) {	this.idTaquillero = idTaquillero; }
	
	public void setIdPase(Collection<TLineaFactura> carrito) { this.carrito = carrito; }
}
