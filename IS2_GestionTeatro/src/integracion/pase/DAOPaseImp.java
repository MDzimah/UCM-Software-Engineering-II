package integracion.pase;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
		JSONObject bdPase = new JSONObject();
		if (OpsBBDD.isEmpty(Messages.BDPase)) {
			bdPase.put(Messages.KEY_lastId, -1);
			bdPase.put(Messages.KEY_pases, new JSONObject());
		}
		else bdPase = OpsBBDD.read(Messages.BDPase);
		JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
		int newID = bdPase.getInt(Messages.KEY_lastId) + 1;
		bdPase.put(Messages.KEY_lastId, newID);
		JSONObject nuevoPase = new JSONObject();
		nuevoPase.put(Messages.KEY_idPase, newID);
		nuevoPase.put(Messages.KEY_idCompTea, tPase.getIdCompanyaTeatral());
		nuevoPase.put(Messages.KEY_idObra, tPase.getIdObra());
		nuevoPase.put(Messages.KEY_act, tPase.isActivo());
		nuevoPase.put(Messages.KEY_fecha, tPase.getFecha());
		nuevoPase.put(Messages.KEY_stock, tPase.getStock());
		nuevoPase.put(Messages.KEY_precioPase, tPase.getPrecio());
		pases.put(Integer.toString(newID), nuevoPase);
		bdPase.put(Messages.KEY_pases, pases);
		OpsBBDD.write(bdPase, Messages.BDPase);
		return newID;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
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
		return -1;
	}

	@Override
	public TPase read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
			JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
			JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
			TPase tPase = null;
			if (pases.has(Integer.toString(id))) {
				JSONObject pase = pases.getJSONObject(Integer.toString(id));
				tPase = read(pase);
				tPase.setIdPase(id);
			}
			if (tPase.isActivo())
			return tPase;	
			else return null;
		}
		return null;
	}

	@Override
	public ArrayList<TPase> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
			JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
			JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
			ArrayList<TPase> pasesADevolver = new ArrayList<>();
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
		else return null;
	}

	@Override
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException {		
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
			JSONObject bdPase = OpsBBDD.read(Messages.BDPase);
			JSONObject pases = bdPase.getJSONObject(Messages.KEY_pases);
			if(pases.has(String.valueOf(tPase.getIdPase()))) {
				JSONObject pase = pases.getJSONObject(String.valueOf(tPase.getIdPase()));
				if (pase.getBoolean(Messages.KEY_act)) {
					JSONObject nuevoPase = new JSONObject();
					nuevoPase.put(Messages.KEY_idPase, tPase.getIdPase());
					nuevoPase.put(Messages.KEY_idCompTea, tPase.getIdCompanyaTeatral());
					nuevoPase.put(Messages.KEY_idObra, tPase.getIdObra());
					if (tPase.getStock() <= 0) nuevoPase.put(Messages.KEY_act, false);
					else nuevoPase.put(Messages.KEY_act, tPase.isActivo());
					nuevoPase.put(Messages.KEY_fecha, tPase.getFecha().toString());
					nuevoPase.put(Messages.KEY_stock, tPase.getStock());
					nuevoPase.put(Messages.KEY_precioPase, tPase.getPrecio());
					
					pases.put(String.valueOf(tPase.getIdPase()), nuevoPase);
					bdPase.put(Messages.KEY_pases, pases);
					OpsBBDD.write(bdPase, Messages.BDPase);
					return 1;
				}
				else return -1;
			}
			else return -1;
		}
		else return -1;
	}
	
	private TPase read(JSONObject jsonPas) {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//LocalDateTime fechaParseada = LocalDateTime.parse(jsonPas.getString(Messages.KEY_fecha), formatter);
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		ZonedDateTime zonedDate = ZonedDateTime.parse(jsonPas.getString(Messages.KEY_fecha), formatter);
		LocalDateTime fecha = zonedDate.toLocalDateTime();*/
		return new TPase(
				jsonPas.getInt(Messages.KEY_idPase), 
				jsonPas.getInt(Messages.KEY_idCompTea), 
				jsonPas.getInt(Messages.KEY_idObra),
				jsonPas.getBoolean(Messages.KEY_act),
				LocalDateTime.parse(jsonPas.getString(Messages.KEY_fecha)),
				jsonPas.getInt(Messages.KEY_stock),
				jsonPas.getFloat(Messages.KEY_precioPase));
		}

	@Override
	public void deletePorObra(int idObra) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
			JSONObject bdPases = OpsBBDD.read(Messages.BDPase);
			JSONObject pases = bdPases.getJSONObject(Messages.KEY_pases);
			List<String> clavesEliminar = new LinkedList<>();
			for (String idPase : pases.keySet()) {
				JSONObject pase = pases.getJSONObject(idPase);
				int idObraPase = pase.getInt(Messages.KEY_idObra);
				if (idObra == idObraPase && pase.getBoolean(Messages.KEY_act)) clavesEliminar.add(idPase);
			}
			for (String key: clavesEliminar) {
				//bdPases.remove(key); no hago borrado fisico
				JSONObject pase = pases.getJSONObject(key);
				pase.put(Messages.KEY_act, false);
				pases.put(key, pase);
				bdPases.put(Messages.KEY_pases, pases);
			}
			OpsBBDD.write(bdPases, Messages.BDPase);
		}
	}

	@Override
	public void deletePorCompTea(int idCompTea) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDPase)) {
			JSONObject bdPases = OpsBBDD.read(Messages.BDPase);
			JSONObject pases = bdPases.getJSONObject(Messages.KEY_pases);
			List<String> clavesEliminar = new LinkedList<>();
			for (String idPase : pases.keySet()) {
				JSONObject pase = pases.getJSONObject(idPase);
				int idCompTeaPase = pase.getInt(Messages.KEY_idCompTea);
				if (idCompTea == idCompTeaPase && pase.getBoolean(Messages.KEY_act)) clavesEliminar.add(idPase);
			}
			for (String key: clavesEliminar) {
				JSONObject pase = pases.getJSONObject(key);
				pase.put(Messages.KEY_act, false);
				pases.put(key, pase);
				bdPases.put(Messages.KEY_pases, pases);
			}
			OpsBBDD.write(bdPases, Messages.BDPase);
		}
	}
	
}
