package negocio.factura;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class TFactura {
	
	private int idFactura;
	
	private int idCliente;
	private int idTaquillero;
	
	private boolean activo;
	private LocalDateTime fecha;
	private Collection<TLineaFactura> carrito;
	private float subtotal;
	private float importe;
	
	public TFactura (int idCl, int idTaq, boolean act, 
			LocalDateTime fecha, Collection<TLineaFactura> carrito, float subtotal, float importe) 
	{
		this.idCliente = idCl;
		this.activo = act;
		this.fecha = fecha;
		this.carrito = carrito;
		this.subtotal = subtotal;
		this.importe = importe;
	}
	
	//Para poder buscar factura
	public TFactura (int idFac) { this.idFactura = idFac; }
	
	
	/*--GETTERS--*/
	
	public int getIdFactura() {	return this.idFactura; }
	
	public int getIdCliente() {	return this.idCliente; }
	
	public int getIdTaquillero() { return this.idTaquillero; }
	
	public boolean getActivo() { return this.activo; }
	
	public LocalDateTime getFecha() { return this.fecha; }

	public Collection<TLineaFactura> getCarrito() {	return Collections.unmodifiableCollection(carrito); }
	
	public float getSubtotal() { return this.subtotal; }
	
	public float getImporte() {	return this.importe; }
	
	
	/*--SETTERS--*/
	
	public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
	
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
	
	public void setIdTaquillero(int idTaquillero) { this.idTaquillero = idTaquillero; }
	
	public void setActivo(boolean bool) { this.activo = bool; }
	
	public void setFecha(LocalDateTime fecha) {	this.fecha = fecha; }
	
	public void setCarrito(Collection<TLineaFactura> carrito) {	this.carrito = carrito;	}
	
	//Returns the last index of the carrito
	public int addToCarrito(TLineaFactura tLf) { this.carrito.add(tLf); return this.carrito.size()-1; }
	
	public void removeFromCarrito(TLineaFactura tLf) { this.carrito.remove(tLf); }
	
	public void setSubtotal(float nuevoSubtotal) { this.subtotal = nuevoSubtotal; }
	
	public void setImporte(float nuevoImporte) { this.importe = nuevoImporte; }

	
	@Override
	public String toString() { return Integer.toString(this.idFactura); }
}
