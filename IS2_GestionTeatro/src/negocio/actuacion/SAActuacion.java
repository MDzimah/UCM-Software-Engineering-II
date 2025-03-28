package negocio.actuacion;

import java.util.Collection;

public interface SAActuacion {
	public int create(TActuacion a);
	public TActuacion read(int id);
	public int update(TActuacion a);
	public int delete (int id);
	public Collection<TActuacion> readAll();
}
