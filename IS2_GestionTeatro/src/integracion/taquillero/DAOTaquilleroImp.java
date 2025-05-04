package integracion.taquillero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Genero;
import misc.Messages;
import misc.OpsBBDD;
import negocio.taquillero.TTaquillero;

public class DAOTaquilleroImp implements DAOTaquillero {

	@Override
	public int create(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
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
		nuevoTaq.put(Messages.KEY_nombre, tTaquillero.getNombre());
		nuevoTaq.put(Messages.KEY_apellido, tTaquillero.getApellido());
		nuevoTaq.put(Messages.KEY_DNI, tTaquillero.getDNI());
		nuevoTaq.put(Messages.KEY_numVentas, tTaquillero.getNumVentas());
		nuevoTaq.put(Messages.KEY_sueldo, tTaquillero.getSueldo());
		nuevoTaq.put(Messages.KEY_edad, tTaquillero.getEdad());
		nuevoTaq.put(Messages.KEY_genero, tTaquillero.getGenero().toString());
		nuevoTaq.put(Messages.KEY_act, tTaquillero.getActivo());
		
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
				tTaq = createTTaq(taq);
				tTaq.setIdTaquillero(id);
			}
			return tTaq; 
		}
		return null;
	}
	
	
	@Override
	public int update(TTaquillero tTaquillero) throws BBDDReadException, BBDDWriteException {
		if(!OpsBBDD.isEmpty(Messages.BDTaq)) {
			JSONObject bdTaq = OpsBBDD.read(Messages.BDTaq);
			JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros);
			
			String _id = Integer.toString(tTaquillero.getIdTaquillero());
			if(taquilleros.has(_id) && taquilleros.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				JSONObject taq = createJSONTaq(tTaquillero);
				taquilleros.put(_id, taq);
				OpsBBDD.write(bdTaq, Messages.BDTaq);
				return Integer.parseInt(_id);
			}
		}
		return -1;
	}

	@Override
	public Collection<TTaquillero> readAll() throws BBDDReadException {
		if(!OpsBBDD.isEmpty(Messages.BDTaq)) {
			JSONObject bdTaq = OpsBBDD.read(Messages.BDTaq);
			JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros);
			
			Collection<TTaquillero> listaTaqs = new ArrayList<TTaquillero>();
			
			Set<String> allIds = taquilleros.keySet();
			for(String idTaq : allIds) {
				JSONObject taq = taquilleros.getJSONObject(idTaq);
				
				if(taq.getBoolean(Messages.KEY_act)) {
					TTaquillero tTaq = createTTaq(taq);
					tTaq.setIdTaquillero(Integer.valueOf(idTaq));
					listaTaqs.add(tTaq);
				}
			}
			
			return listaTaqs;
		}
		return null;
	}
	
	@Override
	public int aumentarVenta(int id) throws BBDDReadException, BBDDWriteException {
		if(!OpsBBDD.isEmpty(Messages.BDTaq)) {
			JSONObject bdTaq = OpsBBDD.read(Messages.BDTaq);
			JSONObject taquilleros = bdTaq.getJSONObject(Messages.KEY_taquilleros);
			
			String _id = Integer.toString(id);
			if(taquilleros.has(_id) && taquilleros.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				JSONObject taq = taquilleros.getJSONObject(_id);
				int numVentas = taq.getInt(Messages.KEY_numVentas);
				taq.put(Messages.KEY_numVentas, numVentas + 1);
				taquilleros.put(_id, taq);
				OpsBBDD.write(bdTaq, Messages.BDTaq);
				
				return id;
			}
		}
		return -1; //no se ha encontrado o no existe
	}

	//Método adicional
	@Override
	public TTaquillero readByDNI() throws BBDDReadException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Crea un JSONObject a partir del Transfer Taquillero
	 */
	private JSONObject createJSONTaq(TTaquillero tTaquillero) {
		JSONObject nuevoTaq = new JSONObject();
		nuevoTaq.put(Messages.KEY_idTaq, tTaquillero.getIdTaquillero());
		nuevoTaq.put(Messages.KEY_nombre, tTaquillero.getNombre());
		nuevoTaq.put(Messages.KEY_apellido, tTaquillero.getApellido());
		nuevoTaq.put(Messages.KEY_DNI, tTaquillero.getDNI());
		nuevoTaq.put(Messages.KEY_numVentas, tTaquillero.getNumVentas());
		nuevoTaq.put(Messages.KEY_sueldo, tTaquillero.getSueldo());
		nuevoTaq.put(Messages.KEY_edad, tTaquillero.getEdad());
		nuevoTaq.put(Messages.KEY_genero, tTaquillero.getGenero().toString());
		nuevoTaq.put(Messages.KEY_act, tTaquillero.getActivo());
		return nuevoTaq;
	}
	
	/*
	 * Crea un Transfer Taquillero a partir del JSONObject
	 */
	private TTaquillero createTTaq(JSONObject taq) {
		return new TTaquillero(
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







