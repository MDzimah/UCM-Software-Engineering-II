package integracion.factoria;

import integracion.cliente.DAOCliente;
import integracion.compTea.DAOCompTea;
import integracion.factura.DAOFactura;
import integracion.miemCompTea.DAOMiemCompTea;
import integracion.obra.DAOObra;
import integracion.pase.DAOPase;
import integracion.taquillero.DAOTaquillero;

public abstract class FactoriaAbstractaIntegracion {
	private static FactoriaAbstractaIntegracion instancia = null;
		
	public static FactoriaAbstractaIntegracion getInstance() {
		if (instancia == null) instancia = new FactoriaIntegracion();
		return instancia;
	}
	
	public abstract DAOFactura crearDAOFactura();
	public abstract DAOCliente crearDAOCliente();
	public abstract DAOPase crearDAOPase();
	public abstract DAOTaquillero crearDAOTaquillero();
	public abstract DAOObra crearDAOObra();
	public abstract DAOCompTea crearDAOCompTea();
	public abstract DAOMiemCompTea crearDAOMiemCompTea();
}
