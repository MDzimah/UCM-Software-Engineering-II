package negocio.cliente;

import java.util.Collection;
import java.util.Collections;

import negocio.factura.TFactura;
import presentacion.Convertable;

public class TCliente implements Convertable<TCliente> {
	private String tipo;
	private int idCliente;
	private String DNI;
	private String nombre;
	private String apellido;
	private boolean activo;
	private String cuentaBancaria;
	
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
			case 0: return idCliente;
			case 1: return DNI;
			case 2: return nombre;
			case 3: return apellido;
			case 4: return activo;
			case 5: return cuentaBancaria;
			default: return null;
		}
	}


	@Override
	public void setColumnValue(int col, String value) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
