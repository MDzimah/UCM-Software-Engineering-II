package negocio.taquillero;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;

public interface SATaquillero {
	public int create(TTaquillero emp) throws BBDDReadException, BBDDWriteException;
	public TTaquillero read(int id) throws BBDDReadException;
	public int update(TTaquillero emp) throws BBDDReadException, BBDDWriteException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TTaquillero> readAll() throws BBDDReadException;
	public TTaquillero readByDNI(String dni) throws BBDDReadException;
	public int aumentarVenta(int id) throws BBDDReadException, BBDDWriteException;
}
