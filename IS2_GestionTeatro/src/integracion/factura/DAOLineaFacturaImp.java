package integracion.factura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	@Override
	public int create(TLineaFactura tLineaFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdLinFac = new JSONObject(); 
		
		//Inicializo la BD si no está ya inicializada
		if (OpsBBDD.isEmpty(Messages.BDLinFac)) {
			bdLinFac.put(Messages.KEY_lastId, 0);
			bdLinFac.put(Messages.KEY_LineasFac, new JSONObject());
		}
		else bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_LineasFac);
		
		//Aumentamos el último índice de la bd
		int newId = bdLinFac.getInt(Messages.KEY_lastId) + 1;
		bdLinFac.put(Messages.KEY_lastId, newId);
		
		//Creamos nueva linea
		JSONObject nuevaLinea = new JSONObject();
		nuevaLinea.put(Messages.KEY_idFac, tLineaFactura.getIdFactura());
		nuevaLinea.put(Messages.KEY_idPase, tLineaFactura.getIdPase());
		nuevaLinea.put(Messages.KEY_ctdad, tLineaFactura.getCantidad());
		nuevaLinea.put(Messages.KEY_LF_precio, tLineaFactura.getPrecioVenta());
		
		//La insertamos en la bd de facturas, su clave es su id
		lineasFactura.put(Integer.toString(newId), nuevaLinea);
		OpsBBDD.write(bdLinFac, Messages.BDLinFac);
		
		return newId;
	}
	
	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
	        
			if (lineasFactura.has(Integer.toString(id))) {
		        JSONObject linea = lineasFactura.getJSONObject(Integer.toString(id));
		        
		        if (linea.getBoolean(Messages.KEY_act)) {
			    	linea.put(Messages.KEY_act, false);
			    	return id;  
		        }
			}
		}
        return -1; //No se ha encontrado la linea de factura con dicho id
	}

	@Override
	public TLineaFactura read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
			
			TLineaFactura tLfRead = null;
			if (lineasFactura.has(Integer.toString(id))) {
				JSONObject lf = lineasFactura.getJSONObject(Integer.toString(id));
				
				if (lf.getBoolean(Messages.KEY_act)) {
					tLfRead = this.readAux(lf);
					tLfRead.setIdFactura(id);
				}
			}
			return tLfRead;
		}
		else return null;
	}
	
	@Override
	public Collection<TLineaFactura> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDFac);
			
			Collection<TLineaFactura> lfValidas = new ArrayList<>();
			
			JSONObject linFacs = new JSONObject(bdLinFac.getJSONArray(Messages.KEY_LineasFac));
			
			Set<String> allIdsLinFacs = linFacs.keySet();
			for (String idLf : allIdsLinFacs) {
				JSONObject lf = linFacs.getJSONObject(idLf);
				
				//Cojo solo las facturas activas
				if (lf.getBoolean(Messages.KEY_act)) {
					TLineaFactura tLfVal = this.readAux(lf);
					tLfVal.setIdLineaFactura((Integer.valueOf(idLf)));
					lfValidas.add(tLfVal);
				}
			}
			
			((ArrayList<TLineaFactura>) lfValidas).sort((a, b) -> Integer.compare(a.getIdLineaFactura(), b.getIdLineaFactura()));
			return lfValidas;
		}
		else return null;
	}
	
	@Override
	public int update(TLineaFactura tLineaFac) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
			
			Set<String> allIdsLinFacs = lineasFactura.keySet();
			for (String idLinFac : allIdsLinFacs) {
	        	JSONObject linea = lineasFactura.getJSONObject(idLinFac);
	        	
	        	//Solo se actualiza si coincide en id y si está activa
	            if (linea.getInt(Messages.KEY_idFac) == tLineaFac.getIdFactura() && linea.getBoolean(Messages.KEY_act)) {
	            	lineasFactura.put(idLinFac,tLineaFac);
	            	OpsBBDD.write(bdLinFac, Messages.BDFac);
	                return Integer.valueOf(idLinFac);
	            }
	        }
		}
        return -1; //No se ha actualizado la factura pasada por parámetro
	}
	
	private TLineaFactura readAux(JSONObject lf) {
		return new TLineaFactura(
				lf.getInt(Messages.KEY_idLinFac), 
				lf.getInt(Messages.KEY_idPase),
				lf.getInt(Messages.KEY_ctdad),
				lf.getFloat(Messages.KEY_LF_precio));
	}
}
