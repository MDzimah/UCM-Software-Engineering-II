package integracion.pase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDFacReadException;
import exceptions.BBDDFacWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOLineaFactura;
import misc.OpsBBDD;
import misc.PanelUtils;
import negocio.factura.TLineaFactura;
import negocio.pase.TPase;

public class DAOPaseImp implements DAOPase {

	@Override
	public int create(TPase tPase) {
		try {
			JSONObject bdPase = OpsBBDD.read("BDPase.json");
			JSONArray pases = new JSONArray(bdPase.get("facturas"));
			int newID = bdPase.getInt("lastId") + 1;
			bdPase.put("lastId", newID);
			JSONObject nuevoPase = new JSONObject();
			nuevoPase.put("idPase", newID);
			nuevoPase.put("idCompanyaTeatral", tPase.getIdCompanyaTeatral());
			nuevoPase.put("idObra", tPase.getIdObra());
			nuevoPase.put("activo", tPase.isActivo());
			nuevoPase.put("fecha", tPase.getFecha().toString());
			nuevoPase.put("stock", tPase.getStock());
			nuevoPase.put("precio", tPase.getPrecio());
			pases.put(nuevoPase);
			bdPase.put("pases", pases);
			OpsBBDD.write(bdPase, "BDPase.json");
			return newID;
		} 
		catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDPase.json", e.getMessage());
			return -1;
		}
		catch (BBDDFacWriteException e) {
			PanelUtils.panelBBDDWriteError(null, "BDPase.json", e.getMessage());
			return -1;
		}
	}

	@Override
	public int delete(int id) {
		try {
			JSONObject bdPase = OpsBBDD.read("BDPase.json");
			JSONArray pases = new JSONArray(bdPase.getJSONArray("pases"));
			for (int i = 0; i < pases.length();i++) {
				JSONObject pase = pases.getJSONObject(i);
				if (pase.getInt("idPase") == id) {
					pase.put("activo", false);
					return 1; //se ha borrado exitosamente el usuario
				}
			}
		} catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
		}
		return 0; //no se ha borrado pase porque no se ha encontrado
	}

	@Override
	public TPase read(int id) {
		try {
			JSONObject bdPase = OpsBBDD.read("BDPase.json");
			JSONArray pases = new JSONArray(bdPase.getJSONArray("pases"));
			for (int i = 0; i < pases.length();i++) {
				JSONObject pase = pases.getJSONObject(i);
				if (pase.getInt("idPase") == id) {
					return read(pase);
				}
			}
		} catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
		}
		return null;
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
		} catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
		}
		return null;
	}

	@Override
	public int update(TPase tPase) {
		return 0;
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
