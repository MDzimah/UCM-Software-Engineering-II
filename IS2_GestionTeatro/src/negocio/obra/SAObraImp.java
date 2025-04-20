package negocio.obra;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.obra.DAOObra;

public class SAObraImp implements SAObra {

	@Override
	public int create(TObra o) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.create(o);
	}

	@Override
	public TObra read(int id) throws BBDDReadException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.read(id);
	}

	@Override
	public int update(TObra o) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.update(o);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.delete(id);
	}

	@Override
	public Collection<TObra> readActive() throws BBDDReadException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.readActive();
	}

	public Collection<TObra> search(Integer Id, String titulo, String autor, String genero, Boolean activo) throws BBDDReadException{
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.search(Id, titulo, autor, genero, activo);
	}

	@Override
	public int enlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(idObra);
		obra.agregarPase(IdPase);
		return daoObra.update(obra);
	}

	@Override
	public int desenlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(idObra);
		obra.eliminarPase(IdPase);
		return daoObra.update(obra);
	}

}
