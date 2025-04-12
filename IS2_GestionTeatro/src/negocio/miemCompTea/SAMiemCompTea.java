package negocio.mieCompTeatral;

import java.util.Collection;

public interface SAMieCompTeatral {
	public int create(TMieCompTeatral tMieCT);
	public TMieCompTeatral read(int id);
	public int update(TMieCompTeatral tMieCT);
	public int delete (int id);
	public Collection<TMieCompTeatral> readAll();
}
