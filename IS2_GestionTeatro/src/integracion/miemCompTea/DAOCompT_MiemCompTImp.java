package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.Messages;
import misc.OpsBBDD;
import negocio.miemCompTea.TCompT_MiemCompT;

public class DAOCompT_MiemCompTImp implements DAOCompT_MiemCompT{
	@Override
	public int create(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException {
		JSONObject BDRel = new JSONObject(); 
		
		if (OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			BDRel.put(Messages.KEY_lastId, -1);
			BDRel.put(Messages.KEY_relComp_Miem, new JSONObject());
		}
		else {
			if(existeRelacion(tCompT_MieCT.getIdCompania(),tCompT_MieCT.getIdMiembroComp()) != -1) {
				return -1; //si ya existe esa relacion se devuelve -1
			}
			BDRel = OpsBBDD.read(Messages.BDCT_MCT);
		}
		
		JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
		
		int newId = BDRel.getInt(Messages.KEY_lastId) + 1;
		BDRel.put(Messages.KEY_lastId, newId);
		
		relCom_Miem.put(Integer.toString(newId), this.createJSON(tCompT_MieCT));
		OpsBBDD.write(BDRel, Messages.BDCT_MCT);
		
		return newId;
	}
	
	@Override
	public int delete_relacion(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
			int idRel = this.existeRelacion(tCompT_MieCT.getIdCompania(), tCompT_MieCT.getIdMiembroComp());
			if(idRel != -1) {
				relCom_Miem.remove(Integer.toString(idRel));
				OpsBBDD.write(BDRel, Messages.BDCT_MCT);
				return idRel;
			}
		}        
        return -1;
	}
	
	@Override
	public int delete_compania(int idComp) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
	        
			Set<String> allIds = relCom_Miem.keySet();
			boolean encontrado = false;
			List<String> toRemove = new ArrayList<>();
			
			for (String id : allIds) {
			    JSONObject rel = relCom_Miem.getJSONObject(id);
			    if (rel.getInt(Messages.KEY_idCompTea) == idComp) {
			        toRemove.add(id);
			    }
			}
			for (String id : toRemove) {
			    relCom_Miem.remove(id);
			    encontrado = true;
			}
			
			if(encontrado) {
				OpsBBDD.write(BDRel, Messages.BDCT_MCT);
				return idComp;
			}
		}
        return -1;
	}

	@Override
	public int delete_miembro(int idMiem) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
	        
			Set<String> allIds = relCom_Miem.keySet();
			boolean encontrado = false;
			List<String> toRemove = new ArrayList<>();
			
			for (String id : allIds) {
			    JSONObject rel = relCom_Miem.getJSONObject(id);
			    if (rel.getInt(Messages.KEY_idMiemComp) == idMiem) {
			        toRemove.add(id);
			    }
			}
			for (String id : toRemove) {
			    relCom_Miem.remove(id);
			    encontrado = true;
			}

			if(encontrado) {
				OpsBBDD.write(BDRel, Messages.BDCT_MCT);
				return idMiem;
			}
		}
        return -1;
	}

	@Override
	public Collection<TCompT_MiemCompT> read_compania(int idComp) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			for (String id : allIds) {
				JSONObject rel = relCom_Miem.getJSONObject(id);
				
				if(rel.getInt(Messages.KEY_idCompTea) == idComp) {
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
	public Collection<TCompT_MiemCompT> read_miembro(int idMiem) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			for (String id : allIds) {
				JSONObject rel = relCom_Miem.getJSONObject(id);
				
				if(rel.getInt(Messages.KEY_idMiemComp) == idMiem) {
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
			
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
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
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
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
	
	private int existeRelacion(int idComp, int idMiem) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_relComp_Miem);
			
			Set<String> allIds = relCom_Miem.keySet();
			
			for (String id : allIds) {
			    JSONObject rel = relCom_Miem.getJSONObject(id);
			    if (rel.getInt(Messages.KEY_idCompTea) == idComp && rel.getInt(Messages.KEY_idMiemComp) == idMiem) {
			    	return Integer.valueOf(id);
			    }
			}
		} 
		return -1;
	}
}
