package negocio.factoria;

import negocio.cliente.*;
import negocio.compTea.*;
import negocio.factura.*;
import negocio.miemCompTea.*;
import negocio.obra.*;
import negocio.pase.*;
import negocio.taquillero.*;

public class FactoriaNegocio extends FactoriaAbstractaNegocio {

	@Override
	public SAFactura crearSAFactura() { return new SAFacturaImp(); }

	@Override
	public SACliente crearSACliente() { return new SAClienteImp(); }

	@Override
	public SAPase crearSAPase() { return new SAPaseImp(); }

	@Override
	public SATaquillero crearSATaquillero() { return new SATaquilleroImp(); }

	@Override
	public SAObra crearSAObra() { return new SAObraImp(); }

	@Override
	public SACompTea crearSACompTea() { return new SACompTeaImp(); }

	@Override
	public SAMiemCompTea crearSAMiemCompTea() { return new SAMiemCompTeaImp(); }

}
