package negocio.pase;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownObraException;


public interface SAPase {
	public int create(TPase tPase) throws UnknownObraException, UnknownCompTeaException, BBDDReadException, BBDDWriteException;
	public TPase read(int id) throws BBDDReadException;
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TPase> readAll() throws BBDDReadException;
	public int comprar(int idPase, int cantidad) throws BBDDReadException, BBDDWriteException;  //Devuelve el stock comprado del pase con idPase. 
												   //Si cantidad > stock, entonces da todo lo que tenga (lo dijo Vicky)
												   //Además, actualiza en la BD el pase con dicho id
}
