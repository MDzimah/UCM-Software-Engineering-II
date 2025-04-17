package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDFacReadException;
import exceptions.BBDDFacWriteException;
import misc.OpsBBDD;
import misc.PanelUtils;
import negocio.miemCompTea.TMiemCompTea;

public class DAOMiemCompTeaImp implements DAOMiemCompTea {

	@Override
	public int create(TMiemCompTea tMieCT) {
		try {
			JSONObject BDMiemComp;
			BDMiemComp = OpsBBDD.read("BDJMiemCompTea.json");
			JSONArray miembrosComp = new JSONArray(BDMiemComp.get("miembros de la compania teatral"));
			
			int newId = BDMiemComp.getInt("lastId") + 1;
			BDMiemComp.put("lastId", newId);
			
			JSONObject nuevoMiemComp = new JSONObject();			
			nuevoMiemComp.put("idMiemComp", newId);
			nuevoMiemComp.put("DNI", tMieCT.getDNI());
			nuevoMiemComp.put("nombre", tMieCT.getNombre());
			nuevoMiemComp.put("apellido", tMieCT.getApellido());
			nuevoMiemComp.put("email", tMieCT.getEmail());
			nuevoMiemComp.put("edad", tMieCT.getEdad());
			nuevoMiemComp.put("activo", tMieCT.getActivo());
			nuevoMiemComp.put("genero", tMieCT.getGenero().toString());			
			
			miembrosComp.put(nuevoMiemComp);
			BDMiemComp.put("miembros de la compania teatral", miembrosComp);
			OpsBBDD.write(BDMiemComp, "BDMiemCompTea.json");
			
			return newId;
		} 
		catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDMiemCompTea.json", e.getMessage());
			return -1;
		}
		catch (BBDDFacWriteException e) {
			PanelUtils.panelBBDDWriteError(null, "BDMiemCompTea.json", e.getMessage());
			return -1;
		}
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TMiemCompTea read(int id) {
		try {
			JSONObject BDMiemCompTea = OpsBBDD.read("BDMiemCompTea.json");
			JSONArray miembrosComp = new JSONArray(BDMiemCompTea.getJSONArray("miembros de la compania teatral"));
			
			for (int i = 0; i < miembrosComp.length(); i++) {
				JSONObject miembroComp = miembrosComp.getJSONObject(i);
				if (miembroComp.getInt("idMiemComp") == id) {
					return createTMiemCompTea(miembroComp);
				}
			}
		} 
		catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDMiemCompTea.json", e.getMessage());
		}
		return null;
	}

	@Override
	public Collection<TMiemCompTea> readAll() {
		try {
			JSONObject BDMiemComp = OpsBBDD.read("BDMiemCompTea.json");			
			JSONArray miembrosComp = new JSONArray(BDMiemComp.getJSONArray("miembros de la compania teatral"));
			Collection<TMiemCompTea> miembrosCollection = new ArrayList<>();
			
			for (int i = 0; i < miembrosComp.length(); i++) {
				miembrosCollection.add(createTMiemCompTea(miembrosComp.getJSONObject(i)));
			}
			return miembrosCollection;
		} 
		catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDMiemCompTea.json", e.getMessage());
			return null;
		}
	}

	@Override
	public int update(TMiemCompTea tMieCT) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public TMiemCompTea readByDNI(String dni) {
		try {
			JSONObject BDMiemCompTea = OpsBBDD.read("BDMiemCompTea.json");
			JSONArray miembrosComp = new JSONArray(BDMiemCompTea.getJSONArray("miembros de la compania teatral"));
			
			for (int i = 0; i < miembrosComp.length(); i++) {
				JSONObject miembroComp = miembrosComp.getJSONObject(i);
				if (miembroComp.getString("DNI").equals(dni)) {
					return createTMiemCompTea(miembroComp);
				}
			}
		} 
		catch (BBDDFacReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDMiemCompTea.json", e.getMessage());
		}
		return null;
	}

	private TMiemCompTea createTMiemCompTea(JSONObject miembroComp) {
		return new TMiemCompTea(
				miembroComp.getInt("idMiemComp"),
				miembroComp.getString("DNI"),
				miembroComp.getString("nombre"),
				miembroComp.getString("apellido"),
				miembroComp.getString("email"),
				miembroComp.getInt("edad"),
				miembroComp.getBoolean("activo"),
				miembroComp.getString("genero")
				);
	}
}
