package negocio.obra;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.pase.TPase;

public interface SAObra {
	//De casos de uso
	public int create(TObra o) throws BBDDReadException, BBDDWriteException;
	public TObra read(int id) throws BBDDReadException;
	public int update(TObra o) throws BBDDReadException, BBDDWriteException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TObra> readActive() throws BBDDReadException;
	public Collection<TObra> search(Integer Id, String titulo, String autor, String genero, Boolean activo) throws BBDDReadException;
	
	//Para la relacion n a 1 con los Pases
	int enlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException;
	int desenlazarPase(int IdPase, int idObra) throws BBDDReadException, BBDDWriteException;
}
