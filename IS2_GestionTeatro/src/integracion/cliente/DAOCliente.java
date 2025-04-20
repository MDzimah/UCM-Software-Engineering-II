package integracion.cliente;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.cliente.TCliente;

public interface DAOCliente {
	public int create(TCliente tCliente) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TCliente read(int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TCliente> readAll() throws BBDDReadException, BBDDWriteException;
	public int update(TCliente tCliente) throws BBDDReadException, BBDDWriteException;
	public TCliente readByDNI(String dni) throws BBDDReadException, BBDDWriteException;
}
