package negocio.obra;

import java.util.Collection;
import java.util.List;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownObraException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.obra.DAOObra;

public class SAObraImp implements SAObra {

	@Override
	public int create(TObra o) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.create(o);
	}

	@Override
	public TObra read(int id) throws BBDDReadException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(id);
		if(obra==null)
			throw new UnknownObraException();
		else
			return obra;
	}

	@Override
	public int update(TObra o) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		int salida = daoObra.update(o);
		if(salida<=0)
			throw new UnknownObraException();
		else
			return salida;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		int salida = daoObra.delete(id);
		if(salida<=0)
			throw new UnknownObraException();
		else
			return salida;			
	}

	@Override
	public Collection<TObra> readActive() throws BBDDReadException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		Collection<TObra> obras = daoObra.readActive();
		if(obras.isEmpty() || obras==null)
			throw new UnknownObraException();
		else
			return obras;
	}

	public Collection<TObra> search(List<String> params) throws BBDDReadException, UnknownObraException{
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		Collection<TObra> obras = daoObra.search(params);
		if(obras.isEmpty() || obras==null)
			throw new UnknownObraException();
		else
			return obras;
	}

	@Override
	public int enlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(idObra);
		if(obra==null)
			throw new UnknownObraException();
		obra.agregarPase(IdPase);
		return daoObra.update(obra);
	}

	@Override
	public int desenlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(idObra);
		if(obra==null)
			throw new UnknownObraException();
		obra.eliminarPase(IdPase);
		return daoObra.update(obra);
	}

}
