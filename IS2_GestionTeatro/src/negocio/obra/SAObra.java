package negocio.obra;

import java.util.Collection;
import java.util.List;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownObraException;
import negocio.pase.TPase;

public interface SAObra {
	//De casos de uso
	public int create(TObra o) throws BBDDReadException, BBDDWriteException;
	public TObra read(int id) throws BBDDReadException, UnknownObraException;
	public int update(TObra o) throws BBDDReadException, BBDDWriteException, UnknownObraException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException, UnknownObraException;
	public Collection<TObra> readActive() throws BBDDReadException, UnknownObraException;
	/**
	 * 
	 * @param params - params - Integer Id, String titulo, String autor, String genero, Boolean activo
	 * @return
	 * @throws BBDDReadException
	 * @throws UnknownObraException
	 */
	public Collection<TObra> search(List<String> params) throws BBDDReadException, UnknownObraException;

}
