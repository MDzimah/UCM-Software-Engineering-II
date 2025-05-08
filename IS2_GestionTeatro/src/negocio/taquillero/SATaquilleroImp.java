package negocio.taquillero;

import java.util.Collection;
import java.util.Iterator;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.taquillero.DAOTaquillero;

public class SATaquilleroImp implements SATaquillero {

	/**
	 * Crea un nuevo taquillero a partir del transfer
	 * @return devuelve el id si se crea correctamente, -1 en caso contrario
	 */
	@Override
	public int create(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		
		TTaquillero tTaqExistente = readByDNI(tTaquillero.getDNI()); //busca un taquillero por su DNI
		if(tTaqExistente == null) { //nuevo taquillero
			return daoTaquillero.create(tTaquillero);
		} else {
			return -1;
		}
	}

	/**
	 * Lee un taquillero a partir del idTaquillero
	 */
	@Override
	public TTaquillero read(int id) throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.read(id);
	}

	/**
	 * Actualiza los datos de un taquillero a partir de un transfer
	 */
	@Override
	public int update(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.update(tTaquillero);
	}

	/**
	 * Elimina un taquillero a partir de su idTaquillero (borrado lógico)
	 */
	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.delete(id);
	}

	/**
	 * Devuelve una colección de todos los taquilleros (activos)
	 */
	@Override
	public Collection<TTaquillero> readAll() throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readAll();
	}

	/**
	 * Lee un taquillero a partir del dni
	 */
	@Override
	public TTaquillero readByDNI(String dni) throws BBDDReadException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		return daoTaquillero.readByDNI(dni);
	}
	
	/**
	 * Aumenta la venta del taquillero en 1
	 */
	@Override
	public int aumentarVenta(int id) throws BBDDReadException, BBDDWriteException {
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		TTaquillero tTaq = daoTaquillero.read(id);
		int nuevoNumVentas = tTaq.getNumVentas() + 1;
		tTaq.setNumVentas(nuevoNumVentas);
		return daoTaquillero.update(tTaq);
	}

}
