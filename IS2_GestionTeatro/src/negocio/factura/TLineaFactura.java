package negocio.factura;

public class TLineaFactura {

	private int idLineaFactura;
	
	private int idFactura;
	private int idPase;
	
	private boolean activo;
	private int cantidad;
	private float precioVenta;

	
	public TLineaFactura(int idFactura, int idPase, 
			boolean activo,	int cantidad, float precioVenta) {
		this.idFactura = idFactura;
		this.idPase = idPase;
		this.activo = activo;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
	}
	
	public TLineaFactura(int idPase, int cantidad) {
		this.idPase = idPase;
		this.cantidad = cantidad;
	}
	
	
	/*--GETTERS--*/

	public int getIdLineaFactura() { return this.idLineaFactura; }
	
	public int getIdFactura() {	return this.idFactura; }
	
	public int getIdPase() { return this.idPase; }
	
	public boolean getActivo() { return this.activo; }
	
	public int getCantidad() { return this.cantidad; }
	
	public float getPrecioVenta() {	return this.precioVenta; }
	
	
	/*--SETTERS--*/
	
	public void setIdLineaFactura(int idLineaFactura) { this.idLineaFactura = idLineaFactura; }
	
	public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
	
	public void setIdPase(int idPase) {	this.idPase = idPase; }
	
	public void setActivo(boolean activo) { this.activo = activo; }
	
	public void setCantidad(int cantidad) {	this.cantidad = cantidad; }
	
	public void setPrecioVenta(float importe) {	this.precioVenta = importe;	}
}
