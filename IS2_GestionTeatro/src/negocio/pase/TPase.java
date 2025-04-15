package negocio.pase;

import java.time.LocalDateTime;

public class TPase {
	private Integer idPase;
	private Integer idCompanyaTeatral;
	private Integer idObra;
	private String nombre;
	private boolean activo;
	private LocalDateTime fecha;
	private Integer stock;
	private float precio;
	public Integer getIdPase() {
		return idPase;
	}
	public void setIdPase(Integer idPase) {
		this.idPase = idPase;
	}
	public Integer getIdCompanyaTeatral() {
		return idCompanyaTeatral;
	}
	public void setIdCompanyaTeatral(Integer idCompanyaTeatral) {
		this.idCompanyaTeatral = idCompanyaTeatral;
	}
	public Integer getIdObra() {
		return idObra;
	}
	public void setIdObra(Integer idObra) {
		this.idObra = idObra;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public TPase(Integer idPase, Integer idCompanyaTeatral, Integer idObra, String nombre, boolean activo,
			LocalDateTime fecha, Integer stock, float precio) {
		super();
		this.idPase = idPase;
		this.idCompanyaTeatral = idCompanyaTeatral;
		this.idObra = idObra;
		this.nombre = nombre;
		this.activo = activo;
		this.fecha = fecha;
		this.stock = stock;
		this.precio = precio;
	}
	
	@Override
	public String toString() { return this.nombre + ' ' + this.fecha.toString() + ' ' + this.precio; }
}
