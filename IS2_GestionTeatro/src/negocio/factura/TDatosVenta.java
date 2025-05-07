package negocio.factura;

import java.util.Collection;
import java.util.Collections;

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
	
	public Collection<TLineaFactura> getCarrito() { return this.carrito; }
	
	
	/*--SETTERS--*/
	
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
	
	public void setIdTaquillero(int idTaquillero) {	this.idTaquillero = idTaquillero; }
	
	//Evitamos ligar el carrito que recibe a otros lugares del código
	public void setIdPase(Collection<TLineaFactura> carrito) { this.carrito = Collections.unmodifiableCollection(carrito); }
}
