package negocio.taquillero;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.taquillero.DAOTaquillero;

public class SATaquilleroImp implements SATaquillero {

	@Override
	public int create(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.create(tTaquillero);
	}

	@Override
	public TTaquillero read(int id) throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.read(id);
	}

	@Override
	public int update(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.update(tTaquillero);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.delete(id);
	}

	@Override
	public Collection<TTaquillero> readAll() throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readAll();
	}

	@Override
	public Collection<TTaquillero> readActive() throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readActive();
	}

	@Override
	public TTaquillero readByDNI() throws BBDDReadException {
		
		return null;
	}

	@Override
	public int aumentarVenta(int id) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.aumentarVenta(id);
	}

}
