package negocio.factura;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class TFactura {
	
	private int idFactura;
	private boolean activo;
	
	private int idCliente;
	private int idTaquillero;
	private LocalDateTime fecha;
	private Collection<TLineaFactura> carrito;
	private float importe;
	
	
	public TFactura(int idFac) {
		this.idFactura = idFac;
	}
	
	//Una factura tiene el dni o el id o ambos???
	public TFactura(int dniCliente, int dniTaquillero, Collection<TLineaFactura> carrito) {
		this.idCliente = idCliente;
		this.idTaquillero = idTaquillero;
		this.carrito = carrito;
		this.fecha = LocalDateTime.now();
	}
	
	
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	
	public void setActivo(boolean bool) {
		this.activo = bool;
	}
	
	
	
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public void setIdTaquillero(int idTaquillero) {
		this.idTaquillero = idTaquillero;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public void setCarrito(Collection<TLineaFactura> carrito) {
		this.carrito = carrito;
	}
	
	public void setImporte(float nuevoImporte) {
		this.importe = nuevoImporte;
	}
	
	
	public int getIdFactura() {
		return this.idFactura;
	}
	
	public boolean getActivo() {
		return this.activo;
	}
	
	public int getIdCliente() {
		return this.idCliente;
	}
	
	public int getIdTaquillero() {
		return this.idTaquillero;
	}
	
	public LocalDateTime getFecha() {
		return this.fecha;
	}

	public Collection<TLineaFactura> getCarrito() {
		return Collections.unmodifiableCollection(carrito);
	}
	
	public float getImporte() {
		return this.importe;
	}
}
