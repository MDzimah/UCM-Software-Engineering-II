package negocio.pase;

import java.time.LocalDateTime;

public class TPase {
	private int idPase;
	private int idCompanyaTeatral;
	private int idObra;
	private boolean activo;
	private LocalDateTime fecha;
	private int stock;
	private float precio;
	public TPase(int idPase, int idCompanyaTeatral, int idObra, boolean activo,
			LocalDateTime fecha, int stock, float precio) {
		this.idPase = idPase;
		this.idCompanyaTeatral = idCompanyaTeatral;
		this.idObra = idObra;
		this.activo = activo;
		this.fecha = fecha;
		this.stock = stock;
		this.precio = precio;
	}
	public TPase(int IDPase) {
		idPase = IDPase;
	}
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
}
