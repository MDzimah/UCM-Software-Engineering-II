package integracion.compTeatral;

import java.util.Collection;

import negocio.compTeatral.TCompTeatral;

public interface DAOCompTeatral {
	public int create(TCompTeatral tCompTeatral);
	public int delete(int id);
	public TCompTeatral read(int id);
	public Collection<TCompTeatral> readAll();
	public int update(TCompTeatral tCompTeatral);
}
