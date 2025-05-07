package negocio.pase;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownObraException;
import exceptions.UnknownPaseException;


public interface SAPase {
	public int create(TPase tPase) throws UnknownObraException, UnknownCompTeaException, BBDDReadException, BBDDWriteException ;
	public TPase read(int id) throws BBDDReadException;
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException, UnknownPaseException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public ArrayList<TPase> readAll() throws BBDDReadException, UnknownPaseException;
	public int comprar(int idPaseValido, int cantidad) throws BBDDReadException, BBDDWriteException;  //Devuelve el stock comprado del pase con idPase. 
												   													  //Si cantidad > stock, entonces da todo lo que tenga (lo dijo Vicky)
																									  //Además, actualiza en la BD el pase con dicho id
	public int deletePorObra(int idObra) throws BBDDReadException, BBDDWriteException; //Se borran los pases asociados a la obra pasada como parametro.	
																											 // Devuelve el id de la obra pero si no se ha podido operar correctamente devuelve -1
	public int deletePorCompTea(int idCompTea) throws BBDDReadException, BBDDWriteException; //Se borran los pases asociados a la companya teatral pasada como parametro.	
	 																						 // Devuelve el id de la companya teatral pero si no se ha podido operar correctamente devuelve -1
	ArrayList<TPase> allPasesPorObra(int idObra) throws BBDDReadException, UnknownObraException;
}
