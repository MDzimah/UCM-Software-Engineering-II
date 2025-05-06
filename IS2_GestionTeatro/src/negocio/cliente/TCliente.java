package negocio.cliente;

import java.util.Collection;
import java.util.Collections;

import negocio.factura.TFactura;
import presentacion.Convertable;

public class TCliente implements Convertable<TCliente> {
	protected String tipo;
	protected int idCliente;
	protected String DNI;
	protected String nombre;
	protected String apellido;
	protected boolean activo;
	protected String cuentaBancaria;
	
	public TCliente () {}
	
	public TCliente (int idCliente,String DNI, String nombre, String apellido, boolean activo, String cuentaBancaria) {
		this.idCliente = idCliente;
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellido = apellido;
		this.activo = activo;
		this.cuentaBancaria = cuentaBancaria;
	}
	
	//getters
	
	public String getTipo() {
	    return tipo;
	}
	
	public String getDNI() {
		return this.DNI;
	}

	public int getIdCliente() {
	    return idCliente;
	}

	public String getNombre() {
	    return nombre;
	}

	public String getApellido() {
	    return apellido;
	}

	public boolean getActivo() {
	    return activo;
	}

	public String getCuentaBancaria() {
	    return cuentaBancaria;
	}

	// Setters
	public void setTipo(String tipo) {
	    this.tipo = tipo;
	}

	public void setIdCliente(int idCliente) {
	    this.idCliente = idCliente;
	}

	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}
	
	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public void setApellido(String apellido) {
	    this.apellido = apellido;
	}

	public void setActivo(boolean activo) {
	    this.activo = activo;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
	    this.cuentaBancaria = cuentaBancaria;
	}

	@Override
	public Object getColumnValue(int columnIndex) {
		switch (columnIndex) {
		default: return this.nombre;
		}
	}

	@Override
	public void setColumnValue(int col, String value) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
