package negocio.taquillero;

import java.util.Collection;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.taquillero.DAOTaquillero;

public class SATaquilleroImp implements SATaquillero {

	@Override
	public int create(TTaquillero tTaquillero) {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.create(tTaquillero);
	}

	@Override
	public TTaquillero read(int id) {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.read(id);
	}

	@Override
	public int update(TTaquillero tTaquillero) {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.update(tTaquillero);
	}

	@Override
	public int delete(int id) {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.delete(id);
	}

	@Override
	public Collection<TTaquillero> readAll() {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readAll();
	}

	@Override
	public Collection<TTaquillero> readActive() {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readActive();
	}

}
