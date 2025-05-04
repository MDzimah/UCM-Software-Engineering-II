package integracion.taquillero;

import java.util.Collection;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.taquillero.TTaquillero;

public class DAOTaquilleroImp implements DAOTaquillero {

	@Override
	public int create(TTaquillero tCliente) throws BBDDReadException, BBDDWriteException {
		JSONObject bdTaq = new JSONObject();
		
		if(OpsBBDD.isEmpty(Messages.BDTaq)) { //base de datos vacía
			bdTaq.put(Messages.KEY_lastId, 0); 
			bdTaq.put(Messages.KEY_taquilleros, new JSONObject()); //no hay taquilleros
		} else {
			bdTaq = OpsBBDD.read(Messages.BDTaq);
		}
		
		JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros); 
		int newId = bdTaq.getInt(Messages.KEY_lastId) + 1;
		bdTaq.put(Messages.KEY_lastId, newId);
		
		//creamos nuevo taquillero
		JSONObject nuevoTaq = new JSONObject();
		nuevoTaq.put(Messages.KEY_idTaq, newId);
		nuevoTaq.put(Messages.KEY_nombre, tCliente.getNombre());
		nuevoTaq.put(Messages.KEY_apellido, tCliente.getApellido());
		nuevoTaq.put(Messages.KEY_DNI, tCliente.getDNI());
		nuevoTaq.put(Messages.KEY_numVentas, tCliente.getNumVentas());
		nuevoTaq.put(Messages.KEY_sueldo, tCliente.getSueldo());
		nuevoTaq.put(Messages.KEY_edad, tCliente.getEdad());
		nuevoTaq.put(Messages.KEY_genero, tCliente.getGenero());
		nuevoTaq.put(Messages.KEY_act, tCliente.getActivo());
		
		taquilleros.put(Integer.toString(newId), nuevoTaq);
		OpsBBDD.write(bdTaq, Messages.BDTaq);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TTaquillero read(int id) throws BBDDReadException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int update(TTaquillero tCliente) throws BBDDReadException, BBDDWriteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TTaquillero> readAll() throws BBDDReadException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<TTaquillero> readActive() throws BBDDReadException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TTaquillero readByDNI() throws BBDDReadException {
		// TODO Auto-generated method stub
		return null;
	}

	
}