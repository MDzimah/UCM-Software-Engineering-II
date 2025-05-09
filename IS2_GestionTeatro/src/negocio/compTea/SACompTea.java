package negocio.compTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.miemCompTea.TCompT_MiemCompT;

public interface SACompTea {
	public int create(TCompTea ct) throws BBDDReadException, BBDDWriteException;
	public TCompTea read(int id)throws  BBDDReadException;
	public int update(TCompTea ct) throws BBDDWriteException, BBDDReadException;
	public int delete (int id) throws BBDDReadException, BBDDWriteException;
	public Collection<TCompTea> readAll() throws BBDDReadException;
	public int addMember(TCompT_MiemCompT cmt) throws BBDDReadException, BBDDWriteException;
	public int removeMember(TCompT_MiemCompT cmt) throws BBDDReadException, BBDDWriteException;
}
