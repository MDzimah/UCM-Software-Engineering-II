package negocio.factura;

import java.util.Collection;

public interface SAFactura {
	public int create(TFactura tFac);
	public TFactura read(int id);
	public int update(TFactura tFac);
	public int delete (int id);
	public Collection<TFactura> readAll();
}
