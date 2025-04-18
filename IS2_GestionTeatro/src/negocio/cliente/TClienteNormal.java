package negocio.cliente;

import java.util.Collection;

import negocio.factura.TFactura;

public class TClienteNormal extends TCliente {
	private int PuntosAcumulados;
	
	public TClienteNormal() {
		super();
	}
	
	public TClienteNormal (int idCliente,String DNI, String nombre, String apellido, boolean activo, String cuentaBancaria, Collection<TFactura> facturas, int puntos) {
		super(idCliente,DNI,nombre,apellido,activo,cuentaBancaria,facturas);
		tipo = "Normal";
		this.PuntosAcumulados = puntos;
	}
	
	//getters especificos
	public int getPuntosAcumulados() {
		return this.PuntosAcumulados;
	}
	
	//setters especificos
	public void setPuntosAcumulados(int PuntosAcumulados) {
		this.PuntosAcumulados = PuntosAcumulados;
	}

}
