package negocio.cliente;

import java.util.Collection;

import negocio.factura.TFactura;
import presentacion.Convertable;

public class TClienteVIP extends TCliente implements Convertable<TCliente>{
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

	@Override
	public Object getColumnValue(int columnIndex) {
		switch(columnIndex) {
		case 0: return this.tipo;
		case 1: return this.idCliente;
		case 2: return this.nombre;
		case 3: return this.apellido;
		case 4: return this.DNI;
		case 5: return this.cuentaBancaria;
		case 6: return this.nivelVIP;
		default: return this.costeMensualSuscripcion;
		}
	}

	@Override
	public void setColumnValue(int col, String value) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
