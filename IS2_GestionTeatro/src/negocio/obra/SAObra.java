package negocio.obra;

import java.util.Collection;

public interface SAObra {
	public int create(TObra o);
	public TObra read(int id);
	public int update(TObra o);
	public int delete (int id);
	public Collection<TObra> readAll();
}
