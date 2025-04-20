package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.*;
import misc.Messages;
import misc.OpsBBDD;
import negocio.miemCompTea.TMiemCompTea;
import negocio.miemCompTea.TMiemCompTea.Genero;

public class DAOMiemCompTeaImp implements DAOMiemCompTea {

	@Override
	public int create(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException{
		JSONObject BDMiemComp;
		BDMiemComp = OpsBBDD.read(Messages.BDMCT);
		JSONArray miembrosComp = new JSONArray(BDMiemComp.get(Messages.KEY_MiembCompTea));
		
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
		
		miembrosComp.put(nuevoMiemComp);
		BDMiemComp.put(Messages.KEY_MiembCompTea, miembrosComp);
		OpsBBDD.write(BDMiemComp, Messages.BDMCT);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);
		JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_MiembCompTea);
        
		if (miembrosComp.has(Integer.toString(id))) {
	        JSONObject elimMiemComp = miembrosComp.getJSONObject(Integer.toString(id));
	        elimMiemComp.put(Messages.KEY_act, false);
	        OpsBBDD.write(BDMiemComp, Messages.BDMCT);
	        return id;
		}
        return -1;
	}

	@Override
	public TMiemCompTea read(int id) throws BBDDReadException {
		JSONObject miembrosComp = OpsBBDD.read(Messages.BDMCT).getJSONObject(Messages.KEY_MiembCompTea);
		
		TMiemCompTea tMiemComp = null;
		if (miembrosComp.has(Integer.toString(id))) {
			JSONObject miemComp = miembrosComp.getJSONObject(Integer.toString(id));
			tMiemComp = createTMiemCompTea(miemComp);
			tMiemComp.setIdMiembComp(id);
		}
		return tMiemComp;
	}

	@Override
	public Collection<TMiemCompTea> readAll() throws BBDDReadException {
		JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);			
		JSONArray miembrosComp = new JSONArray(BDMiemComp.getJSONArray(Messages.KEY_MiembCompTea));
		Collection<TMiemCompTea> miembrosCollection = new ArrayList<>();
		
		for (int i = 0; i < miembrosComp.length(); i++) {
			miembrosCollection.add(createTMiemCompTea(miembrosComp.getJSONObject(i)));
		}
		return miembrosCollection;
	}

	@Override
	public int update(TMiemCompTea tMieCT) throws BBDDReadException, BBDDWriteException {
		JSONObject BDMiemComp = OpsBBDD.read(Messages.BDMCT);
		JSONObject miembrosComp = BDMiemComp.getJSONObject(Messages.KEY_MiembCompTea);
		
		Set<String> allIdsMiemComp = miembrosComp.keySet();
		for (String idMiemComp : allIdsMiemComp) {
			if (Integer.valueOf(idMiemComp) == tMieCT.getIdMiembComp()) {
				JSONObject miemComp = miembrosComp.getJSONObject(idMiemComp);
				miemComp.put(Messages.KEY_DNI, tMieCT.getDNI());
				miemComp.put(Messages.KEY_nombre, tMieCT.getNombre());
				miemComp.put(Messages.KEY_apellido, tMieCT.getApellido());
				miemComp.put(Messages.KEY_email, tMieCT.getEmail());
				miemComp.put(Messages.KEY_edad, tMieCT.getEdad());
				miemComp.put(Messages.KEY_act, tMieCT.getActivo());
				miemComp.put(Messages.KEY_genero, tMieCT.getGenero().toString());
				
				OpsBBDD.write(BDMiemComp, Messages.BDFac);
                return Integer.valueOf(idMiemComp);
			}
		}
		return -1;
	}
	
	@Override
	public TMiemCompTea readByDNI(String dni) throws BBDDReadException {
		JSONObject BDMiemCompTea = OpsBBDD.read(Messages.BDMCT);
		JSONArray miembrosComp = new JSONArray(BDMiemCompTea.getJSONArray(Messages.KEY_MiembCompTea));
		
		for (int i = 0; i < miembrosComp.length(); i++) {
			JSONObject miembroComp = miembrosComp.getJSONObject(i);
			if (miembroComp.getString(Messages.KEY_DNI).equals(dni)) {
				return createTMiemCompTea(miembroComp);
			}
		}
	
		return null;
	}

	private TMiemCompTea createTMiemCompTea(JSONObject miembroComp) {
		return new TMiemCompTea(
				miembroComp.getString(Messages.KEY_DNI),
				miembroComp.getString(Messages.KEY_nombre),
				miembroComp.getString(Messages.KEY_apellido),
				miembroComp.getString(Messages.KEY_email),
				miembroComp.getInt(Messages.KEY_edad),
				miembroComp.getBoolean(Messages.KEY_act),
				Genero.valueOf(miembroComp.getString(Messages.KEY_genero))
				);
	}
}
