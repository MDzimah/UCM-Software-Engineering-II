package integracion.pase;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.pase.TPase;

public interface DAOPase {
	public int create(TPase tPase) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TPase read(int id) throws BBDDReadException;
	public Collection<TPase> readAll() throws BBDDReadException;
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException;
	public boolean readPorObra(int idObra) throws BBDDReadException;
}
