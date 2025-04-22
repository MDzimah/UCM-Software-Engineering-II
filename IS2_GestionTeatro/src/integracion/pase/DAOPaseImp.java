package integracion.pase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;

import misc.Messages;
import misc.OpsBBDD;
import negocio.pase.TPase;

public class DAOPaseImp implements DAOPase {

	@Override
	public int create(TPase tPase) throws BBDDReadException, BBDDWriteException {
		JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
		JSONArray pases = new JSONArray(bdPase.get(Messages.KEY_pases));
		int newID = bdPase.getInt(Messages.KEY_lastId) + 1;
		bdPase.put(Messages.KEY_lastId, newID);
		JSONObject nuevoPase = new JSONObject();
		nuevoPase.put(Messages.KEY_idPase, newID);
		nuevoPase.put(Messages.KEY_idCompTea, tPase.getIdCompanyaTeatral());
		nuevoPase.put(Messages.KEY_idObra, tPase.getIdObra());
		nuevoPase.put(Messages.KEY_act, tPase.isActivo());
		nuevoPase.put(Messages.KEY_fecha, tPase.getFecha().toString());
		nuevoPase.put(Messages.KEY_stock, tPase.getStock());
		nuevoPase.put(Messages.KEY_precioPase, tPase.getPrecio());
		pases.put(nuevoPase);
		bdPase.put(Messages.KEY_pases, pases);
		OpsBBDD.write(bdPase, Messages.BDPase);
		return newID;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
		JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
		if (pases.has(Integer.toString(id))) {
			JSONObject pase = pases.getJSONObject(Integer.toString(id));
	        pase.put(Messages.KEY_act, false);
	        OpsBBDD.write(bdPase, Messages.BDPase);
	        return id;
		}
		return -1; //no se ha borrado pase porque no se ha encontrado
	}

	@Override
	public TPase read(int id) throws BBDDReadException {
		JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
		JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
		TPase tPase = null;
		if (pases.has(Integer.toString(id))) {
			JSONObject pase = pases.getJSONObject(Integer.toString(id));
			tPase = read(pase);
			tPase.setIdPase(id);
		}
		return tPase;
	}

	@Override
	public Collection<TPase> readAll() throws BBDDReadException {
		JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
		JSONObject pases = new JSONObject(bdPase.getJSONArray(Messages.KEY_pases));
		Collection<TPase> pasesADevolver = new ArrayList<>();
		Set<String> idSetPases = pases.keySet();
		for (String idPase : idSetPases) {
			JSONObject pase = pases.getJSONObject(idPase);
			if (pase.getBoolean(Messages.KEY_act)) {
				TPase tPase = read(pase);
				tPase.setIdPase(Integer.valueOf(idPase));
				pasesADevolver.add(tPase);
			}
		}
		return pasesADevolver;
	}

	@Override
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException {
		JSONObject bdPase = OpsBBDD.read("BDPase.json");
		if(bdPase.has(String.valueOf(tPase.getIdObra())) && bdPase.getBoolean(null)) {
			
			JSONObject nuevoPase = new JSONObject();
			nuevoPase.put(Messages.KEY_idPase, tPase.getIdPase());
			nuevoPase.put(Messages.KEY_idCompTea, tPase.getIdCompanyaTeatral());
			nuevoPase.put(Messages.KEY_idObra, tPase.getIdObra());
			nuevoPase.put(Messages.KEY_act, tPase.isActivo());
			nuevoPase.put(Messages.KEY_fecha, tPase.getFecha().toString());
			nuevoPase.put(Messages.KEY_stock, tPase.getStock());
			nuevoPase.put(Messages.KEY_precioPase, tPase.getPrecio());
			
			bdPase.put(String.valueOf(tPase.getIdObra()), nuevoPase);
			return 1;
		}
		return -1;
	}
	
	private TPase read(JSONObject jsonPas) {
		return new TPase(
				jsonPas.getInt(Messages.KEY_idPase), 
				jsonPas.getInt(Messages.KEY_idCompTea), 
				jsonPas.getInt(Messages.KEY_idObra),
				jsonPas.getBoolean(Messages.KEY_act),
				LocalDateTime.parse(jsonPas.getString(Messages.KEY_fecha)),
				jsonPas.getInt(Messages.KEY_stock),
				jsonPas.getFloat(Messages.KEY_precioPase));
		}
}
