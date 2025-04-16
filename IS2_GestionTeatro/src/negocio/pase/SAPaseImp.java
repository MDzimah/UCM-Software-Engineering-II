package negocio.pase;

import java.time.LocalDateTime;
import java.util.Collection;

import exceptions.UnknownClienteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownObraException;
import exceptions.UnknownTaquilleroException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.obra.DAOObra;
import integracion.compTea.*;
import integracion.pase.DAOPase;
import negocio.compTea.TCompTea;
import negocio.obra.TObra;

public class SAPaseImp implements SAPase {

	@Override
	public int create(TPase tPase) {
		int id = -1;
		
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra lecturaObra = daoObra.read(tPase.getIdObra());
		DAOCompTea daoCompTea = FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		TCompTea lecturaCompTea = daoCompTea.read(tPase.getIdCompanyaTeatral());
		
		if (lecturaObra == null) {
			throw new UnknownObraException();
		}
		if (lecturaCompTea == null) {
			throw new UnknownCompTeaException();
		}
		
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		id = daoPas.create(tPase);
		
		return id;
	}

	@Override
	public TPase read(int id) {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		return daoPas.read(id);
	}

	@Override
	public int update(TPase tPase) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		return daoPas.delete(id);
	}

	@Override
	public Collection<TPase> readAll() {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		return daoPas.readAll();
	}
}
