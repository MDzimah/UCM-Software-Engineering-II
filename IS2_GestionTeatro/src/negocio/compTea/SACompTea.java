package negocio.compTeatral;

import java.util.Collection;

public interface SACompTeatral {
	public int create(TCompTeatral ct);
	public TCompTeatral read(int id);
	public int update(TCompTeatral ct);
	public int delete (int id);
	public Collection<TCompTeatral> readAll();
}
