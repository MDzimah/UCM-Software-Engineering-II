package integracion.compTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.compTea.TCompTea;

public interface DAOCompTea {
	public int create(TCompTea tCompTea) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TCompTea read(int id) throws BBDDReadException;
	public Collection<TCompTea> readAll() throws BBDDReadException;
	public int update(TCompTea tCompTea) throws BBDDWriteException, BBDDReadException;
}
