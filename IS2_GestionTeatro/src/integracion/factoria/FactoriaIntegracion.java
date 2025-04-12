package integracion.factoria;

import integracion.cliente.*;
import integracion.compTea.*;
import integracion.factura.*;
import integracion.miemCompTea.*;
import integracion.obra.*;
import integracion.pase.*;
import integracion.taquillero.*;

public class FactoriaIntegracion extends FactoriaAbstractaIntegracion {

	@Override
	public DAOFactura crearDAOFactura() { return new DAOFacturaImp(); }

	@Override
	public DAOCliente crearDAOCliente() { return new DAOClienteImp(); }

	@Override
	public DAOPase crearDAOPase() { return new DAOPaseImp(); }

	@Override
	public DAOTaquillero crearDAOTaquillero() {	return new DAOTaquilleroImp(); }

	@Override
	public DAOObra crearDAOObra() { return new DAOObraImp(); }

	@Override
	public DAOCompTea crearDAOCompTea() { return new DAOCompTeaImp(); }

	@Override
	public DAOMiemCompTea crearDAOMiemCompTea() { return new DAOMiemCompTeaImp(); }

}
