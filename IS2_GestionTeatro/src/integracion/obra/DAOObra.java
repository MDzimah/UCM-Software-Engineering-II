package integracion.obra;

import java.util.List;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.obra.TObra;

public interface DAOObra {
	public int create(TObra tObra)throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TObra read(int id)throws BBDDReadException;
	public int update(TObra tObra)throws BBDDReadException, BBDDWriteException;
	public List<TObra> getAll()throws BBDDReadException;
	public List<TObra> search(List<String> params)throws BBDDReadException;
}
