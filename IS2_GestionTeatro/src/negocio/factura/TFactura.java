package negocio.factura;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class TFactura {
	
	private Integer idFactura;
	private boolean activo;
	
	private Integer idCliente;
	private Integer idTaquillero;
	private LocalDateTime fecha;
	private Collection<TLineaFactura> carrito;
	private float importe;
	
	
	public TFactura() {
		this.idFactura = null;
		this.activo = false;
		
		this.idCliente = null;
		this.idTaquillero = null;
		this.fecha = null;
		this.carrito = null;
	}
	
	
	public TFactura(Integer dniCliente, Integer dniTaquillero, Collection<TLineaFactura> carrito) {
		this.idCliente = idCliente;
		this.idTaquillero = idTaquillero;
		this.carrito = carrito;
		
		this.fecha = LocalDateTime.now();
	}
	
	
	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}
	
	public void setActivo(boolean bool) {
		this.activo = bool;
	}
	
	
	
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
	public void setIdTaquillero(Integer idTaquillero) {
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
	
	
	public Integer getIdFactura() {
		return this.idFactura;
	}
	
	public boolean getActivo() {
		return this.activo;
	}
	
	public Integer getIdCliente() {
		return this.idCliente;
	}
	
	public Integer getIdTaquillero() {
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
