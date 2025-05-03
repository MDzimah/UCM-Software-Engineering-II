package negocio.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;

import presentacion.Convertable;

public class TFactura implements Convertable<TFactura> {
	
	private int idFactura;
	
	private int idCliente;
	private int idTaquillero;
	
	private boolean activo;
	private LocalDateTime fecha;
	private float subtotal;
	private float importe;
	
	public TFactura (int idCl, int idTaq, boolean act, 
			LocalDateTime fecha, float importe, float subtotal) 
	{
		this.idCliente = idCl;
		this.idTaquillero = idTaq;
		this.activo = act;
		this.fecha = fecha;
		this.subtotal = subtotal;
		this.importe = importe;
	}
	
	//Para poder buscar factura
	public TFactura (int idFac) { this.idFactura = idFac; }
	
	
	/*--GETTERS--*/
	
	public int getIdFactura() {	return this.idFactura; }
	
	public int getIdCliente() {	return this.idCliente; }
	
	public int getIdTaquillero() { return this.idTaquillero; }
	
	public boolean getActivo() { return this.activo; }
	
	public LocalDateTime getFecha() { return this.fecha; }
	
	public float getSubtotal() { return this.subtotal; }
	
	public float getImporte() {	return this.importe; }
	
	
	/*--SETTERS--*/
	
	public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
	
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
	
	public void setIdTaquillero(int idTaquillero) { this.idTaquillero = idTaquillero; }
	
	public void setActivo(boolean bool) { this.activo = bool; }
	
	public void setFecha(LocalDateTime fecha) {	this.fecha = fecha; }
	
	public void setSubtotal(float nuevoSubtotal) { this.subtotal = nuevoSubtotal; }
	
	public void setImporte(float nuevoImporte) { this.importe = nuevoImporte; }


	@Override
	public ArrayList<ArrayList<Object>> matrizDeInformacion(ArrayList<TFactura> objects) {
		ArrayList<ArrayList<Object>> matInfo = new ArrayList<ArrayList<Object>>();
		for (TFactura tFac : objects) {
			ArrayList<Object> fila = new ArrayList<Object>();
			
			fila.add(tFac.getIdFactura());
			fila.add(tFac.getIdCliente());
			fila.add(getIdTaquillero());
			fila.add(tFac.getFecha());
			fila.add(tFac.getImporte());
			fila.add(tFac.getSubtotal());
			
			matInfo.add(fila);
		}
		return matInfo;
	}

	@Override
	public TFactura filaAObjetoT(ArrayList<Object> fila) {
		 return new TFactura(
				 (int)fila.get(0), 
				 (int)fila.get(1), 
				 (boolean)fila.get(2), 
				 (LocalDateTime)fila.get(3), 
				 (float)fila.get(4), 
				 (float)fila.get(5));
	}
}
