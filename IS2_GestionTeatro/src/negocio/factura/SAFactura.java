package negocio.facturas;

import java.util.Collection;

public interface SAFacturas {
	public int create(TFacturas tFac);
	public TFacturas read(int id);
	public int update(TFacturas tFac);
	public int delete (int id);
	public Collection<TFacturas> readAll();
}
