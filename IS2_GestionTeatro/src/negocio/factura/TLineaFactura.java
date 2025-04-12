package negocio.factura;

import java.time.LocalDateTime;

public class TLineaFactura {

	private Integer idLineaFactura;
	
	private Integer idFactura;
	private Integer idPase;
	
	private String tituloObra;
	private LocalDateTime fechaPase;
	private Integer cantidad;
	private float precioVenta;
	
	public TLineaFactura() {
		
		this.cantidad = null;
	}
	
	public TLineaFactura(Integer idPase, int cantidad) {
		this.idPase = idPase;
		this.cantidad = cantidad;
	}
	
	
	// - GETTERS AND SETTERS -
	
	public void setIdPase(Integer idPase) {
		this.idPase = idPase;
	}
	
	
	
	public void setTituloObra(String titulo) {
		this.tituloObra = titulo;
	}
	
	public void setFechaPase(LocalDateTime fecha) {
		this.fechaPase = fecha;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void setPrecioVenta(float importe) {
		this.precioVenta = importe;
	}
	
	

	public Integer getIdLineaFactura() {
		return this.idLineaFactura;
	}
	
	public Integer getIdFactura() {
		return this.idFactura;
	}
	
	public Integer getIdPase() {
		return this.idPase;
	}
	
	public String getTituloObra() {
		return this.tituloObra;
	}
	
	public LocalDateTime getFechaPase() {
		return this.fechaPase;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public float getPrecioVenta() {
		return this.precioVenta;
	}
}
