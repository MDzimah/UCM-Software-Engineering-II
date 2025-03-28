package integracion.mieCompTeatral;

import java.util.Collection;

import negocio.mieCompTeatral.TMieCompTeatral;

public interface DAOMieCompTeatral {
	public int create(TMieCompTeatral tMieCT);
	public int delete(int id);
	public TMieCompTeatral read(int id);
	public Collection<TMieCompTeatral> readAll();
	public int update(TMieCompTeatral tMieCT);
}
