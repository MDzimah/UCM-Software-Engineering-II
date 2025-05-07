package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.*;
import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.Messages;
import misc.OpsBBDD;
import negocio.miemCompTea.TCompT_MiemCompT;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;

public class DAOMiemCompTeaImp implements DAOMiemCompTea {

	@Override
	public int create(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException{
		JSONObject BDMiemComp = new JSONObject();
		
		if (OpsBBDD.isEmpty(Messages.BDMCT)) {
			BDMiemComp.put(Messages.KEY_lastId, 0);
			BDMiemComp.put(Messages.KEY_miembCompTea, new JSONObject());
		}
		else {
			BDMiemComp = OpsBBDD.read(Messages.BDMCT);
		}
		
		JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_miembCompTea);
		
		int newId = BDMiemComp.getInt(Messages.KEY_lastId) + 1;
		BDMiemComp.put(Messages.KEY_lastId, newId);
		
		JSONObject nuevoMiemComp = new JSONObject();			
		nuevoMiemComp.put(Messages.KEY_idMiemComp, newId);
		nuevoMiemComp.put(Messages.KEY_DNI, tMieCT.getDNI());
		nuevoMiemComp.put(Messages.KEY_nombre, tMieCT.getNombre());
		nuevoMiemComp.put(Messages.KEY_apellido, tMieCT.getApellido());
		nuevoMiemComp.put(Messages.KEY_email, tMieCT.getEmail());
		nuevoMiemComp.put(Messages.KEY_edad, tMieCT.getEdad());
		nuevoMiemComp.put(Messages.KEY_act, tMieCT.getActivo());
		nuevoMiemComp.put(Messages.KEY_genero, tMieCT.getGenero().toString());			
		
		miembrosComp.put(Integer.toString(newId), nuevoMiemComp);
		BDMiemComp.put(Messages.KEY_miembCompTea, miembrosComp);
		OpsBBDD.write(BDMiemComp, Messages.BDMCT);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {
			JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);
			JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_miembCompTea);
			
			String _id = Integer.toString(id);
			if (miembrosComp.has(_id) && miembrosComp.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				DAOCompT_MiemCompTImp relMiem_Comp = FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea_MiemCompTea();
				relMiem_Comp.delete_miembro(id);
				miembrosComp.getJSONObject(Integer.toString(id)).put(Messages.KEY_act, false);
		        OpsBBDD.write(BDMiemComp, Messages.BDMCT);
		        return id;
			}
		}        
		
        return -1;
	}

	@Override
	public TMiemCompTea read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {
			JSONObject miembrosComp = OpsBBDD.read(Messages.BDMCT).getJSONObject(Messages.KEY_miembCompTea);
			
			TMiemCompTea tMiemComp = null;
			String _id = Integer.toString(id);
			if (miembrosComp.has(_id)) {
				JSONObject miemComp = miembrosComp.getJSONObject(Integer.toString(id));
				tMiemComp = createTMiemCompTea(miemComp);
			}
			return tMiemComp;
		}
		return null;
	}

	@Override
	public Collection<TMiemCompTea> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {
			JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);			
			JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_miembCompTea);
			Collection<TMiemCompTea> miembrosCollection = new ArrayList<>();
			
			Set<String> allIds = miembrosComp.keySet();
			for (String id: allIds) {
				JSONObject miemComp = miembrosComp.getJSONObject(id);
				
				if(miemComp.getBoolean(Messages.KEY_act)) {
					TMiemCompTea tMiemComp = createTMiemCompTea(miemComp);
					miembrosCollection.add(tMiemComp);
				}
			}
			((ArrayList<TMiemCompTea>) miembrosCollection).sort((a, b) -> Integer.compare(a.getIdMiembComp(), b.getIdMiembComp()));
			return miembrosCollection;
		}
		return null;
	}

	@Override
	public int update(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {
			JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);
			JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_miembCompTea);
			
			String id = Integer.toString(tMieCT.getIdMiembComp());
			
			if (miembrosComp.has(id)) {
				JSONObject miemComp = new JSONObject();
				miemComp.put(Messages.KEY_idMiemComp, tMieCT.getIdMiembComp());
				miemComp.put(Messages.KEY_DNI, tMieCT.getDNI());
				miemComp.put(Messages.KEY_nombre, tMieCT.getNombre());
				miemComp.put(Messages.KEY_apellido, tMieCT.getApellido());
				miemComp.put(Messages.KEY_email, tMieCT.getEmail());
				miemComp.put(Messages.KEY_edad, tMieCT.getEdad());
				miemComp.put(Messages.KEY_act, tMieCT.getActivo());
				miemComp.put(Messages.KEY_genero, tMieCT.getGenero().toString());
				
				miembrosComp.put(id, miemComp);
				OpsBBDD.write(BDMiemComp, Messages.BDMCT);
                return Integer.valueOf(id);
			}
		}
		return -1;
	}
	
	@Override
	public TMiemCompTea readByDNI(String dni) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {
			JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);
			JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_miembCompTea);
			
			Set<String> allIds = miembrosComp.keySet();
			for (String id: allIds) {
				JSONObject miembroComp = miembrosComp.getJSONObject(id);
				
				if (miembroComp.getString(Messages.KEY_DNI).equals(dni)) {
					return createTMiemCompTea(miembroComp);
				}
			}
		}
		return null;
	}

	private TMiemCompTea createTMiemCompTea(JSONObject miembroComp) {
		TMiemCompTea tMiemComp = new TMiemCompTea(
				miembroComp.getString(Messages.KEY_DNI),
				miembroComp.getString(Messages.KEY_nombre),
				miembroComp.getString(Messages.KEY_apellido),
				miembroComp.getString(Messages.KEY_email),
				miembroComp.getInt(Messages.KEY_edad),
				miembroComp.getBoolean(Messages.KEY_act),
				Genero.valueOf(miembroComp.getString(Messages.KEY_genero))
				);
		tMiemComp.setIdMiembComp(miembroComp.getInt(Messages.KEY_idMiemComp));
		return tMiemComp;
	}
}
