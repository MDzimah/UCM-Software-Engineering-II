package negocio.compTea;

import java.util.Collection;

public interface SACompTea {
	public int create(TCompTea ct);
	public TCompTea read(int id);
	public int update(TCompTea ct);
	public int delete (int id);
	public Collection<TCompTea> readAll();
}
