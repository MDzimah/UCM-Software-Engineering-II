package integracion.taquillero;

import java.util.Collection;

import negocio.taquillero.TTaquillero;

public class DAOTaquilleroImp implements DAOTaquillero {

	@Override
	public int create(TTaquillero tCliente) {
		int id = -1;
		
		
		return id;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TTaquillero read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TTaquillero> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TTaquillero tCliente) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TTaquillero> readActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TTaquillero readByDNI() {
		// TODO Auto-generated method stub
		return null;
	}

	
}