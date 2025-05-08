package negocio.pase;

import java.time.LocalDateTime;
import java.util.Date;

import presentacion.Convertable;

public class TPase implements Convertable<TPase> {
	private int idPase;
	private int idCompanyaTeatral;
	private int idObra;
	private boolean activo;
	private LocalDateTime fecha;
	private int stock;
	private float precio;
	public TPase(int idPase, int idCompanyaTeatral, int idObra, boolean activo,
			LocalDateTime selectedDate, int stock, float precio) {
		this.idPase = idPase;
		this.idCompanyaTeatral = idCompanyaTeatral;
		this.idObra = idObra;
		this.activo = activo;
		this.fecha = selectedDate;
		this.stock = stock;
		this.precio = precio;
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
	@Override
	public Object getColumnValue(int columnIndex) {
		switch (columnIndex) {
	        case 0: return this.idPase;
	        case 1: return this.idCompanyaTeatral;
	        case 2: return this.idObra;
	        case 3: return this.fecha;
	        case 4: return this.stock;
	        default: return this.precio;
		}
	}

	@Override
	public void setColumnValue(int col, String value) {
	    switch(col) {
	        case 0: this.idPase = Integer.valueOf(value); break;
	        case 1: this.idCompanyaTeatral = Integer.valueOf(value); break;
	        case 2: this.idObra = Integer.valueOf(value); break;
	        case 3: this.fecha = LocalDateTime.parse(value); break;
	        case 4: this.stock = Integer.parseInt(value); break;
	        default: this.precio = Float.parseFloat(value); break;
	    }
	}
	
}
