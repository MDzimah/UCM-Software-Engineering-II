package negocio.cliente;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownClienteException;

public interface SACliente {
	public int create(TCliente cl) throws BBDDReadException, BBDDWriteException;
	public TCliente read(int id) throws BBDDReadException, BBDDWriteException;
	public int update(TCliente cl) throws BBDDReadException, BBDDWriteException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TCliente> readAll() throws BBDDReadException, BBDDWriteException;
	public float aplicarDescuento(int idCliente, float importe) throws UnknownClienteException;
}
