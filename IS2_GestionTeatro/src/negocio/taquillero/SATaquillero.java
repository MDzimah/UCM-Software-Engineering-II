package negocio.empleado;

import java.util.Collection;

public interface SAEmpleado {
	public int create(TEmpleado emp);
	public TEmpleado read(int id);
	public int update(TEmpleado emp);
	public int delete (int id);
	public Collection<TEmpleado> readAll();
}
