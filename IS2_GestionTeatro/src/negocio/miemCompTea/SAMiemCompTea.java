package negocio.miemCompTea;

import java.util.Collection;

public interface SAMiemCompTea {
	public int create(TMiemCompTea tMieCT);
	public TMiemCompTea read(int id);
	public int update(TMiemCompTea tMieCT);
	public int delete (int id);
	public Collection<TMiemCompTea> readAll();
}
