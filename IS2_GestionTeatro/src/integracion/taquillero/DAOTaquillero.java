package integracion.taquillero;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.taquillero.TTaquillero;

public interface DAOTaquillero {
	public int create(TTaquillero tCliente) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TTaquillero read(int id) throws BBDDReadException;
	public int update(TTaquillero tCliente) throws BBDDReadException, BBDDWriteException;
	public Collection<TTaquillero> readAll() throws BBDDReadException;
	public Collection<TTaquillero> readActive() throws BBDDReadException;
	public TTaquillero readByDNI() throws BBDDReadException;;
}
