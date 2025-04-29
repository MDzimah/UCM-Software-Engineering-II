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
		
		JSONObject linFacs = bdLinFac.getJSONObject(Messages.KEY_LineasFac);
		
		//Aumentamos el último índice de la bd
		int newId = bdLinFac.getInt(Messages.KEY_lastId) + 1;
		bdLinFac.put(Messages.KEY_lastId, newId);
		
		//Insertamos nueva linea en la bd de LinFacturas, su clave es su id
		linFacs.put(Integer.toString(newId), this.newLinFac(tLineaFactura));
		OpsBBDD.write(bdLinFac, Messages.BDLinFac);
		
		return newId;
	}
	
	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			JSONObject linFacs = bdLinFac.getJSONObject(Messages.KEY_facs);
	        
			String _id = Integer.toString(id);
			if (linFacs.has(_id)) {
		        linFacs.remove(_id);
		        OpsBBDD.write(bdLinFac, Messages.BDLinFac);
			    return id;
			}
		}
        return -1; //No se ha encontrado la linea de factura con dicho id
	}

	@Override
	public TLineaFactura read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			JSONObject linFacs = bdLinFac.getJSONObject(Messages.KEY_facs);
			
			TLineaFactura tLfRead = null;
			String _id = Integer.toString(id);
			
			if (linFacs.has(_id)) {
		        tLfRead = this.readAux(linFacs.getJSONObject(_id));
		        tLfRead.setIdLineaFactura(id);
			}
			return tLfRead;
		}
		else return null;
	}
	
	@Override
	public Collection<TLineaFactura> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDLinFac)) {
			JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
			
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
			JSONObject linFacs = bdLinFac.getJSONObject(Messages.KEY_facs);
			
			String _id = Integer.toString(tLineaFac.getIdLineaFactura());
			
			if (linFacs.has(_id)) {
				linFacs.put(_id, this.newLinFac(tLineaFac));
        		OpsBBDD.write(bdLinFac, Messages.BDLinFac);
        		return tLineaFac.getIdLineaFactura();
			}
		}
        return -1; //No se ha actualizado la linea factura pasada por parámetro
	}
	
	private TLineaFactura readAux(JSONObject lf) {
		return new TLineaFactura(
				lf.getInt(Messages.KEY_idLinFac), 
				lf.getInt(Messages.KEY_idPase),
				lf.getInt(Messages.KEY_ctdad),
				lf.getFloat(Messages.KEY_precioVenta));
	}
	
	private JSONObject newLinFac(TLineaFactura tLinFac) {
		JSONObject res = new JSONObject();
		res.put(Messages.KEY_idFac, tLinFac.getIdFactura());
		res.put(Messages.KEY_idPase, tLinFac.getIdPase());
		res.put(Messages.KEY_ctdad, tLinFac.getCantidad());
		res.put(Messages.KEY_precioVenta, tLinFac.getPrecioVenta());
		return res;
	}
}
