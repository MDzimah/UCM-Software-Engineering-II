package negocio.miemCompTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.miemCompTea.DAOMiemCompTea;

public class SAMiemCompTeaImp implements SAMiemCompTea {

	@Override
	public int create(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.create(tMieCT);
	}

	@Override
	public TMiemCompTea read(int id) throws BBDDReadException{
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.read(id);
	}

	@Override
	public int update(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.update(tMieCT);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.delete(id);
	}

	@Override
	public Collection<TMiemCompTea> readAll() throws BBDDReadException{
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.readAll();
	}
	
	@Override
	public TMiemCompTea readByDNI(String dni) throws BBDDReadException{
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.readByDNI(dni);
	}

}
