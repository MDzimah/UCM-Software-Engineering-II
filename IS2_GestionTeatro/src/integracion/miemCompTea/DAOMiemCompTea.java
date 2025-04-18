package integracion.miemCompTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.miemCompTea.TMiemCompTea;

public interface DAOMiemCompTea {
	public int create(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException;
	public int delete(int id) throws BBDDReadException, BBDDWriteException;
	public TMiemCompTea read(int id) throws BBDDReadException;
	public Collection<TMiemCompTea> readAll() throws BBDDReadException;
	public int update(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException;
	public TMiemCompTea readByDNI(String dni) throws BBDDReadException;
}
