package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.miemCompTea.TCompT_MiemCompT;

public class DAOCompT_MiemCompTImp implements DAOCompT_MiemCompT{
	@Override
	public int create(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException {
		JSONObject BDRel = new JSONObject(); 
		
		if (OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			BDRel.put(Messages.KEY_lastId, 0);
			BDRel.put(Messages.KEY_RelComp_Miem, new JSONObject());
		}
		else BDRel = OpsBBDD.read(Messages.BDCT_MCT);
		
		JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
		
		int newId = BDRel.getInt(Messages.KEY_lastId) + 1;
		BDRel.put(Messages.KEY_lastId, newId);
		
		relCom_Miem.put(Integer.toString(newId), this.createJSON(tCompT_MieCT));
		OpsBBDD.write(BDRel, Messages.BDCT_MCT);
		
		return newId;
	}
	
	@Override
	public int delete_compania(int id_comp) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
	        
			Set<String> allIds = relCom_Miem.keySet();
			boolean encontrado = false;
			List<String> toRemove = new ArrayList<>();
			
			for (String id : allIds) {
			    JSONObject rel = relCom_Miem.getJSONObject(id);
			    if (rel.getInt(Messages.KEY_idCompTea) == id_comp) {
			        toRemove.add(id);
			    }
			}
			for (String id : toRemove) {
			    relCom_Miem.remove(id);
			    encontrado = true;
			}
			
			if(encontrado) {
				OpsBBDD.write(BDRel, Messages.BDCT_MCT);
				return id_comp;
			}
		}
        return -1;
	}

	@Override
	public int delete_miembro(int id_miem) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
	        
			Set<String> allIds = relCom_Miem.keySet();
			boolean encontrado = false;
			List<String> toRemove = new ArrayList<>();
			
			for (String id : allIds) {
			    JSONObject rel = relCom_Miem.getJSONObject(id);
			    if (rel.getInt(Messages.KEY_idMiemComp) == id_miem) {
			        toRemove.add(id);
			    }
			}
			for (String id : toRemove) {
			    relCom_Miem.remove(id);
			    encontrado = true;
			}

			if(encontrado) {
				OpsBBDD.write(BDRel, Messages.BDCT_MCT);
				return id_miem;
			}
		}
        return -1;
	}

	@Override
	public Collection<TCompT_MiemCompT> read_compania(int id_comp) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			for (String id : allIds) {
				JSONObject rel = relCom_Miem.getJSONObject(id);
				
				if(rel.getInt(Messages.KEY_idCompTea) == id_comp) {
					TCompT_MiemCompT tRel = this.createTCT_MCT(rel);
					tRel.setIdRelacionMiemComp(Integer.valueOf(id));
					relaciones.add(tRel);
				}
			}
			
			((ArrayList<TCompT_MiemCompT>) relaciones).sort((a, b) -> Integer.compare(a.getIdCompania(), b.getIdCompania()));
			return relaciones;
		}
		return null;
	}

	@Override
	public Collection<TCompT_MiemCompT> read_miembro(int id_miem) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			for (String id : allIds) {
				JSONObject rel = relCom_Miem.getJSONObject(id);
				
				if(rel.getInt(Messages.KEY_idMiemComp) == id_miem) {
					TCompT_MiemCompT tRel = this.createTCT_MCT(rel);
					tRel.setIdRelacionMiemComp(Integer.valueOf(id));
					relaciones.add(tRel);
				}
			}
			
			((ArrayList<TCompT_MiemCompT>) relaciones).sort((a, b) -> Integer.compare(a.getIdCompania(), b.getIdCompania()));
			return relaciones;
		}
		return null;
	}

	@Override
	public Collection<TCompT_MiemCompT> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			for (String id : allIds) {
				TCompT_MiemCompT tRel = this.createTCT_MCT(relCom_Miem.getJSONObject(id));
				tRel.setIdRelacionMiemComp(Integer.valueOf(id));
				
				relaciones.add(tRel);
			}
			
			((ArrayList<TCompT_MiemCompT>) relaciones).sort((a, b) -> Integer.compare(a.getIdCompania(), b.getIdCompania()));
			return relaciones;
		}
		return null;
	}

	@Override
	public int update(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
			
			String id = Integer.toString(tCompT_MieCT.getIdRelacionMiemComp());
			
			if (relCom_Miem.has(id)) {
				relCom_Miem.put(id, this.createJSON(tCompT_MieCT));
        		OpsBBDD.write(BDRel, Messages.BDCT_MCT);
        		return tCompT_MieCT.getIdRelacionMiemComp();
			}
		}
        return -1;
	}
	
	private TCompT_MiemCompT createTCT_MCT(JSONObject rel) {
		return new TCompT_MiemCompT(
				rel.getInt(Messages.KEY_idCompTea), 
				rel.getInt(Messages.KEY_idMiemComp));
	}
	
	private JSONObject createJSON(TCompT_MiemCompT tRel) {
		JSONObject relC_M = new JSONObject();
		relC_M.put(Messages.KEY_idCompTea, tRel.getIdCompania());
		relC_M.put(Messages.KEY_idMiemComp, tRel.getIdMiembroComp());
		return relC_M;
	}
}
