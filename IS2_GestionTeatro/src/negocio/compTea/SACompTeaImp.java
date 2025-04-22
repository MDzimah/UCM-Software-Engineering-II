package negocio.compTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownMiemCompTeaException;
import integracion.compTea.DAOCompTea;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.miemCompTea.DAOMiemCompTea;
import negocio.miemCompTea.TMiemCompTea;
import negocio.compTea.TCompTea;

public class SACompTeaImp implements SACompTea {

	@Override
	public int create(TCompTea ct) throws BBDDReadException, BBDDWriteException, UnknownCompTeaException  {
		if(ct==null)throw new UnknownCompTeaException();
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		Collection<TMiemCompTea> listaMiembros=ct.getMiembros();
		return daoCT.create(ct);
	}

	@Override
	public TCompTea read(int id) throws BBDDReadException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.read(id);
	}

	@Override
	public int update(TCompTea ct) {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.update(ct);
	}

	@Override
	public int delete(int id) {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.delete(id);
	}

	@Override
	public Collection<TCompTea> readAll() {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.readAll();
	}

}
