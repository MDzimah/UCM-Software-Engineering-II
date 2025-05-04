package integracion.taquillero;

import java.util.Collection;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.taquillero.TTaquillero;
import negocio.taquillero.TTaquillero.Genero;

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
		nuevoTaq.put(Messages.KEY_genero, tCliente.getGenero().toString());
		nuevoTaq.put(Messages.KEY_act, tCliente.getActivo());
		
		taquilleros.put(Integer.toString(newId), nuevoTaq);
		OpsBBDD.write(bdTaq, Messages.BDTaq);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if(!OpsBBDD.isEmpty(Messages.BDTaq)) {
			JSONObject bdTaq = OpsBBDD.read(Messages.BDTaq);
			JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros);
			
			String _id = Integer.toString(id);
			if(taquilleros.has(_id) && taquilleros.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				taquilleros.getJSONObject(_id).put(Messages.KEY_act, false); //borrado lógico
				OpsBBDD.write(bdTaq, Messages.BDTaq);
				return id; 
			}
		}
		return -1; //no se ha encontrado o no existe
	}

	@Override
	public TTaquillero read(int id) throws BBDDReadException {
		if(!OpsBBDD.isEmpty(Messages.BDTaq)) {
			JSONObject bdTaq = OpsBBDD.read(Messages.BDTaq);
			JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros);
			
			TTaquillero tTaq = null;
			String _id = Integer.toString(id);
			if(taquilleros.has(_id) && taquilleros.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				JSONObject taq = taquilleros.getJSONObject(_id);
				tTaq = readTTaquillero(taq);
				tTaq.setIdTaquillero(id);
			}
			return tTaq; 
		}
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

	private TTaquillero readTTaquillero(JSONObject taq) {
		return new TTaquillero(
				taq.getInt(Messages.KEY_idTaq),
				taq.getBoolean(Messages.KEY_act),
				taq.getString(Messages.KEY_DNI),
				taq.getString(Messages.KEY_nombre),
				taq.getString(Messages.KEY_apellido),
				taq.getInt(Messages.KEY_numVentas),
				taq.getFloat(Messages.KEY_sueldo),
				taq.getInt(Messages.KEY_edad),
				Genero.valueOf(taq.getString(Messages.KEY_genero))
				);
	}

}









