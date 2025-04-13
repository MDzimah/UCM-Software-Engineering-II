package negocio.factura;

import java.time.LocalDateTime;

public class TLineaFactura {

	private int idLineaFactura;
	
	private int idFactura;
	private int idPase;
	
	private String tituloObra;
	private LocalDateTime fechaPase;
	private int cantidad;
	private float precioVenta;
	
	
	public TLineaFactura(int idPase, int cantidad) {
		this.idPase = idPase;
		this.cantidad = cantidad;
	}
	
	public TLineaFactura(String titObra, LocalDateTime fechaPase, int ctdad) {
		this.tituloObra = titObra;
		this.fechaPase = fechaPase;
		this.cantidad = ctdad;
	}
	
	
	/*--GETTERS--*/

	public int getIdLineaFactura() { return this.idLineaFactura; }
	
	public int getIdFactura() {	return this.idFactura; }
	
	public int getIdPase() { return this.idPase; }
	
	public String getTituloObra() {	return this.tituloObra; }
	
	public LocalDateTime getFechaPase() { return this.fechaPase; }
	
	public int getCantidad() { return this.cantidad; }
	
	public float getPrecioVenta() {	return this.precioVenta; }
	
	
	/*--SETTERS--*/
	
	public void setIdLineaFactura(int idLineaFactura) { this.idLineaFactura = idLineaFactura; }
	
	public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
	
	public void setIdPase(int idPase) {	this.idPase = idPase; }
	
	public void setTituloObra(String titulo) { this.tituloObra = titulo; }
	
	public void setFechaPase(LocalDateTime fecha) {	this.fechaPase = fecha;	}
	
	public void setCantidad(int cantidad) {	this.cantidad = cantidad; }
	
	public void setPrecioVenta(float importe) {	this.precioVenta = importe;	}
}
