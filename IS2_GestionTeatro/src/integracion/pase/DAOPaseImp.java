package integracion.pase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOLineaFactura;
import misc.Messages;
import misc.OpsBBDD;
import misc.SwingUtils;
import negocio.factura.TLineaFactura;
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
	public Collection<TPase> readAll() {
		try {
			JSONObject bdPase = OpsBBDD.read("BDPase.json");
			JSONArray pases = new JSONArray(bdPase.getJSONArray("pases"));
			Collection<TPase> pasesADevolver = new ArrayList();
			for (int i = 0; i < pases.length();i++) {
				JSONObject pase = pases.getJSONObject(i);
				if (pase.getBoolean("activo")) {
					pasesADevolver.add(read(pase));
				}
			}
		} catch (BBDDReadException e) {
			SwingUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
		}
		return null;
	}

	@Override
	public int update(TPase tPase) {
		try {
			JSONObject bdPase = OpsBBDD.read("BDPase.json");
			if(bdPase.has(String.valueOf(tPase.getIdObra()))) {
				
				JSONObject nuevoPase = new JSONObject();
				nuevoPase.put("idPase", tPase.getIdPase());
				nuevoPase.put("idCompanyaTeatral", tPase.getIdCompanyaTeatral());
				nuevoPase.put("idObra", tPase.getIdObra());
				nuevoPase.put("activo", tPase.isActivo());
				nuevoPase.put("fecha", tPase.getFecha().toString());
				nuevoPase.put("stock", tPase.getStock());
				nuevoPase.put("precio", tPase.getPrecio());
				
				bdPase.put(String.valueOf(tPase.getIdObra()), nuevoPase);
				return 1;
			}
			else
				return -1;
		} catch (BBDDReadException e) {
			SwingUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
			return -1;
		}
	}
	
	private TPase read(JSONObject jsonPas) {
		return new TPase(
				jsonPas.getInt("idPase"), 
				jsonPas.getInt("idCompanyaTeatral"), 
				jsonPas.getInt("idObra"),
				jsonPas.getBoolean("activo"),
				LocalDateTime.parse(jsonPas.getString("fecha")),
				jsonPas.getInt("stock"),
				jsonPas.getFloat("precio"));
		}
	
}
