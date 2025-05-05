package negocio.pase;

import java.util.ArrayList;
import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownObraException;
import exceptions.UnknownPaseException;


public interface SAPase {
	public int create(TPase tPase) throws UnknownObraException, UnknownCompTeaException, BBDDReadException, BBDDWriteException ;
	public TPase read(int id) throws BBDDReadException, UnknownPaseException;
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException, UnknownPaseException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException, UnknownPaseException;
	public ArrayList<TPase> readAll() throws BBDDReadException, UnknownPaseException;
	public int comprar(int idPase, int cantidad) throws BBDDReadException, BBDDWriteException, UnknownPaseException;  //Devuelve el stock comprado del pase con idPase. 
												   												//Si cantidad > stock, entonces da todo lo que tenga (lo dijo Vicky)
																								//Además, actualiza en la BD el pase con dicho id
	public void deletePorObra(int idObra) throws BBDDReadException, BBDDWriteException, UnknownObraException; //Devuelve si existe algun pase con el id de la obra pasada por parametro
	ArrayList<TPase> allPasesPorObra(int idObra) throws BBDDReadException, UnknownObraException;
}
