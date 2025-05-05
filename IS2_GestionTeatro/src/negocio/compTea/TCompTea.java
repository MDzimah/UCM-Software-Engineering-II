package negocio.compTea;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import negocio.factura.TFactura;
import negocio.miemCompTea.TMiemCompTea;
import negocio.pase.TPase;
import presentacion.Convertable;

public class TCompTea implements Convertable<TCompTea> {
	private int idCompTeat;
	private String nombre;
	private String direccion;
	private boolean activo;
	private float costecontratacion;

	public TCompTea(int id, String nombre, String direccion, boolean act, float coste ){
		this.idCompTeat=id;
		this.nombre=nombre;
		this.direccion=direccion;
		this.activo=act;
		this.costecontratacion=coste;
	}
	public int getId() {
	    return idCompTeat;
	}

	public String getNombre() {
	    return nombre;
	}

	public String getDireccion() {
	    return direccion;
	}

	public boolean isActivo() {
	    return activo;
	}

	public float getCosteContratacion() {
	    return costecontratacion;
	}

	
	public void setIdCompTeat(int idCompTeat) {
	    this.idCompTeat = idCompTeat;
	}

	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	public void setDireccion(String direccion) {
	    this.direccion = direccion;
	}

	public void setActivo(boolean activo) {
	    this.activo = activo;
	}

	public void setCosteContratacion(float costecontratacion) {
	    this.costecontratacion = costecontratacion;
	}
	@Override
	public Object getColumnValue(int columnIndex) {
		switch (columnIndex) {
        case 0: return this.idCompTeat;
        case 1: return this.nombre;
        case 2: return this.direccion;
        default: return this.costecontratacion; 
	}		
	}
	@Override
	public void setColumnValue(int col, String value) throws Exception {
		  switch(col) {
	        case 0: this.idCompTeat = Integer.valueOf(value); break;
	        case 1: this.nombre= value; break;
	        case 2: this.direccion= value; break;
	        default: this.costecontratacion = Float.parseFloat(value); break;
	    }
		
	}

	
}
