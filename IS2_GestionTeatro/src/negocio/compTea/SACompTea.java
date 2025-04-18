package negocio.compTea;

import java.util.Collection;

import exceptions.UnknownCompTeaException;
import exceptions.UnknownMiemCompTeaException;

public interface SACompTea {
	public int create(TCompTea ct)throws UnknownCompTeaException,UnknownMiemCompTeaException ;
	public TCompTea read(int id)throws UnknownCompTeaException;
	public int update(TCompTea ct);
	public int delete (int id);
	public Collection<TCompTea> readAll();
}
