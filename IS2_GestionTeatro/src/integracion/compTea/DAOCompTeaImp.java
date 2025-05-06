package integracion.compTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOLineaFactura;
import integracion.miemCompTea.DAOMiemCompTea;
import integracion.pase.DAOPase;
import misc.Messages;
import misc.OpsBBDD;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.miemCompTea.TMiemCompTea;
import negocio.pase.TPase;


public class DAOCompTeaImp implements DAOCompTea {

	@Override
	public int create(TCompTea tCompTea) throws BBDDReadException, BBDDWriteException  {
		JSONObject bdCompania= new JSONObject();
		if (OpsBBDD.isEmpty(Messages.BDCT)) {
			bdCompania.put(Messages.KEY_lastId, 0);
		}
		else {
			bdCompania = OpsBBDD.read(Messages.BDCT);
			Set<String> idCompanias = bdCompania.keySet();
			for(String id: idCompanias) {
				if(!id.equals(Messages.KEY_lastId)){//hay una clave que guarda lastid
					JSONObject JSONcompania = bdCompania.getJSONObject(id);
					if (JSONcompania.getString(Messages.KEY_compTea).equals(tCompTea.getNombre())) {
						return -1;
					}
					}
			}
			}
		    
			int lastPos = bdCompania.getInt(Messages.KEY_lastId);
			JSONObject nuevaCompania = new JSONObject();
			
			nuevaCompania.put(Messages.KEY_idCompTea, lastPos+1);
			nuevaCompania.put(Messages.KEY_compTea, tCompTea.getNombre());
			nuevaCompania.put(Messages.KEY_direccion, tCompTea.getDireccion());
			nuevaCompania.put(Messages.KEY_coste, tCompTea.getCosteContratacion());
			nuevaCompania.put(Messages.KEY_act, tCompTea.isActivo());
			bdCompania.put(String.valueOf(lastPos+1), nuevaCompania);
			bdCompania.put(Messages.KEY_lastId, lastPos+1);
			
			OpsBBDD.write(bdCompania, Messages.BDCT);
			
			return lastPos+1;
		
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDCT)) {
		JSONObject bdCompania = OpsBBDD.read(Messages.BDCT);
		JSONObject compania = bdCompania.getJSONObject(Integer.toString(id));
        
		if (compania.getBoolean(Messages.KEY_act)) {
	        compania.put(Messages.KEY_act, false);
	        OpsBBDD.write(bdCompania, Messages.BDCT);
	        return id;
		}
		}
        return -1;
	}

	@Override
	public TCompTea read(int id) throws BBDDReadException {
		TCompTea tCompTea = null;
		if (!OpsBBDD.isEmpty(Messages.BDMCT)) {//si esta vacio logicamente no habra nada que devolver (null)
		JSONObject bdCompania= OpsBBDD.read(Messages.BDCT);
		if (bdCompania.has(Integer.toString(id))) {
			JSONObject compania = bdCompania.getJSONObject(Integer.toString(id));
			if(compania.getBoolean(Messages.KEY_act)) {
			tCompTea = new TCompTea(id, compania.getString(Messages.KEY_compTea), compania.getString(Messages.KEY_direccion),compania.getBoolean(Messages.KEY_act), compania.getFloat(Messages.KEY_coste));
			}
			}
		}
		
		return tCompTea;
	
	}

	@Override
	public Collection<TCompTea> readAll() throws BBDDReadException {
		Collection<TCompTea> companias = new ArrayList<>();
		if (!OpsBBDD.isEmpty(Messages.BDCT)) {
			JSONObject BDCompania = OpsBBDD.read(Messages.BDCT);
			Set<String> idCompanias = BDCompania.keySet();
			for (String id : idCompanias) {
				
				if(!id.equals(Messages.KEY_lastId)){//hay una clave que guarda lastid
					JSONObject JSONcompania = BDCompania.getJSONObject(id);
					if (JSONcompania.getBoolean(Messages.KEY_act)) {
						TCompTea tCompTea = new TCompTea(Integer.parseInt(id), JSONcompania.getString(Messages.KEY_compTea), JSONcompania.getString(Messages.KEY_direccion),JSONcompania.getBoolean(Messages.KEY_act), JSONcompania.getFloat(Messages.KEY_coste));
						companias.add(tCompTea);
					}
		        }

			
			}
			if(companias.isEmpty())return null;
			return companias;
		}
		else return null;
	}
	

	@Override
	public int update(TCompTea tCompTea) throws BBDDWriteException, BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDCT)) {
			JSONObject BDCompania = OpsBBDD.read(Messages.BDCT);
			
			Set<String> ids = BDCompania.keySet();
			for (String idCompania : ids) {
				if(!ids.equals(Messages.KEY_lastId)) {
				JSONObject Compania = BDCompania.getJSONObject(idCompania);
				
				
	            if (Integer.valueOf(idCompania) == tCompTea.getId() && Compania.getBoolean(Messages.KEY_act)) {
	            	Compania.put(Messages.KEY_act, tCompTea.isActivo());
	    	        Compania.put(Messages.KEY_compTea, tCompTea.getNombre());
	    			Compania.put(Messages.KEY_direccion, tCompTea.getDireccion());
	    			Compania.put(Messages.KEY_coste, tCompTea.getCosteContratacion());
	    			Compania.put(Messages.KEY_act, tCompTea.isActivo());
	            	
	            	OpsBBDD.write(BDCompania, Messages.BDFac);
	                return Integer.valueOf(idCompania);
	            }
	            }
	        }
		}
        return -1; 
	
	}

}
