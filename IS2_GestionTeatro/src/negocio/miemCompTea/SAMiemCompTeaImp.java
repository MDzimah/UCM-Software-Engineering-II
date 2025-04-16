package negocio.miemCompTea;

import java.util.Collection;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.miemCompTea.DAOMiemCompTea;

public class SAMiemCompTeaImp implements SAMiemCompTea {

	@Override
	public int create(TMiemCompTea tMieCT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TMiemCompTea read(int id) {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.read(id);
	}

	@Override
	public int update(TMiemCompTea tMieCT) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.delete(id);
	}

	@Override
	public Collection<TMiemCompTea> readAll() {
		DAOMiemCompTea daoMiem = FactoriaAbstractaIntegracion.getInstance().crearDAOMiemCompTea();
		return daoMiem.readAll();
	}

}
