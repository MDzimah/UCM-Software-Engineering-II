package negocio.cliente;

import java.util.Collection;

import negocio.factura.TFactura;

public class TClienteVIP extends TCliente {
	private float costeMensualSuscripcion;
	private VIPEnum nivelVIP;
	
	public TClienteVIP() {
		super();
	}
	
	public TClienteVIP (int idCliente, String DNI,String nombre, String apellido, boolean activo, String cuentaBancaria, VIPEnum nivelVIP, float coste) {
		super(idCliente,DNI,nombre,apellido,activo,cuentaBancaria);
		tipo = "VIP";
		this.nivelVIP = nivelVIP;
		this.costeMensualSuscripcion = coste;
	}
	
	//getters especificos
	public float getCosteMensual() {
		return costeMensualSuscripcion;
		
	}
	
	public VIPEnum getNivelVIP() {
		return nivelVIP;
	}
	
	//setters especificos
	public void setCosteMensual(float nCoste) {
		this.costeMensualSuscripcion = nCoste;
		
	}
	
	public void setNivelVIP(VIPEnum nivelVIP) {
		this.nivelVIP = nivelVIP;
	}

}
