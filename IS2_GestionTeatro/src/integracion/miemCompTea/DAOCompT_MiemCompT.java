package integracion.miemCompTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.miemCompTea.TCompT_MiemCompT;

public interface DAOCompT_MiemCompT {
	public int create(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TCompT_MiemCompT read(int id) throws BBDDReadException;
	public Collection<TCompT_MiemCompT> readAll() throws BBDDReadException;
	public int update(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
}
