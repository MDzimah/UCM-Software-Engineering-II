package integracion.taquillero;

import java.util.Collection;

import negocio.taquillero.TTaquillero;

public interface DAOTaquillero {
	public int create(TTaquillero tCliente);
	public int delete(int id);
	public TTaquillero read(int id);
	public Collection<TTaquillero> readAll();
	public int update(TTaquillero tCliente);
	
	public Collection<TTaquillero> readActive();
	public TTaquillero readByDNI();
}
