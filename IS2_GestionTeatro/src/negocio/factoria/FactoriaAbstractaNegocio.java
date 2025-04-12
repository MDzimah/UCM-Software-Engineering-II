package negocio.factoria;

import negocio.cliente.SACliente;
import negocio.compTea.SACompTea;
import negocio.factura.SAFactura;
import negocio.miemCompTea.SAMiemCompTea;
import negocio.obra.SAObra;
import negocio.pase.SAPase;
import negocio.taquillero.SATaquillero;

public abstract class FactoriaAbstractaNegocio {
	private static FactoriaAbstractaNegocio instancia = null;
		
	public static FactoriaAbstractaNegocio getInstance() {
		if (instancia == null) instancia = new FactoriaNegocio();
		return instancia;
	}
	
	public abstract SAFactura crearSAFactura();
	public abstract SACliente crearSACliente();
	public abstract SAPase crearSAPase();
	public abstract SATaquillero crearSATaquillero();
	public abstract SAObra crearSAObra();
	public abstract SACompTea crearSACompTea();
	public abstract SAMiemCompTea crearSAMiemCompTea();
}
