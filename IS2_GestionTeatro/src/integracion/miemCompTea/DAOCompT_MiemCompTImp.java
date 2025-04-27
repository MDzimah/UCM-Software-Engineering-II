package integracion.miemCompTea;

import java.util.ArrayList;
import java.util.Collection;
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
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		/*
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
	        
			String _id = Integer.toString(id);
			if (relCom_Miem.has(_id)) {
				relCom_Miem.getJSONObject(_id).remove(_id);
			    return id;
			}
		}
        return -1;
        */
	}

	@Override
	public TCompT_MiemCompT read(int id) throws BBDDReadException {
		/*
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			JSONObject relCom_Miem = BDRel.getJSONObject(Messages.KEY_RelComp_Miem);
			
			TCompT_MiemCompT tRel = null;
			String _id = Integer.toString(id);
			
			if (relCom_Miem.has(_id)) {
				tRel = this.createTCT_MCT(relCom_Miem.getJSONObject(_id));
				tRel.setIdRelacionMiemComp(id);
			}
			return tRel;
		}
		return null;
		*/
	}

	@Override
	public Collection<TCompT_MiemCompT> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT_MCT)) {
			JSONObject BDRel = OpsBBDD.read(Messages.BDCT_MCT);
			
			Collection<TCompT_MiemCompT> relaciones = new ArrayList<>();
			
			JSONObject relCom_Miem = new JSONObject(BDRel.getJSONArray(Messages.KEY_RelComp_Miem));
			
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
