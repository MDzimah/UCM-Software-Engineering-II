package negocio.cliente;

import java.util.Collection;

import negocio.factura.TFactura;
import presentacion.Convertable;

public class TClienteNormal extends TCliente implements Convertable<TCliente>{
	private int PuntosAcumulados;
	
	public TClienteNormal() {
		super();
	}
	
	public TClienteNormal (int idCliente,String DNI, String nombre, String apellido, boolean activo, String cuentaBancaria, int puntos) {
		super(idCliente,DNI,nombre,apellido,activo,cuentaBancaria);
		super.setTipo("Normal"); 
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

	@Override
	public Object getColumnValue(int columnIndex) {
		switch(columnIndex) {
		case 0: return this.getTipo();
		case 1: return this.getIdCliente();
		case 2: return this.getNombre();
		case 3: return this.getApellido();
		case 4: return this.getDNI();
		case 5: return this.getCuentaBancaria();
		default: return this.PuntosAcumulados;
		}
	}

	@Override
	public void setColumnValue(int col, String value) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
