package negocio.compTea;

import java.util.Collection;
import java.util.Collections;

import negocio.miemCompTea.TMiemCompTea;
import negocio.pase.TPase;

public class TCompTea {
	private int idCompTeat;
	private String nombre;
	private String direccion;
	private boolean activo;
	private float costecontratacion;
	private Collection<TMiemCompTea> miembros;
	private Collection<TPase> pases;

	public TCompTea(int id, String nombre, String direccion, boolean act, float coste, Collection<TPase> object ){
		this.idCompTeat=id;
		this.nombre=nombre;
		this.direccion=direccion;
		this.activo=act;
		this.costecontratacion=coste;
		this.pases= object;
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

	public Collection<TPase> getPases() {
		return Collections.unmodifiableCollection(pases);
	}
	
	public Collection<TMiemCompTea> getMiembros() {
		return Collections.unmodifiableCollection(miembros);
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

	public void setPases(Collection<TPase> pases) {
	    this.pases = pases;
	}
	
	
}
